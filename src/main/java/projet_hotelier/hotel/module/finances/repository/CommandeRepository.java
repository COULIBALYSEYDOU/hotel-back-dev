package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.fournisseur.CommandeModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<CommandeModel, Long> {

    Optional<CommandeModel> findByUuid(String uuid);

    Page<CommandeModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    List<CommandeModel> findByOrganisationIdAndFournisseurIdAndActifTrue(Long organisationId, Long fournisseurId);

    @Query("SELECT c FROM CommandeModel c WHERE c.organisationId = :orgId AND c.dateCommande BETWEEN :dateDebut AND :dateFin AND c.actif = true")
    List<CommandeModel> findByOrganisationAndPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(c.montantTTC) FROM CommandeModel c WHERE c.organisationId = :orgId AND c.dateCommande BETWEEN :dateDebut AND :dateFin AND c.actif = true")
    BigDecimal sumCommandesByPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}
