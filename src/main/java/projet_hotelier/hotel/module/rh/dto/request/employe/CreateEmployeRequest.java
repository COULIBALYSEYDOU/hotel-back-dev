package projet_hotelier.hotel.module.rh.dto.request.employe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateEmployeRequest {

    @NotBlank(message = "Le matricule est requis")
    @Size(max = 50, message = "Le matricule ne peut pas depasser 50 caracteres")
    private String matricule;

    @NotBlank(message = "Le nom est requis")
    @Size(max = 100, message = "Le nom ne peut pas depasser 100 caracteres")
    private String nom;

    @NotBlank(message = "Le prenom est requis")
    @Size(max = 100, message = "Le prenom ne peut pas depasser 100 caracteres")
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

    @NotNull(message = "Le salaire de base est requis")
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
