package projet_hotelier.hotel.module.clientele.model.reservation.checkin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.module.clientele.enumeration.StatutCheckIn;
import projet_hotelier.hotel.module.clientele.enumeration.MoyenPaiement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Check-in des réservations.
 * Gestion du processus de check-in, validation, attribution de chambres.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_checkin",
    indexes = {
        @Index(name = "idx_checkin_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_checkin_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_checkin_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_checkin_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_checkin_client", columnList = "clientId"),
        @Index(name = "idx_crm_checkin_date", columnList = "dateCheckIn"),
        @Index(name = "idx_crm_checkin_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CheckInModel extends BaseEntity {

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
    private Long reservationId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private LocalDateTime dateCheckIn;

    @Column
    private Long chambreId; // Chambre attribuée

    @Column(length = 50)
    private String numeroChambre;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 50)
    private StatutCheckIn statut;

    // ========== Référence agent (pattern découplé) ==========
    
    /**
     * ID de l'agent qui a effectué le check-in (référence au module RH)
     * Pattern découplé : pas de relation JPA, uniquement ID
     */
    @Column(name = "agent_checkin_id")
    private Long agentCheckInId;

    /**
     * Nom de l'agent (copie dénormalisée pour affichage)
     */
    @Column(name = "agent_checkin_nom", length = 200)
    private String agentCheckInNom;

    /**
     * Email de l'agent (copie dénormalisée pour contact)
     */
    @Column(name = "agent_checkin_email", length = 200)
    private String agentCheckInEmail;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(name = "heure_arrivee_prevue")
    private LocalDateTime heureArriveePrevue;

    @Column(name = "duree_attente_minutes")
    private Integer dureeAttenteMinutes;

    @Column(name = "nombre_invites")
    private Integer nombreInvites;

    @Column(name = "nombre_bagages")
    private Integer nombreBagages;

    @Column(name = "demande_chambre_haute_etage")
    @Builder.Default
    private Boolean demandeChambreHauteEtage = false;

    @Column(name = "demande_vue_mer")
    @Builder.Default
    private Boolean demandeVueMer = false;

    @Column(name = "demande_lit_double")
    @Builder.Default
    private Boolean demandeLitDouble = false;

    @Column(name = "demande_lit_simple")
    @Builder.Default
    private Boolean demandeLitSimple = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "moyen_paiement", length = 50)
    private MoyenPaiement moyenPaiement;

    @Column(columnDefinition = "TEXT")
    private String documentsVerifies; // Liste des documents vérifiés (JSON)

    @Column(name = "carte_identite_verifiee")
    @Builder.Default
    private Boolean carteIdentiteVerifiee = false;

    @Column(name = "carte_credit_verifiee")
    @Builder.Default
    private Boolean carteCreditVerifiee = false;

    @Column(name = "depot_garantie_requis")
    @Builder.Default
    private Boolean depotGarantieRequis = false;

    @Column(name = "montant_depot_garantie", precision = 18, scale = 2)
    private java.math.BigDecimal montantDepotGarantie;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String notesCheckIn;
    @Builder.Default
    private Boolean checkInPrecoce = false;
    @Builder.Default
    private Boolean checkInTardif = false;

    @Column(columnDefinition = "TEXT")
    private String remarques;

    /**
     * Marque l'entité comme supprimée (soft delete)
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
        // BaseEntity gère deletedAt et deletedBy via les annotations
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
        return this.getSupprime() != null && this.getSupprime();
    }

    // ========== Méthodes métier ==========

    /**
     * Calcule la durée d'attente en minutes
     */
    public void calculateDureeAttente() {
        if (heureArriveePrevue != null && dateCheckIn != null) {
            long minutes = ChronoUnit.MINUTES.between(heureArriveePrevue, dateCheckIn);
            this.dureeAttenteMinutes = (int) minutes;
            this.checkInTardif = minutes > 30; // Plus de 30 minutes de retard
        }
    }

    /**
     * Vérifie si le check-in est en retard
     */
    public boolean isCheckInRetarde() {
        return dureeAttenteMinutes != null && dureeAttenteMinutes > 30;
    }

    /**
     * Vérifie si tous les documents sont vérifiés
     */
    public boolean areDocumentsVerifies() {
        return carteIdentiteVerifiee != null && carteIdentiteVerifiee &&
               (carteCreditVerifiee == null || !carteCreditVerifiee || carteCreditVerifiee);
    }

    /**
     * Assigne l'agent qui effectue le check-in (synchronisation depuis API RH)
     */
    public void assignAgentCheckIn(Long agentId, String agentNom, String agentEmail) {
        this.agentCheckInId = agentId;
        this.agentCheckInNom = agentNom;
        this.agentCheckInEmail = agentEmail;
    }

    /**
     * Retire l'agent assigné
     */
    public void removeAgentCheckIn() {
        this.agentCheckInId = null;
        this.agentCheckInNom = null;
        this.agentCheckInEmail = null;
    }

    /**
     * Vérifie si un agent est assigné
     */
    public boolean hasAgentCheckIn() {
        return agentCheckInId != null;
    }

    /**
     * Marque le check-in comme terminé
     */
    public void markAsTermine(Long agentId, String agentNom, String agentEmail) {
        this.statut = StatutCheckIn.TERMINE;
        assignAgentCheckIn(agentId, agentNom, agentEmail);
        calculateDureeAttente();
    }

        // Note: Status est géré par BaseEntity
}
