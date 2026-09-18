package projet_hotelier.hotel.module.finances.service.paiement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.finances.dto.request.paiement.CreatePaiementRequest;
import projet_hotelier.hotel.module.finances.dto.response.paiement.PaiementResponse;
import projet_hotelier.hotel.module.finances.mapper.PaiementMapper;
import projet_hotelier.hotel.module.finances.model.facturationDocument.FactureModel;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.PaiementModel;
import projet_hotelier.hotel.module.finances.repository.FactureRepository;
import projet_hotelier.hotel.module.finances.repository.PaiementRepository;
import projet_hotelier.hotel.module.finances.service.facture.FactureService;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service pour la gestion des paiements (encaissements/décaissements).
 * Si le paiement référence une facture, met à jour le montantPaye/montantRestant de la facture.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;
    private final FactureRepository factureRepository;
    private final FactureService factureService;

    public PaiementResponse create(CreatePaiementRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation paiement montant={} type={} facture={}", request.getMontant(), request.getTypePaiement(), request.getFactureId());

        PaiementModel entity = paiementMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId != null ? hotelId : request.getHotelId());
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        // Code paiement auto si absent
        String code = "PAY-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        entity.setCodePaiement(code);
        if (paiementRepository.existsByCodePaiement(code)) {
            throw ConflictException.duplicate("Paiement", "codePaiement", code);
        }
        entity.setStatutPaiement("VALIDE");
        entity.setValide(true);

        PaiementModel saved = paiementRepository.save(entity);

        // Si lié à une facture d'encaissement, mettre à jour la facture
        if (request.getFactureId() != null && "ENCAISSEMENT".equals(request.getTypePaiement())) {
            FactureModel facture = factureRepository.findById(request.getFactureId())
                    .orElseThrow(() -> new ResourceNotFoundException("Facture", request.getFactureId()));
            factureService.appliquerPaiement(facture.getUuid(), request.getMontant(), organisationId, username);
        }

        log.info("Paiement cree code={} montant={}", saved.getCodePaiement(), saved.getMontant());
        return paiementMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PaiementResponse getByUuid(String uuid, Long organisationId) {
        return paiementMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> getByFacture(Long factureId, Long organisationId) {
        return paiementMapper.toResponseList(
                paiementRepository.findByOrganisationIdAndFactureIdAndActifTrue(organisationId, factureId));
    }

    @Transactional(readOnly = true)
    public Page<PaiementResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return paiementRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(paiementMapper::toResponse);
    }

    public void delete(String uuid, Long organisationId, String username) {
        PaiementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        paiementRepository.save(entity);
    }

    private PaiementModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        PaiementModel entity = paiementRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Paiement", "uuid", uuid);
        }
        return entity;
    }
}
