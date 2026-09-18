package projet_hotelier.hotel.module.reporting.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRapportRequest {

    @NotBlank(message = "Le code rapport est requis")
    @Size(max = 50, message = "Le code rapport ne peut pas depasser 50 caracteres")
    private String codeRapport;

    @NotBlank(message = "Le nom est requis")
    @Size(max = 150, message = "Le nom ne peut pas depasser 150 caracteres")
    private String nom;

    private String typeRapport;
    private String description;
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private String formatSortie;
    private String parametresJson;
    private String scheduleCron;
    private Boolean planifie;
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
