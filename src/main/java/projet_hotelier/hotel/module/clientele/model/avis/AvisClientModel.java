package projet_hotelier.hotel.module.clientele.model.avis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.module.clientele.enumeration.TypeAvis;
import projet_hotelier.hotel.module.clientele.enumeration.StatutTraitementAvis;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_avis_client",
    indexes = {
        @Index(name = "idx_crm_avis_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_avis_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_avis_client", columnList = "clientId"),
        @Index(name = "idx_crm_avis_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AvisClientModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

    // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)

    @Column(nullable = false)
    private Long clientId;

    private Long reservationId;

    private Long sejourId;

    // ========== Enrichissements SaaS Enterprise ==========

    @Min(1)
    @Max(10)
    @Column(name = "note", nullable = false)
    private Integer note;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_avis", length = 50)
    private TypeAvis typeAvis;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_traitement", length = 50)
    private StatutTraitementAvis statutTraitement;

    @Column(name = "note_chambre", precision = 3, scale = 2)
    private java.math.BigDecimal noteChambre; // 0-10

    @Column(name = "note_service", precision = 3, scale = 2)
    private java.math.BigDecimal noteService; // 0-10

    @Column(name = "note_restauration", precision = 3, scale = 2)
    private java.math.BigDecimal noteRestauration; // 0-10

    @Column(name = "note_personnel", precision = 3, scale = 2)
    private java.math.BigDecimal notePersonnel; // 0-10

    @Column(name = "recommandation_probable")
    private Boolean recommandationProbable; // NPS

    @Column(name = "source_avis", length = 50)
    private String sourceAvis; // INTERNE, BOOKING, TRIPADVISOR, GOOGLE, etc.

    @Column(name = "identifiant_avis_externe", length = 100)
    private String identifiantAvisExterne;

    @Column(name = "nombre_likes")
    @Builder.Default
    private Integer nombreLikes = 0;

    @Column(name = "nombre_dislikes")
    @Builder.Default
    private Integer nombreDislikes = 0;

    @Column(name = "nombre_signales")
    @Builder.Default
    private Integer nombreSignales = 0;

    // ========== Références utilisateurs (pattern découplé) ==========
    
    /**
     * ID de l'utilisateur qui a modéré l'avis (référence au module RH)
     */
    @Column(name = "modere_par_id")
    private Long modereParId;

    @Column(name = "modere_par_nom", length = 200)
    private String modereParNom;

    @Column(name = "modere_par_email", length = 200)
    private String modereParEmail;

    @Column(name = "date_moderation")
    private LocalDateTime dateModeration;

    @Column(columnDefinition = "TEXT")
    private String reponse;

    /**
     * ID de l'utilisateur qui a répondu à l'avis (référence au module RH)
     */
    @Column(name = "reponse_par_id")
    private Long reponseParId;

    @Column(name = "reponse_par_nom", length = 200)
    private String reponseParNom;

    @Column(name = "reponse_par_email", length = 200)
    private String reponseParEmail;

    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;

    @Column(name = "date_avis")
    private LocalDateTime dateAvis;

    @Column(name = "date_publication")
    private LocalDateTime datePublication;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Note: Traçabilité technique centralisée dans BaseEntity
    // (traceId, spanId, correlationId, requestId, operationId, idempotencyKey, sourceSystem, sourceIp, userAgent)

    
    /**
     * Marque l'entité comme supprimée (soft delete)
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
    }

    /**
     * Restaure une entité supprimée
     */
    public void restore() {
        this.setSupprime(false);
    }

    /**
     * Vérifie si l'entité est supprimée
     */
    public boolean isDeleted() {
        return Boolean.TRUE.equals(this.getSupprime());
    }

    // ========== Méthodes métier ==========

    /**
     * Calcule la note moyenne globale
     */
    public void calculateNoteMoyenne() {
        int count = 0;
        double sum = 0.0;

        if (noteChambre != null) {
            sum += noteChambre.doubleValue();
            count++;
        }
        if (noteService != null) {
            sum += noteService.doubleValue();
            count++;
        }
        if (noteRestauration != null) {
            sum += noteRestauration.doubleValue();
            count++;
        }
        if (notePersonnel != null) {
            sum += notePersonnel.doubleValue();
            count++;
        }

        if (count > 0) {
            this.note = (int) Math.round(sum / count);
        }
    }

    /**
     * Détermine si l'avis est recommandable (NPS)
     */
    public void calculateRecommandationProbable() {
        if (note != null) {
            this.recommandationProbable = note >= 8; // Promoters (8-10)
        }
    }

    /**
     * Marque l'avis comme publié
     */
    public void markAsPublie() {
        this.statutTraitement = StatutTraitementAvis.PUBLIE;
        this.datePublication = LocalDateTime.now();
    }

    /**
     * Assigne l'utilisateur qui modère l'avis (synchronisation depuis API RH)
     */
    public void assignModerePar(Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.modereParId = utilisateurId;
        this.modereParNom = utilisateurNom;
        this.modereParEmail = utilisateurEmail;
    }

    /**
     * Assigne l'utilisateur qui répond à l'avis (synchronisation depuis API RH)
     */
    public void assignReponsePar(Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.reponseParId = utilisateurId;
        this.reponseParNom = utilisateurNom;
        this.reponseParEmail = utilisateurEmail;
    }

    /**
     * Marque l'avis comme modéré
     */
    public void markAsModere(Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.statutTraitement = StatutTraitementAvis.MODERE;
        assignModerePar(utilisateurId, utilisateurNom, utilisateurEmail);
        this.dateModeration = LocalDateTime.now();
    }

    /**
     * Ajoute une réponse à l'avis
     */
    public void addReponse(String reponse, Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.reponse = reponse;
        assignReponsePar(utilisateurId, utilisateurNom, utilisateurEmail);
        this.dateReponse = LocalDateTime.now();
    }

    /**
     * Incrémente le nombre de likes
     */
    public void incrementLikes() {
        this.nombreLikes = (this.nombreLikes == null ? 0 : this.nombreLikes) + 1;
    }

    /**
     * Incrémente le nombre de signalements
     */
    public void incrementSignales() {
        this.nombreSignales = (this.nombreSignales == null ? 0 : this.nombreSignales) + 1;
    }

    // Note: Status est géré par BaseEntity
}
