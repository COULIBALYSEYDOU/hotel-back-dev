package projet_hotelier.hotel.module.finances.model.comptabilite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.time.LocalDate;

/**
 * Modele de journal comptable conforme OHADA.
 */
@Entity
@Table(name = "finance_journal_comptable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class JournalComptableModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String codeJournal;

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 30)
    private String typeJournal; // ACHATS, VENTES, BANQUE, CAISSE, OPERATIONS_DIVERSES, A_NOUVEAUX

    // Compte par defaut
    @Column(length = 20)
    private String compteDebitDefaut;

    @Column(length = 20)
    private String compteCreditDefaut;

    // Comptabilisation automatique
    private boolean comptabilisationAutomatique;

    private boolean validationRequise;

    // Numerotation
    @Column(length = 10)
    private String prefixeNumero;

    private Long dernierNumero;

    @Column(length = 20)
    private String formatNumero;

    // Periode
    private LocalDate dateDebut;

    private LocalDate dateFin;

    // Parametres
    private boolean actifOperationnel;

    private boolean editionCloturee;

    private boolean extourneAutomatique;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Audit
    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
