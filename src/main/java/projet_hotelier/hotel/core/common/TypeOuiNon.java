package projet_hotelier.hotel.core.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * TypeOuiNon :
 * Embeddable pour représenter un booléen en "OUI / NON".
 */
@Embeddable
public class TypeOuiNon {

    @Column(name = "oui_non", length = 3)
    private String valeur; // "OUI" ou "NON"

    protected TypeOuiNon() {}

    public TypeOuiNon(Boolean bool) {
        this.valeur = (bool != null && bool) ? "OUI" : "NON";
    }

    public TypeOuiNon(String valeur) {
        if (!isValide(valeur)) {
            throw new IllegalArgumentException("Valeur invalide : " + valeur);
        }
        this.valeur = valeur.toUpperCase();
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        if (!isValide(valeur)) {
            throw new IllegalArgumentException("Valeur invalide : " + valeur);
        }
        this.valeur = valeur.toUpperCase();
    }

    public Boolean toBoolean() {
        return "OUI".equalsIgnoreCase(this.valeur);
    }

    public static boolean isValide(String valeur) {
        if (valeur == null) return false;
        String v = valeur.trim().toUpperCase();
        return "OUI".equals(v) || "NON".equals(v);
    }

    @Override
    public String toString() {
        return valeur;
    }
}

