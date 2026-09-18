package projet_hotelier.hotel.core.structure;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;

/**
 * Entité représentant un site (emplacement physique d'un établissement).
 */
@Entity
@Table(name = "site")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Site extends BaseEntity {

    // Les champs de BaseEntity fournissent déjà l'identifiant (@Id)
    // Cette classe peut être étendue avec des champs spécifiques au site si nécessaire
}
