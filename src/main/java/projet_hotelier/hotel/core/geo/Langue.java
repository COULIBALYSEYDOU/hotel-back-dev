package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "langues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Langue extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code; // ex: fr, en, pt

    @Column(name = "nom_fr", length = 100)
    private String nomFr;

    @Column(name = "nom_en", length = 100)
    private String nomEn;

    @Column(name = "actif")
    private Boolean actif = true;

    public Langue(String code) {
        if (!isValide(code)) {
            throw new IllegalArgumentException("Langue invalide : " + code);
        }
        this.code = code.toLowerCase();
    }

    public static Set<String> getLanguesDisponibles() {
        return Arrays.stream(Locale.getAvailableLocales())
                .map(Locale::getLanguage)
                .filter(c -> c != null && c.length() > 0)
                .collect(Collectors.toSet());
    }

    public static boolean isValide(String code) {
        if (code == null || code.trim().length() == 0) return false;
        return getLanguesDisponibles().contains(code.toLowerCase());
    }

    public Locale toLocale() {
        return new Locale(code);
    }

    /**
     * TypeLangue : Classe embeddable pour utilisation dans d'autres entités si nécessaire.
     */
    @Embeddable
    public static class TypeLangue {

        @Column(name = "langue", length = 10)
        private String langueCode; // ex: fr, en, pt

        protected TypeLangue() {}

        public TypeLangue(String langueCode) {
            if (!Langue.isValide(langueCode)) {
                throw new IllegalArgumentException("Langue invalide : " + langueCode);
            }
            this.langueCode = langueCode.toLowerCase();
        }

        public String getLangueCode() {
            return langueCode;
        }

        public void setLangueCode(String langueCode) {
            if (!Langue.isValide(langueCode)) {
                throw new IllegalArgumentException("Langue invalide : " + langueCode);
            }
            this.langueCode = langueCode.toLowerCase();
        }

        public Locale toLocale() {
            return new Locale(langueCode);
        }
    }
}
