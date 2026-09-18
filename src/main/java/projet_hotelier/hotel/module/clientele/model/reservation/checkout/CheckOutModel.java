package projet_hotelier.hotel.module.clientele.model.reservation.checkout;

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
import projet_hotelier.hotel.module.clientele.enumeration.StatutCheckOut;
import projet_hotelier.hotel.module.clientele.enumeration.MoyenPaiement;
import projet_hotelier.hotel.module.clientele.enumeration.NiveauSatisfaction;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Check-out des réservations.
 * Gestion du processus de check-out, facturation finale, libération chambres.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_checkout",
    indexes = {
        @Index(name = "idx_checkout_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_checkout_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_checkout_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_checkout_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_checkout_client", columnList = "clientId"),
        @Index(name = "idx_crm_checkout_date", columnList = "dateCheckOut"),
        @Index(name = "idx_crm_checkout_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CheckOutModel extends BaseEntity {

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
    private LocalDateTime dateCheckOut;

    @Column
    private Long factureId; // Facture finale associée

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 50)
    private StatutCheckOut statut;

    // ========== Référence agent (pattern découplé) ==========
    
    /**
     * ID de l'agent qui a effectué le check-out (référence au module RH)
     * Pattern découplé : pas de relation JPA, uniquement ID
     */
    @Column(name = "agent_checkout_id")
    private Long agentCheckOutId;

    /**
     * Nom de l'agent (copie dénormalisée pour affichage)
     */
    @Column(name = "agent_checkout_nom", length = 200)
    private String agentCheckOutNom;

    /**
     * Email de l'agent (copie dénormalisée pour contact)
     */
    @Column(name = "agent_checkout_email", length = 200)
    private String agentCheckOutEmail;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(name = "heure_depart_prevue")
    private LocalDateTime heureDepartPrevue;

    @Column(name = "duree_attente_minutes")
    private Integer dureeAttenteMinutes;

    @Column(name = "nombre_invites")
    private Integer nombreInvites;

    @Column(name = "nombre_bagages")
    private Integer nombreBagages;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTotal;

    @Column(length = 10)
    private String devise;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantPaye;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRestant;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantDepotGarantieRembourse;

    @Enumerated(EnumType.STRING)
    @Column(name = "moyen_paiement", length = 50)
    private MoyenPaiement moyenPaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "satisfaction", length = 50)
    private NiveauSatisfaction satisfaction;

    @Column(name = "score_satisfaction", precision = 3, scale = 2)
    private BigDecimal scoreSatisfaction; // 0-10

    @Column(name = "recommandation_probable")
    private Boolean recommandationProbable; // NPS

    @Column(name = "raison_depart", length = 200)
    private String raisonDepart;

    @Column(name = "chambre_verifiee")
    @Builder.Default
    private Boolean chambreVerifiee = false;

    @Column(name = "dommages_detectes")
    @Builder.Default
    private Boolean dommagesDetectes = false;

    @Column(columnDefinition = "TEXT")
    private String descriptionDommages;
    @Builder.Default
    private Boolean checkOutPrecoce = false;
    @Builder.Default
    private Boolean checkOutTardif = false;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String notesCheckOut;

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
        if (heureDepartPrevue != null && dateCheckOut != null) {
            long minutes = ChronoUnit.MINUTES.between(heureDepartPrevue, dateCheckOut);
            this.dureeAttenteMinutes = (int) minutes;
            this.checkOutTardif = minutes > 30; // Plus de 30 minutes de retard
        }
    }

    /**
     * Calcule le montant restant à payer
     */
    public void calculateMontantRestant() {
        if (montantTotal != null && montantPaye != null) {
            this.montantRestant = montantTotal.subtract(montantPaye);
        } else if (montantTotal != null) {
            this.montantRestant = montantTotal;
        }
    }

    /**
     * Vérifie si le check-out est complet (tout payé)
     */
    public boolean isCheckOutComplet() {
        return montantRestant != null && 
               montantRestant.compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * Assigne l'agent qui effectue le check-out (synchronisation depuis API RH)
     */
    public void assignAgentCheckOut(Long agentId, String agentNom, String agentEmail) {
        this.agentCheckOutId = agentId;
        this.agentCheckOutNom = agentNom;
        this.agentCheckOutEmail = agentEmail;
    }

    /**
     * Retire l'agent assigné
     */
    public void removeAgentCheckOut() {
        this.agentCheckOutId = null;
        this.agentCheckOutNom = null;
        this.agentCheckOutEmail = null;
    }

    /**
     * Vérifie si un agent est assigné
     */
    public boolean hasAgentCheckOut() {
        return agentCheckOutId != null;
    }

    /**
     * Marque le check-out comme terminé
     */
    public void markAsTermine(Long agentId, String agentNom, String agentEmail) {
        this.statut = StatutCheckOut.TERMINE;
        assignAgentCheckOut(agentId, agentNom, agentEmail);
        calculateDureeAttente();
        calculateMontantRestant();
    }

    /**
     * Convertit le niveau de satisfaction en score (0-10)
     */
    public void calculateScoreSatisfaction() {
        if (satisfaction != null) {
            switch (satisfaction) {
                case TRES_SATISFAIT:
                    this.scoreSatisfaction = BigDecimal.valueOf(9.5);
                    this.recommandationProbable = true;
                    break;
                case SATISFAIT:
                    this.scoreSatisfaction = BigDecimal.valueOf(7.5);
                    this.recommandationProbable = true;
                    break;
                case NEUTRE:
                    this.scoreSatisfaction = BigDecimal.valueOf(5.0);
                    this.recommandationProbable = false;
                    break;
                case INSATISFAIT:
                    this.scoreSatisfaction = BigDecimal.valueOf(3.0);
                    this.recommandationProbable = false;
                    break;
                case TRES_INSATISFAIT:
                    this.scoreSatisfaction = BigDecimal.valueOf(1.0);
                    this.recommandationProbable = false;
                    break;
            }
        }
    }

        // Note: Status est géré par BaseEntity
}
