package projet_hotelier.hotel.module.finances.model.budget.ennumeration;

import lombok.Getter;

@Getter
public enum StatutSuiviBudget {

    NORMAL(
            "NORMAL",
            "Exécution normale",
            "Budget exécuté conformément aux prévisions",
            0,
            false,
            false,
            true,
            false,
            false,
            false,
            false,
            false,
            "Aucune action nécessaire",
            "Aucune alerte déclenchée",
            "Visible dans le BI",
            false,
            false,
            false
    ),

    SOUS_SURVEILLANCE(
            "SOUS_SURVEILLANCE",
            "Sous surveillance",
            "Écart léger détecté, mais sous contrôle",
            1,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            false,
            "Renforcer le contrôle, analyser l’écart",
            "Alerte de niveau 1",
            "Visible dans le BI",
            false,
            false,
            false
    ),

    ECART_MODERE(
            "ECART_MODERE",
            "Écart modéré",
            "Écart budgétaire modéré, nécessite une action corrective rapide",
            2,
            true,
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            "Revoir les dépenses non essentielles, ajuster les prévisions",
            "Alerte de niveau 2",
            "Visible dans le BI",
            true,
            false,
            false
    ),

    ECART_SIGNIFICATIF(
            "ECART_SIGNIFICATIF",
            "Écart significatif",
            "Écart significatif, risque de dépassement à court terme",
            3,
            true,
            true,
            true,
            true,
            true,
            false,
            false,
            false,
            "Blocage partiel des dépenses, révision budgétaire recommandée",
            "Alerte de niveau 3",
            "Visible dans le BI",
            true,
            true,
            false
    ),

    DEPASSEMENT(
            "DEPASSEMENT",
            "Dépassement budgétaire",
            "Le budget est dépassé, nécessite une décision de la direction",
            4,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            "Gel des dépenses non critiques, révision budgétaire obligatoire",
            "Alerte de niveau 4",
            "Visible dans le BI",
            true,
            true,
            true
    ),

    CRITIQUE(
            "CRITIQUE",
            "Situation critique",
            "Risque financier majeur, impact sur la trésorerie et la solvabilité",
            5,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            "Escalade immédiate à la DG, audit interne recommandé",
            "Alerte de niveau 5",
            "Visible dans le BI",
            true,
            true,
            true
    ),

    GELE(
            "GELE",
            "Budget gelé",
            "Budget gelé suite à une décision stratégique ou à un risque majeur",
            6,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            "Aucune dépense autorisée (sauf urgence validée)",
            "Alerte de gel",
            "Visible dans le BI",
            true,
            true,
            true
    ),

    SUSPENDU(
            "SUSPENDU",
            "Budget suspendu",
            "Suspension temporaire en attente d’une décision ou d’une clarification",
            7,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            "Suspension des engagements, décision stratégique requise",
            "Alerte de suspension",
            "Visible dans le BI",
            true,
            true,
            true
    ),

    EN_REVISION(
            "EN_REVISION",
            "En révision",
            "Le budget est en cours de révision (réallocation ou ajustement)",
            8,
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            false,
            "Révision budgétaire en cours, blocage partiel",
            "Alerte de révision",
            "Visible dans le BI",
            true,
            false,
            false
    ),

    CLOTURE(
            "CLOTURE",
            "Budget clôturé",
            "Clôture officielle de l’exercice budgétaire",
            0,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            "Clôture validée, archivage",
            "Clôture budgétaire",
            "Visible dans le BI",
            false,
            false,
            false
    );

    private final String code;
    private final String libelle;
    private final String description;
    private final int niveauCriticite;
    private final boolean actionCorrectiveRequise;
    private final boolean depensesAutorisees;
    private final boolean alerteAutomatique;
    private final boolean escalationDG;
    private final boolean blocageComptable;
    private final boolean blocageAchats;
    private final boolean blocageRecrutement;
    private final boolean blocageInvestissement;
    private final String actionRecommandee;
    private final String niveauAlerte;
    private final String visibleDansBI;
    private final boolean impactTresorerie;
    private final boolean impactFiscal;
    private final boolean impactResultat;

    StatutSuiviBudget(
            String code,
            String libelle,
            String description,
            int niveauCriticite,
            boolean actionCorrectiveRequise,
            boolean depensesAutorisees,
            boolean alerteAutomatique,
            boolean escalationDG,
            boolean blocageComptable,
            boolean blocageAchats,
            boolean blocageRecrutement,
            boolean blocageInvestissement,
            String actionRecommandee,
            String niveauAlerte,
            String visibleDansBI,
            boolean impactTresorerie,
            boolean impactFiscal,
            boolean impactResultat
    ) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        this.niveauCriticite = niveauCriticite;
        this.actionCorrectiveRequise = actionCorrectiveRequise;
        this.depensesAutorisees = depensesAutorisees;
        this.alerteAutomatique = alerteAutomatique;
        this.escalationDG = escalationDG;
        this.blocageComptable = blocageComptable;
        this.blocageAchats = blocageAchats;
        this.blocageRecrutement = blocageRecrutement;
        this.blocageInvestissement = blocageInvestissement;
        this.actionRecommandee = actionRecommandee;
        this.niveauAlerte = niveauAlerte;
        this.visibleDansBI = visibleDansBI;
        this.impactTresorerie = impactTresorerie;
        this.impactFiscal = impactFiscal;
        this.impactResultat = impactResultat;
    }
}
