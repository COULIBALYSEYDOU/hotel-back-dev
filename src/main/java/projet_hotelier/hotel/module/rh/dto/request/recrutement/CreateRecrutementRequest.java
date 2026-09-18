package projet_hotelier.hotel.module.rh.dto.request.recrutement;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecrutementRequest {

    @NotBlank(message = "Le poste est requis")
    private String poste;

    private String departement;

    @NotBlank(message = "Le nom du candidat est requis")
    private String candidatNom;

    private String candidatEmail;

    private String candidatTelephone;

    private String source;

    private String statutCandidature;

    private LocalDate dateCandidature;

    private LocalDate dateEntretien;

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
