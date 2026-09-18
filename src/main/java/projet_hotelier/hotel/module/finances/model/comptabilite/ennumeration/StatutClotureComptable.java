package projet_hotelier.hotel.module.finances.model.comptabilite.ennumeration;

/**
 * Statuts possibles pour une clôture comptable.
 */
public enum StatutClotureComptable {
    EN_PREPARATION,     // En cours de préparation
    EN_ATTENTE,         // En attente de validation
    VALIDEE_CC,         // Validée par le chef comptable
    VALIDEE_DF,         // Validée par le directeur financier
    VALIDEE_DG,         // Validée par la direction générale
    CLOTUREE,           // Clôturée définitivement
    VERROUILLEE,        // Verrouillée
    REOUVERTE,          // Rouverte
    ARCHIVEE            // Archivée
}
