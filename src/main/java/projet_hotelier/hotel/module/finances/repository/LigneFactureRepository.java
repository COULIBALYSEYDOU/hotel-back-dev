package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.facturationDocument.LigneFactureModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LigneFactureRepository extends JpaRepository<LigneFactureModel, Long> {

    Optional<LigneFactureModel> findByUuid(String uuid);

    List<LigneFactureModel> findByFactureIdAndActifTrue(Long factureId);

    @Query("SELECT lf FROM FinanceLigneFactureModel lf WHERE lf.facture.id = :factureId AND lf.actif = true ORDER BY lf.numeroLigne")
    List<LigneFactureModel> findByFactureOrderedByNumeroLigne(@Param("factureId") Long factureId);

    @Query("SELECT SUM(lf.montantHT) FROM FinanceLigneFactureModel lf WHERE lf.facture.id = :factureId AND lf.actif = true")
    BigDecimal sumMontantHTByFacture(@Param("factureId") Long factureId);

    @Query("SELECT SUM(lf.montantTVA) FROM FinanceLigneFactureModel lf WHERE lf.facture.id = :factureId AND lf.actif = true")
    BigDecimal sumMontantTVAByFacture(@Param("factureId") Long factureId);

    @Query("SELECT SUM(lf.montantTTC) FROM FinanceLigneFactureModel lf WHERE lf.facture.id = :factureId AND lf.actif = true")
    BigDecimal sumMontantTTCByFacture(@Param("factureId") Long factureId);

    @Query("SELECT lf.tauxTVA, SUM(lf.montantHT), SUM(lf.montantTVA) FROM FinanceLigneFactureModel lf WHERE lf.facture.id = :factureId AND lf.actif = true GROUP BY lf.tauxTVA")
    List<Object[]> sumByTauxTVA(@Param("factureId") Long factureId);

    @Query("SELECT lf.categorie, SUM(lf.montantHT) FROM FinanceLigneFactureModel lf WHERE lf.facture.id = :factureId AND lf.actif = true GROUP BY lf.categorie")
    List<Object[]> sumByCategorie(@Param("factureId") Long factureId);

    void deleteByFactureId(Long factureId);
}
