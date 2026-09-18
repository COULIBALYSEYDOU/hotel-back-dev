package projet_hotelier.hotel.module.finances.model.comptabilite;

import projet_hotelier.hotel.core.organisation.Societe;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.module.finances.model.audit.NiveauRisqueAuditModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CompteComptableModel {

    private Long id;
    private String codeCompte;
    private String libelle;

    private String libelleCourt;
    private String description;

    private Integer classeComptable;
    private String groupeCompte;
    private CompteComptableModel compteParent;
    private List<CompteComptableModel> sousComptes;

    private Integer niveauHierarchique;
    private Boolean estCollectif;
    private Boolean autoriseAuxiliaire;

    private TypeCompteComptable typeCompte;
    private NatureCompte natureCompte;
    private SensNormalCompte sensNormal;

    private BigDecimal soldeOuverture;

    private BigDecimal soldeCourant;
    private BigDecimal soldeCloture;
    private String devise;

    private Boolean multideviseAutorisee;
    private Boolean conversionAutomatique;

    private Boolean imputable;
    private Boolean saisieManuelleAutorisee;
    private Boolean bloque;

    private String motifBlocage;
    private Boolean gele;

    private Boolean analytiqueObligatoire;
    private List<CentreCoutModel> centresCoutAutorises;
    private Boolean ventilationAutomatique;

    private Boolean soumisTVA;
    private BigDecimal tauxTVA;

    private Boolean compteFiscal;
    private Boolean deductibleFiscalement;

    private String codeFiscal;
    private LocalDate dateActivation;

    private LocalDate dateDesactivation;
    private String creePar;
    private LocalDateTime dateCreation;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    private Integer version;
    private Societe societe;
    private Hotel hotel;

    private List<projet_hotelier.hotel.module.finances.model.comptabilite.JournalComptableModel> journauxAutorises;

    private Boolean obligatoire;
    private Boolean compteSysteme;
    private NiveauRisqueAuditModel niveauRisque;

    private String referenceExterne;
    private String commentaireInterne;
}

