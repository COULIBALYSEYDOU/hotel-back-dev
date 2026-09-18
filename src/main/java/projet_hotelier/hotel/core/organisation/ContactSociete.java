package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ContactSociete extends BaseEntity {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;

    private Boolean contactPrincipal;
    private Boolean contactFacturation;
    private Boolean contactSupport;
    private Boolean contactTechnique;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
