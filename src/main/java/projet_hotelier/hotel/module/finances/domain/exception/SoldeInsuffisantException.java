package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

import java.math.BigDecimal;

public class SoldeInsuffisantException extends BusinessException {

    private final String codeCompte;
    private final BigDecimal soldeDisponible;
    private final BigDecimal montantDemande;

    public SoldeInsuffisantException(String codeCompte, BigDecimal soldeDisponible, BigDecimal montantDemande) {
        super("SOLDE_INSUFFISANT",
                String.format("Solde insuffisant sur le compte %s. Disponible: %s, Demande: %s",
                        codeCompte, soldeDisponible, montantDemande));
        this.codeCompte = codeCompte;
        this.soldeDisponible = soldeDisponible;
        this.montantDemande = montantDemande;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public BigDecimal getSoldeDisponible() {
        return soldeDisponible;
    }

    public BigDecimal getMontantDemande() {
        return montantDemande;
    }

    public BigDecimal getEcart() {
        return montantDemande.subtract(soldeDisponible);
    }
}
