package projet_hotelier.hotel.module.finances.service.facture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.finances.dto.request.facture.CreateFactureRequest;
import projet_hotelier.hotel.module.finances.dto.response.facture.FactureResponse;
import projet_hotelier.hotel.module.finances.mapper.FactureMapper;
import projet_hotelier.hotel.module.finances.model.facturationDocument.FactureModel;
import projet_hotelier.hotel.module.finances.repository.FactureRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;
import projet_hotelier.hotel.shared.exception.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des factures (conforme OHADA).
 * CRUD + business ops : calcul TVA, marquage payée, ajout paiement, annulation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FactureService {

    public static final String STATUT_BROUILLON = "BROUILLON";
    public static final String STATUT_EMISE = "EMISE";
    public static final String STATUT_PAYEE = "PAYEE";
    public static final String STATUT_ANNULEE = "ANNULEE";

    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;

    public FactureResponse create(CreateFactureRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation facture pour organisation={} client={}", organisationId, request.getClientNom());

        FactureModel entity = factureMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId != null ? hotelId : request.getHotelId());
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        // Génération du numéro de facture si absent
        if (entity.getNumeroFacture() == null || entity.getNumeroFacture().isBlank()) {
            entity.setNumeroFacture(genererNumeroFacture(organisationId, request.getTypeFacture(), request.getDateFacture()));
        } else if (factureRepository.existsByNumeroFacture(entity.getNumeroFacture())) {
            throw ConflictException.duplicate("Facture", "numeroFacture", entity.getNumeroFacture());
        }

        // Calcul des montants à partir des lignes
        recalculerMontants(entity);

        // Statut initial
        entity.setStatutFacture(STATUT_BROUILLON);
        entity.setEmise(false);
        entity.setPayee(false);
        entity.setAnnulee(false);
        entity.setMontantPaye(BigDecimal.ZERO);
        entity.setMontantRestant(entity.getMontantTTC());

        FactureModel saved = factureRepository.save(entity);
        log.info("Facture creee numero={} montantTTC={}", saved.getNumeroFacture(), saved.getMontantTTC());
        return factureMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public FactureResponse getByUuid(String uuid, Long organisationId) {
        return factureMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public FactureResponse getByNumero(String numero, Long organisationId) {
        FactureModel entity = factureRepository.findByNumeroFacture(numero)
                .orElseThrow(() -> new ResourceNotFoundException("Facture", "numeroFacture", numero));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Facture", "numeroFacture", numero);
        }
        return factureMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<FactureResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return factureRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(factureMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<FactureResponse> getByClient(Long clientId, Long organisationId) {
        return factureMapper.toResponseList(
                factureRepository.findByOrganisationIdAndClientIdAndActifTrue(organisationId, clientId));
    }

    @Transactional(readOnly = true)
    public List<FactureResponse> getByReservation(Long reservationId, Long organisationId) {
        return factureMapper.toResponseList(
                factureRepository.findByOrganisationIdAndReservationIdAndActifTrue(organisationId, reservationId));
    }

    @Transactional(readOnly = true)
    public List<FactureResponse> getNonPayees(Long organisationId) {
        return factureMapper.toResponseList(factureRepository.findNonPayees(organisationId));
    }

    @Transactional(readOnly = true)
    public List<FactureResponse> getEchuesNonPayees(Long organisationId) {
        return factureMapper.toResponseList(factureRepository.findEchuesNonPayees(organisationId, LocalDate.now()));
    }

    /**
     * Emet la facture (passage BROUILLON -> EMISE).
     */
    public FactureResponse emettre(String uuid, Long organisationId, String username) {
        FactureModel entity = findByUuidAndOrganisation(uuid, organisationId);
        if (entity.isAnnulee()) {
            throw new ValidationException("Impossible d'emettre une facture annulee");
        }
        entity.setEmise(true);
        entity.setStatutFacture(STATUT_EMISE);
        entity.setDateEmission(LocalDate.now());
        entity.setModifiePar(username);
        return factureMapper.toResponse(factureRepository.save(entity));
    }

    /**
     * Applique un paiement sur la facture : ajuste montantPaye/montantRestant et le statut payee/partiellementPayee.
     */
    public FactureResponse appliquerPaiement(String uuid, BigDecimal montant, Long organisationId, String username) {
        if (montant == null || montant.signum() <= 0) {
            throw new ValidationException("Le montant du paiement doit etre positif");
        }
        FactureModel entity = findByUuidAndOrganisation(uuid, organisationId);
        if (entity.isAnnulee()) {
            throw new ValidationException("Impossible de payer une facture annulee");
        }
        BigDecimal dejaPaye = entity.getMontantPaye() != null ? entity.getMontantPaye() : BigDecimal.ZERO;
        BigDecimal nouveauPaye = dejaPaye.add(montant);
        BigDecimal ttc = entity.getMontantTTC();
        if (nouveauPaye.compareTo(ttc) > 0) {
            throw new ValidationException("Le montant paye total depasse le montant TTC de la facture");
        }
        entity.setMontantPaye(nouveauPaye);
        entity.setMontantRestant(ttc.subtract(nouveauPaye));
        boolean fullyPaid = entity.getMontantRestant().signum() == 0;
        entity.setPayee(fullyPaid);
        entity.setPartiellementPayee(!fullyPaid && nouveauPaye.signum() > 0);
        if (fullyPaid) {
            entity.setStatutFacture(STATUT_PAYEE);
        }
        entity.setModifiePar(username);
        return factureMapper.toResponse(factureRepository.save(entity));
    }

    public FactureResponse annuler(String uuid, String motif, Long organisationId, String username) {
        FactureModel entity = findByUuidAndOrganisation(uuid, organisationId);
        if (entity.isPayee()) {
            throw new ValidationException("Impossible d'annuler une facture deja payee (emettre un avoir a la place)");
        }
        entity.setAnnulee(true);
        entity.setStatutFacture(STATUT_ANNULEE);
        entity.setNotesInternes(motif);
        entity.setModifiePar(username);
        return factureMapper.toResponse(factureRepository.save(entity));
    }

    public void delete(String uuid, Long organisationId, String username) {
        FactureModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        factureRepository.save(entity);
    }

    /**
     * Créé une facture depuis une réservation confirmée (appelée par listener event).
     */
    public FactureResponse creerDepuisReservation(Long reservationId, String numeroReservation, Long clientId,
                                                   String clientNom, BigDecimal montantTTC, String devise,
                                                   Long organisationId, Long hotelId, String username) {
        log.info("Creation facture depuis reservation {} (montant={})", numeroReservation, montantTTC);
        FactureModel entity = FactureModel.builder()
                .numeroFacture(genererNumeroFacture(organisationId, "FACTURE", LocalDate.now()))
                .typeFacture("FACTURE")
                .dateFacture(LocalDate.now())
                .clientId(clientId)
                .clientNom(clientNom != null ? clientNom : "Client " + clientId)
                .reservationId(reservationId)
                .numeroReservation(numeroReservation)
                .montantHT(montantTTC != null ? montantTTC : BigDecimal.ZERO)
                .montantTTC(montantTTC != null ? montantTTC : BigDecimal.ZERO)
                .montantPaye(BigDecimal.ZERO)
                .devise(devise != null ? devise : "EUR")
                .statutFacture(STATUT_EMISE)
                .emise(true)
                .payee(false)
                .annulee(false)
                .build();
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username != null ? username : "SYSTEM");
        entity.setActif(true);
        entity.setSupprime(false);
        entity.setMontantRestant(entity.getMontantTTC());
        FactureModel saved = factureRepository.save(entity);
        log.info("Facture creee automatiquement: numero={} pour reservation {}", saved.getNumeroFacture(), numeroReservation);
        return factureMapper.toResponse(saved);
    }

    private String genererNumeroFacture(Long organisationId, String type, LocalDate date) {
        int annee = date != null ? date.getYear() : LocalDate.now().getYear();
        String typeCode = type != null ? type : "FACTURE";
        String maxSeq = factureRepository.findMaxNumeroSequence(organisationId, annee, typeCode);
        int next = 1;
        if (maxSeq != null && maxSeq.matches("\\d+")) {
            next = Integer.parseInt(maxSeq) + 1;
        }
        return String.format("%s-%d-%06d", typeCode.substring(0, Math.min(3, typeCode.length())).toUpperCase(), annee, next);
    }

    private void recalculerMontants(FactureModel entity) {
        // Si les lignes ne sont pas encore persistées, on part du montantHT déjà présent
        BigDecimal ht = entity.getMontantHT() != null ? entity.getMontantHT() : BigDecimal.ZERO;
        BigDecimal tauxTva = entity.getTauxTVA() != null ? entity.getTauxTVA() : BigDecimal.ZERO;
        BigDecimal tva = ht.multiply(tauxTva).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal ttc = ht.add(tva);
        entity.setMontantHTApresRemise(ht);
        entity.setMontantTVA(tva);
        entity.setMontantTTC(ttc);
    }

    private FactureModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        FactureModel entity = factureRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Facture", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Facture", "uuid", uuid);
        }
        return entity;
    }
}
