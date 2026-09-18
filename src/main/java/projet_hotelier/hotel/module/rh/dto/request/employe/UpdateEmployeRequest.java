package projet_hotelier.hotel.module.rh.dto.request.employe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeRequest {

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
    private Boolean rgpdApplicable;
    private Boolean donneesSensibles;
    private String baseLegale;
    private String retention;
    private LocalDate dateSortie;
    private String motifSortie;

    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;
}
