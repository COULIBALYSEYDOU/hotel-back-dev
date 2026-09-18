package projet_hotelier.hotel.module.planning.service.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.planning.domain.event.ReservationConfirmeeEvent;
import projet_hotelier.hotel.module.planning.dto.request.reservation.CreateReservationRequest;
import projet_hotelier.hotel.module.planning.dto.request.reservation.UpdateReservationRequest;
import projet_hotelier.hotel.module.planning.dto.response.reservation.ReservationResponse;
import projet_hotelier.hotel.module.planning.mapper.ReservationMapper;
import projet_hotelier.hotel.module.planning.model.reservation.ReservationModel;
import projet_hotelier.hotel.module.planning.repository.ReservationRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;
import projet_hotelier.hotel.shared.exception.ValidationException;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des reservations.
 *
 * Regle metier : si la chambre est deja reservee sur la periode demandee,
 * la creation/modification est ACCEPTEE mais le statut est positionne a "EN_CONFLIT".
 * Le personnel devra resoudre le conflit manuellement.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    public static final String STATUT_EN_CONFLIT = "EN_CONFLIT";
    public static final String STATUT_EN_ATTENTE = "EN_ATTENTE";
    public static final String STATUT_CONFIRMEE = "CONFIRMEE";

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ApplicationEventPublisher eventPublisher;

    public ReservationResponse create(CreateReservationRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation reservation code={} chambre={} client={}", request.getCodeReservation(), request.getChambreId(), request.getClientId());
        validerDates(request.getDateArrivee(), request.getDateDepart());

        if (reservationRepository.existsByCodeReservation(request.getCodeReservation())) {
            throw ConflictException.duplicate("Reservation", "codeReservation", request.getCodeReservation());
        }

        ReservationModel entity = reservationMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        // Verification de disponibilite (regle "avertir mais autoriser")
        List<ReservationModel> conflits = reservationRepository.findOverlapping(
                organisationId, request.getChambreId(),
                request.getDateArrivee(), request.getDateDepart(), null);
        if (!conflits.isEmpty()) {
            log.warn("Chevauchement detecte pour chambre={} : {} reservation(s) en conflit. Statut force a EN_CONFLIT.",
                    request.getChambreId(), conflits.size());
            entity.setStatutReservation(STATUT_EN_CONFLIT);
        } else if (entity.getStatutReservation() == null || entity.getStatutReservation().isBlank()) {
            entity.setStatutReservation(STATUT_EN_ATTENTE);
        }

        ReservationModel saved = reservationRepository.save(entity);
        log.info("Reservation creee id={} uuid={} statut={}", saved.getId(), saved.getUuid(), saved.getStatutReservation());
        return reservationMapper.toResponse(saved);
    }

    public ReservationResponse update(String uuid, UpdateReservationRequest request, Long organisationId, String username) {
        log.info("Mise a jour reservation uuid={}", uuid);
        ReservationModel entity = findByUuidAndOrganisation(uuid, organisationId);

        LocalDate dateArrivee = request.getDateArrivee() != null ? request.getDateArrivee() : entity.getDateArrivee();
        LocalDate dateDepart = request.getDateDepart() != null ? request.getDateDepart() : entity.getDateDepart();
        Long chambreId = request.getChambreId() != null ? request.getChambreId() : entity.getChambreId();
        validerDates(dateArrivee, dateDepart);

        reservationMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        // Verification disponibilite si la chambre ou les dates ont change
        boolean dateOuChambreModifiee = request.getChambreId() != null
                || request.getDateArrivee() != null
                || request.getDateDepart() != null;
        if (dateOuChambreModifiee) {
            List<ReservationModel> conflits = reservationRepository.findOverlapping(
                    organisationId, chambreId, dateArrivee, dateDepart, uuid);
            if (!conflits.isEmpty()) {
                log.warn("Chevauchement detecte apres modification de la reservation uuid={} : {} conflit(s).",
                        uuid, conflits.size());
                entity.setStatutReservation(STATUT_EN_CONFLIT);
            }
        }

        ReservationModel updated = reservationRepository.save(entity);
        return reservationMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public ReservationResponse getByUuid(String uuid, Long organisationId) {
        return reservationMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public ReservationResponse getByCode(String code, Long organisationId) {
        ReservationModel entity = reservationRepository.findByCodeReservation(code)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "codeReservation", code));
        ensureSameOrganisation(entity, organisationId);
        return reservationMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getByClient(Long clientId, Long organisationId) {
        return reservationRepository.findByClientIdAndActifTrue(clientId).stream()
                .filter(r -> r.getOrganisationId().equals(organisationId))
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getByPeriode(Long organisationId, LocalDate dateDebut, LocalDate dateFin) {
        validerDates(dateDebut, dateFin);
        return reservationMapper.toResponseList(
                reservationRepository
                        .findByOrganisationIdAndDateArriveeLessThanEqualAndDateDepartGreaterThanEqual(organisationId, dateFin, dateDebut));
    }

    @Transactional(readOnly = true)
    public Page<ReservationResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return reservationRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(reservationMapper::toResponse);
    }

    /**
     * Verifie si une chambre est libre sur la periode.
     * Retourne true s'il n'existe AUCUNE reservation active chevauchant la periode.
     */
    @Transactional(readOnly = true)
    public boolean isChambreDisponible(Long organisationId, Long chambreId, LocalDate dateDebut, LocalDate dateFin) {
        validerDates(dateDebut, dateFin);
        return reservationRepository.findOverlapping(organisationId, chambreId, dateDebut, dateFin, null).isEmpty();
    }

    public ReservationResponse changerStatut(String uuid, String nouveauStatut, Long organisationId, String username) {
        log.info("Changement de statut reservation uuid={} -> {}", uuid, nouveauStatut);
        ReservationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        String ancienStatut = entity.getStatutReservation();
        entity.setStatutReservation(nouveauStatut);
        entity.setModifiePar(username);
        ReservationModel saved = reservationRepository.save(entity);

        // Publication d'un evenement quand la reservation devient CONFIRMEE
        // (idempotent : uniquement si le statut change effectivement)
        if (STATUT_CONFIRMEE.equalsIgnoreCase(nouveauStatut)
                && !STATUT_CONFIRMEE.equalsIgnoreCase(ancienStatut)) {
            log.info("Publication ReservationConfirmeeEvent pour reservation {}", saved.getCodeReservation());
            eventPublisher.publishEvent(ReservationConfirmeeEvent.builder()
                    .reservationId(saved.getId())
                    .uuidReservation(saved.getUuid())
                    .codeReservation(saved.getCodeReservation())
                    .clientId(saved.getClientId())
                    .clientNom("Client " + saved.getClientId())
                    .montantTotal(saved.getMontantTotal())
                    .devise("EUR")
                    .organisationId(saved.getOrganisationId())
                    .hotelId(saved.getHotelId())
                    .username(username)
                    .build());
        }

        return reservationMapper.toResponse(saved);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Annulation/suppression logique reservation uuid={}", uuid);
        ReservationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setStatutReservation("ANNULEE");
        entity.setModifiePar(username);
        reservationRepository.save(entity);
    }

    private void validerDates(LocalDate dateArrivee, LocalDate dateDepart) {
        if (dateArrivee == null || dateDepart == null) {
            throw new ValidationException("Les dates d'arrivee et de depart sont requises");
        }
        if (!dateDepart.isAfter(dateArrivee)) {
            throw new ValidationException("La date de depart doit etre posterieure a la date d'arrivee");
        }
    }

    private ReservationModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        ReservationModel entity = reservationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "uuid", uuid));
        ensureSameOrganisation(entity, organisationId);
        return entity;
    }

    private void ensureSameOrganisation(ReservationModel entity, Long organisationId) {
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Reservation", "uuid", entity.getUuid());
        }
    }
}
