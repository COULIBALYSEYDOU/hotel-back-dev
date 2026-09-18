package projet_hotelier.hotel.module.rh.dto.response.employe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeResponse {

    private Long id;
    private String uuid;
    private String matricule;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String email;
    private String telephone;
    private String adresse;
    private String poste;
    private String departement;
    private LocalDate dateEmbauche;
    private String statutEmploye;
    private String typeContrat;
    private BigDecimal salaireBase;
    private String cnpsNumero;
    private String nif;
    private String nationalite;
    private String banque;
    private String rib;
    private String contactUrgenceNom;
    private String contactUrgenceTelephone;
    private String notesInternes;
    private boolean rgpdApplicable;
    private boolean donneesSensibles;
    private String baseLegale;
    private String retention;
    private LocalDate dateSortie;
    private String motifSortie;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
