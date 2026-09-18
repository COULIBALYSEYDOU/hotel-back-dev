package projet_hotelier.hotel.module.planning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.reservation.ReservationModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {

    Optional<ReservationModel> findByUuid(String uuid);

    Optional<ReservationModel> findByCodeReservation(String codeReservation);

    List<ReservationModel> findByClientIdAndActifTrue(Long clientId);

    List<ReservationModel> findByOrganisationIdAndDateArriveeLessThanEqualAndDateDepartGreaterThanEqual(Long organisationId, LocalDate dateDebut, LocalDate dateFin);

    Page<ReservationModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    boolean existsByCodeReservation(String codeReservation);

    /**
     * Reservations existantes qui chevauchent la periode demandee pour une chambre donnee.
     * Une reservation chevauche si dateArrivee < dateFin demandee ET dateDepart > dateDebut demandee.
     * Le parametre excludeUuid permet d'ignorer la reservation en cours de modification.
     */
    @Query("SELECT r FROM PlanningReservationModel r " +
           "WHERE r.organisationId = :organisationId " +
           "  AND r.chambreId = :chambreId " +
           "  AND r.actif = true " +
           "  AND r.supprime = false " +
           "  AND r.dateArrivee < :dateFin " +
           "  AND r.dateDepart > :dateDebut " +
           "  AND (:excludeUuid IS NULL OR r.uuid <> :excludeUuid)")
    List<ReservationModel> findOverlapping(@Param("organisationId") Long organisationId,
                                           @Param("chambreId") Long chambreId,
                                           @Param("dateDebut") LocalDate dateDebut,
                                           @Param("dateFin") LocalDate dateFin,
                                           @Param("excludeUuid") String excludeUuid);
}
