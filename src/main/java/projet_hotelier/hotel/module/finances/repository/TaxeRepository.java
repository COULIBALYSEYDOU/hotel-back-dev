package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.fiscalite.TaxeModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxeRepository extends JpaRepository<TaxeModel, Long> {

    Optional<TaxeModel> findByUuid(String uuid);

    List<TaxeModel> findByOrganisationIdAndActifTrue(Long organisationId);

    @Query("SELECT t FROM TaxeModel t WHERE t.organisationId = :orgId AND t.actif = true")
    List<TaxeModel> findActives(@Param("orgId") Long organisationId);
}
