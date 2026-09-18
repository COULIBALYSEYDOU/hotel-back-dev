package projet_hotelier.hotel.module.finances.model.budget.ennumeration;


import lombok.Getter;

@Getter
public enum MotifRevisionBudget {

    AJUSTEMENT_OPERATIONNEL(
            "AJUSTEMENT_OPERATIONNEL",
            "Ajustement opérationnel",
            true,
            false,
            false,
            false,
            "Exploitation",
            "Variation activité quotidienne"
    ),

    VARIATION_TAUX_OCCUPATION(
            "VARIATION_TAUX_OCCUPATION",
            "Variation du taux d’occupation",
            true,
            false,
            false,
            false,
            "Revenue Management",
            "Impact direct RevPAR"
    ),

    CHANGEMENT_STRATEGIQUE(
            "CHANGEMENT_STRATEGIQUE",
            "Changement stratégique",
            true,
            true,
            true,
            false,
            "Stratégie",
            "Décision long terme"
    ),

    INFLATION_LOCALE(
            "INFLATION_LOCALE",
            "Inflation locale",
            true,
            false,
            false,
            true,
            "Macro-économie",
            "Impact économique externe"
    ),

    VARIATION_TAUX_CHANGE(
            "VARIATION_TAUX_CHANGE",
            "Variation des taux de change",
            true,
            false,
            false,
            true,
            "Finance internationale",
            "Multi-devises"
    ),

    DEPASSEMENT_COUTS(
            "DEPASSEMENT_COUTS",
            "Dépassement de coûts",
            true,
            true,
            false,
            false,
            "Contrôle budgétaire",
            "Correction budgétaire"
    ),

    OPPORTUNITE_INVESTISSEMENT(
            "OPPORTUNITE_INVESTISSEMENT",
            "Nouvelle opportunité d’investissement",
            true,
            true,
            true,
            false,
            "Investissement",
            "Création de valeur"
    ),

    CONFORMITE_REGLEMENTAIRE(
            "CONFORMITE_REGLEMENTAIRE",
            "Conformité réglementaire",
            false,
            true,
            true,
            true,
            "Réglementation",
            "Obligation légale"
    ),

    FORCE_MAJEURE(
            "FORCE_MAJEURE",
            "Force majeure",
            false,
            true,
            true,
            true,
            "Risque",
            "Événement exceptionnel"
    ),

    ERREUR_INITIALISATION(
            "ERREUR_INITIALISATION",
            "Erreur lors de la budgétisation initiale",
            true,
            false,
            false,
            false,
            "Correction",
            "Erreur humaine ou technique"
    );

    private final String code;
    private final String libelle;
    private final boolean justificationObligatoire;
    private final boolean validationFinanceObligatoire;
    private final boolean validationDirectionObligatoire;
    private final boolean auditRenforce;
    private final String domaineImpact;
    private final String descriptionMetier;

    MotifRevisionBudget(
            String code,
            String libelle,
            boolean justificationObligatoire,
            boolean validationFinanceObligatoire,
            boolean validationDirectionObligatoire,
            boolean auditRenforce,
            String domaineImpact,
            String descriptionMetier
    ) {
        this.code = code;
        this.libelle = libelle;
        this.justificationObligatoire = justificationObligatoire;
        this.validationFinanceObligatoire = validationFinanceObligatoire;
        this.validationDirectionObligatoire = validationDirectionObligatoire;
        this.auditRenforce = auditRenforce;
        this.domaineImpact = domaineImpact;
        this.descriptionMetier = descriptionMetier;
    }
}
