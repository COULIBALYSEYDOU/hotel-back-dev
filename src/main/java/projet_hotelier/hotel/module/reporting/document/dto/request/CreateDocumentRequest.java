package projet_hotelier.hotel.module.reporting.document.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentRequest {

    @NotBlank(message = "Le code document est requis")
    @Size(max = 80, message = "Le code document ne peut pas depasser 80 caracteres")
    private String codeDocument;

    @NotBlank(message = "Le nom est requis")
    @Size(max = 200, message = "Le nom ne peut pas depasser 200 caracteres")
    private String nom;

    private String typeDocument;
    private String classification;
    private String versionDocument;
    private String urlFichier;
    private String mimeType;
    private Long tailleOctets;
    private String hashSha256;
    private Boolean signe;
    private LocalDateTime dateSignature;
    private LocalDateTime dateHorodatage;
    private String statutDocument;
    private LocalDateTime dateExpiration;
    private Long proprietaireId;
    private String referenceExterne;
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
