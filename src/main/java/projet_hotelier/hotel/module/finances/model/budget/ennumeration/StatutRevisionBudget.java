package projet_hotelier.hotel.module.finances.model.budget.ennumeration;

import lombok.Getter;

@Getter
public enum StatutRevisionBudget {

    BROUILLON(
            "BROUILLON",
            "Révision en cours de préparation",
            false,
            false,
            false,
            false,
            false,
            0,
            "Aucune validation requise",
            "Création initiale",
            true
    ),

    SOUMISE(
            "SOUMISE",
            "Révision soumise pour validation",
            true,
            false,
            false,
            false,
            false,
            1,
            "Validation hiérarchique requise",
            "Soumission officielle",
            true
    ),

    EN_ANALYSE_FINANCIERE(
            "EN_ANALYSE_FINANCIERE",
            "Analyse par la direction financière",
            true,
            true,
            false,
            false,
            false,
            2,
            "Validation finance",
            "Contrôle financier",
            true
    ),

    EN_CONTROLE_INTERNE(
            "EN_CONTROLE_INTERNE",
            "Contrôle interne et conformité",
            true,
            true,
            true,
            false,
            false,
            3,
            "Contrôle interne obligatoire",
            "Audit interne",
            true
    ),

    EN_VALIDATION_DIRECTION(
            "EN_VALIDATION_DIRECTION",
            "Validation par la direction",
            true,
            true,
            true,
            true,
            false,
            4,
            "Validation direction générale",
            "Décision stratégique",
            true
    ),

    VALIDEE(
            "VALIDEE",
            "Révision validée définitivement",
            false,
            true,
            true,
            true,
            true,
            5,
            "Processus terminé",
            "Décision approuvée",
            false
    ),

    REJETEE(
            "REJETEE",
            "Révision rejetée",
            false,
            false,
            false,
            false,
            false,
            0,
            "Révision annulée",
            "Décision négative",
            false
    ),

    ANNULEE(
            "ANNULEE",
            "Révision annulée",
            false,
            false,
            false,
            false,
            false,
            0,
            "Annulation manuelle",
            "Annulation volontaire",
            false
    ),

    ARCHIVEE(
            "ARCHIVEE",
            "Révision archivée",
            false,
            true,
            true,
            true,
            true,
            6,
            "Historique figé",
            "Archivage légal",
            false
    );

    private final String code;
    private final String libelle;
    private final boolean editable;
    private final boolean visibleFinance;
    private final boolean visibleAudit;
    private final boolean visibleDirection;
    private final boolean statutFinal;
    private final int niveauWorkflow;
    private final String regleValidation;
    private final String usageMetier;
    private final boolean actionUtilisateurAutorisee;

    StatutRevisionBudget(
            String code,
            String libelle,
            boolean editable,
            boolean visibleFinance,
            boolean visibleAudit,
            boolean visibleDirection,
            boolean statutFinal,
            int niveauWorkflow,
            String regleValidation,
            String usageMetier,
            boolean actionUtilisateurAutorisee
    ) {
        this.code = code;
        this.libelle = libelle;
        this.editable = editable;
        this.visibleFinance = visibleFinance;
        this.visibleAudit = visibleAudit;
        this.visibleDirection = visibleDirection;
        this.statutFinal = statutFinal;
        this.niveauWorkflow = niveauWorkflow;
        this.regleValidation = regleValidation;
        this.usageMetier = usageMetier;
        this.actionUtilisateurAutorisee = actionUtilisateurAutorisee;
    }
}
