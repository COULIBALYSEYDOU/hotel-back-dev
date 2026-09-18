package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.geo.AdresseGeo;
import projet_hotelier.hotel.core.geo.Devise;
import projet_hotelier.hotel.core.organisation.enumeration.TypeSociete;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "societe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Societe extends BaseEntity {

    private String raisonSociale;
    private String nomCommercial;

    @Enumerated(EnumType.STRING)
    private TypeSociete typeSociete;

    private String secteurActivite;

    private String numeroRegistreCommerce;
    private String numeroIdentificationFiscale;
    private String numeroTVA;
    private String numeroPatente;

    private String siteWeb;

    private String emailGeneral;
    private String telephoneGeneral;

    @ManyToOne
    private AdresseGeo adresseSiege;

    @ManyToOne
    private AdresseGeo adresseOperationnelle;

    @ManyToOne
    private AdresseGeo adresseFacturation;

    @ManyToOne
    private Devise devisePrincipale;

    private Boolean facturationActive;
    private String methodeFacturation;
    private String termePaiement;
    private Boolean factureElectronique;

    @OneToMany
    private List<ContactSociete> contacts;

    private String banque;
    private String numeroCompte;
    private String swiftCode;
    private String iban;
    private String nomTitulaireCompte;

    private Boolean kycValide;
    private LocalDateTime dateKyc;
    private String statutKyc;
    private String raisonRejetKyc;

    @Column(columnDefinition = "TEXT")
    private String documentsKycJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Status statut;

    private Boolean clientActif;
    private Boolean fournisseurActif;
    private Boolean partenaireActif;

    private Boolean multiHotel;
    private Integer nombreMaxHotels;

    private String segment;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
