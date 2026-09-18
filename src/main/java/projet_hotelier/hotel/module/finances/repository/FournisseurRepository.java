package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.fournisseur.FournisseurModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<FournisseurModel, Long> {

    Optional<FournisseurModel> findByUuid(String uuid);

    Optional<FournisseurModel> findByCodeFournisseur(String codeFournisseur);

    Page<FournisseurModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    List<FournisseurModel> findByOrganisationIdAndActifTrue(Long organisationId);

    List<FournisseurModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId);

    List<FournisseurModel> findByOrganisationIdAndStatutFournisseurAndActifTrue(Long organisationId, String statutFournisseur);

    List<FournisseurModel> findByOrganisationIdAndTypeFournisseurAndActifTrue(Long organisationId, String typeFournisseur);

    List<FournisseurModel> findByOrganisationIdAndCategorieAndActifTrue(Long organisationId, String categorie);

    @Query("SELECT f FROM FournisseurModel f WHERE f.organisationId = :orgId AND f.actifCommercial = true AND f.bloqueCommande = false AND f.actif = true")
    List<FournisseurModel> findActifsCommandables(@Param("orgId") Long organisationId);

    @Query("SELECT f FROM FournisseurModel f WHERE f.organisationId = :orgId AND (f.bloqueCommande = true OR f.bloquePaiement = true) AND f.actif = true")
    List<FournisseurModel> findBloques(@Param("orgId") Long organisationId);

    @Query("SELECT f FROM FournisseurModel f WHERE f.organisationId = :orgId AND f.encoursFournisseur > :seuil AND f.actif = true")
    List<FournisseurModel> findAvecEncoursSuperieur(@Param("orgId") Long organisationId, @Param("seuil") BigDecimal seuil);

    @Query("SELECT f FROM FournisseurModel f WHERE f.organisationId = :orgId AND LOWER(f.raisonSociale) LIKE LOWER(CONCAT('%', :terme, '%')) AND f.actif = true")
    List<FournisseurModel> searchByRaisonSociale(@Param("orgId") Long organisationId, @Param("terme") String terme);

    @Query("SELECT SUM(f.encoursFournisseur) FROM FournisseurModel f WHERE f.organisationId = :orgId AND f.actif = true")
    BigDecimal sumEncoursFournisseurs(@Param("orgId") Long organisationId);

    @Query("SELECT f.categorie, COUNT(f) FROM FournisseurModel f WHERE f.organisationId = :orgId AND f.actif = true GROUP BY f.categorie")
    List<Object[]> countByCategorie(@Param("orgId") Long organisationId);

    boolean existsByCodeFournisseur(String codeFournisseur);

    boolean existsByNif(String nif);
}
