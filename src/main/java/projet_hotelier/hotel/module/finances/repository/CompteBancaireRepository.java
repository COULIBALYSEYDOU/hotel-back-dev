package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.banque.CompteBancaireModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaireModel, Long> {

    Optional<CompteBancaireModel> findByUuid(String uuid);

    Optional<CompteBancaireModel> findByCodeCompte(String codeCompte);

    Optional<CompteBancaireModel> findByIban(String iban);

    Page<CompteBancaireModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    List<CompteBancaireModel> findByOrganisationIdAndActifTrue(Long organisationId);

    List<CompteBancaireModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId);

    List<CompteBancaireModel> findByOrganisationIdAndActifOperationnelTrueAndActifTrue(Long organisationId);

    List<CompteBancaireModel> findByOrganisationIdAndTypeCompteAndActifTrue(Long organisationId, String typeCompte);

    List<CompteBancaireModel> findByOrganisationIdAndDeviseAndActifTrue(Long organisationId, String devise);

    @Query("SELECT cb FROM CompteBancaireModel cb WHERE cb.organisationId = :orgId AND cb.comptePrincipal = true AND cb.actif = true")
    Optional<CompteBancaireModel> findComptePrincipal(@Param("orgId") Long organisationId);

    @Query("SELECT cb FROM CompteBancaireModel cb WHERE cb.organisationId = :orgId AND cb.visibleTableauBord = true AND cb.actif = true")
    List<CompteBancaireModel> findVisiblesTableauBord(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(cb.soldeCourant) FROM CompteBancaireModel cb WHERE cb.organisationId = :orgId AND cb.actif = true")
    BigDecimal sumSoldeCourant(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(cb.soldeCourant) FROM CompteBancaireModel cb WHERE cb.organisationId = :orgId AND cb.devise = :devise AND cb.actif = true")
    BigDecimal sumSoldeCourantByDevise(@Param("orgId") Long organisationId, @Param("devise") String devise);

    @Query("SELECT cb.devise, SUM(cb.soldeCourant) FROM CompteBancaireModel cb WHERE cb.organisationId = :orgId AND cb.actif = true GROUP BY cb.devise")
    List<Object[]> sumSoldeByDevise(@Param("orgId") Long organisationId);

    boolean existsByCodeCompte(String codeCompte);

    boolean existsByIban(String iban);
}
