package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.comptabilite.CentreCoutModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CentreCoutRepository extends JpaRepository<CentreCoutModel, Long> {

    Optional<CentreCoutModel> findByUuid(String uuid);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.code = :code AND c.actif = true")
    Optional<CentreCoutModel> findByOrganisationAndCode(@Param("orgId") Long organisationId, @Param("code") String code);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.actif = true")
    List<CentreCoutModel> findByOrganisation(@Param("orgId") Long organisationId);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.hotel.id = :hotelId AND c.actif = true")
    List<CentreCoutModel> findByOrganisationAndHotel(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.parent IS NULL AND c.actif = true")
    List<CentreCoutModel> findRacines(@Param("orgId") Long organisationId);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.parent.id = :parentId AND c.actif = true")
    List<CentreCoutModel> findByParent(@Param("parentId") Long parentId);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.actifOperationnel = true AND c.actif = true")
    List<CentreCoutModel> findActifsOperationnels(@Param("orgId") Long organisationId);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.generateurRevenu = true AND c.actif = true")
    List<CentreCoutModel> findGenerateursRevenu(@Param("orgId") Long organisationId);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.tauxConsommation > :seuil AND c.actif = true")
    List<CentreCoutModel> findAvecDepassementSeuil(@Param("orgId") Long organisationId, @Param("seuil") BigDecimal seuil);

    @Query("SELECT c FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.sousSurveillance = true AND c.actif = true")
    List<CentreCoutModel> findSousSurveillance(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(c.budgetAnnuelInitial) FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.actif = true")
    BigDecimal sumBudgetInitial(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(c.budgetConsommé) FROM CentreCoutModel c WHERE c.organisation.id = :orgId AND c.actif = true")
    BigDecimal sumBudgetConsomme(@Param("orgId") Long organisationId);
}
