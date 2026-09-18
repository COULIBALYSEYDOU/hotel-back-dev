package projet_hotelier.hotel.module.reporting.document.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentRequest {

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
