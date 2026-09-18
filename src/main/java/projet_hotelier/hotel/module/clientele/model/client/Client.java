package projet_hotelier.hotel.module.clientele.model.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import projet_hotelier.hotel.module.clientele.model.client.ClientProfil;
import projet_hotelier.hotel.module.clientele.model.client.ClientPreference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "clients",
    indexes = {
        @Index(name = "idx_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_client_organisation", columnList = "organisation_id"),
        @Index(name = "idx_client_hotel", columnList = "hotel_id"),
        @Index(name = "idx_client_email", columnList = "tenant_id, email"),
        @Index(name = "idx_client_segment", columnList = "tenant_id, segment"),
        @Index(name = "idx_client_statut", columnList = "tenant_id, statut")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_client_email_tenant",
            columnNames = {"email", "tenant_id"}
        )
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank
    private String tenantId;

    @Column(name = "organisation_id", nullable = false, length = 100)
    @NotBlank
    private String organisationId;

    @Column(name = "hotel_id", nullable = false, length = 100)
    @NotBlank
    private String hotelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "civilite", length = 10)
    private Civilite civilite;

    @Column(name = "nom", nullable = false, length = 200)
    @NotBlank
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String nom;

    @Column(name = "prenom", length = 200)
    private String prenom;

    @Column(name = "email", nullable = false, length = 200)
    @NotBlank
    @Email
    @Size(max = 200, message = "L'email ne peut pas dépasser 200 caractères")
    private String email;

    @Column(name = "telephone", length = 20)
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Format téléphone invalide (10-15 chiffres)")
    private String telephone;

    @Column(name = "telephone_mobile", length = 20)
    private String telephoneMobile;

    @Column(name = "date_naissance")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    @Column(name = "nationalite", length = 3)
    private String nationalite;

    @Column(name = "langue_preferee", length = 5)
    private String languePreferee;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @NotNull
    @Builder.Default
    private ClientStatut statut = ClientStatut.ACTIF;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", length = 20)
    @Builder.Default
    private ClientSegment segment = ClientSegment.STANDARD;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_client", length = 20)
    @Builder.Default
    private TypeClient typeClient = TypeClient.INDIVIDUEL;

    @Column(name = "score_satisfaction")
    @Min(0)
    @Max(100)
    @Builder.Default
    private Integer scoreSatisfaction = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "risque_churn", length = 20)
    @Builder.Default
    private RisqueChurn risqueChurn = RisqueChurn.FAIBLE;

    @Column(name = "last_auto_segmentation")
    private LocalDateTime lastAutoSegmentation;

    @Column(name = "derniere_interaction")
    private LocalDateTime derniereInteraction;

    @Column(name = "nombre_sejours")
    @Builder.Default
    private Integer nombreSejours = 0;

    @Column(name = "nombre_nuitees")
    @Builder.Default
    private Integer nombreNuitees = 0;

    @Column(name = "chiffre_affaires_total", precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal chiffreAffairesTotal = BigDecimal.ZERO;

    @Column(name = "chiffre_affaires_annee_en_cours", precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal chiffreAffairesAnneeEnCours = BigDecimal.ZERO;

    @Column(name = "panier_moyen", precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal panierMoyen = BigDecimal.ZERO;

    // Référence vers module RH - ID UNIQUEMENT (pas de @ManyToOne)
    @Column(name = "gestionnaire_compte_id")
    private Long gestionnaireCompteId;

    @Column(name = "gestionnaire_compte_nom", length = 200)
    private String gestionnaireCompteNom;

    @Column(name = "gestionnaire_compte_email", length = 200)
    private String gestionnaireCompteEmail;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Version
    private Long version;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Column(name = "tags", length = 500)
    private String tags;

    // Relations bidirectionnelles
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ClientProfil profil;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ClientPreference preference;

    // Méthodes métier
    public void assignGestionnaireCompte(Long id, String nom, String email) {
        this.gestionnaireCompteId = id;
        this.gestionnaireCompteNom = nom;
        this.gestionnaireCompteEmail = email;
    }

    public void removeGestionnaireCompte() {
        this.gestionnaireCompteId = null;
        this.gestionnaireCompteNom = null;
        this.gestionnaireCompteEmail = null;
    }

    public boolean hasGestionnaireCompte() {
        return gestionnaireCompteId != null;
    }

    public void updateDerniereInteraction() {
        this.derniereInteraction = LocalDateTime.now();
    }

    public void softDelete(String deletedBy) {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
        this.statut = ClientStatut.INACTIF;
    }

    public void restore() {
        this.deleted = false;
        this.deletedAt = null;
        this.deletedBy = null;
        this.statut = ClientStatut.ACTIF;
    }

    public boolean isVIP() {
        return segment == ClientSegment.PLATINUM || segment == ClientSegment.DIAMOND;
    }

    public boolean isActif() {
        return statut == ClientStatut.ACTIF && !deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id != null && id.equals(client.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", statut=" + statut +
                ", segment=" + segment +
                '}';
    }
}
