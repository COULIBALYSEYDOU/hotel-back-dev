package projet_hotelier.hotel.module.reporting.document.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private Long id;
    private String uuid;
    private String codeDocument;
    private String nom;
    private String typeDocument;
    private String classification;
    private String versionDocument;
    private String urlFichier;
    private String mimeType;
    private Long tailleOctets;
    private String hashSha256;
    private boolean signe;
    private LocalDateTime dateSignature;
    private LocalDateTime dateHorodatage;
    private String statutDocument;
    private LocalDateTime dateExpiration;
    private Long proprietaireId;
    private String referenceExterne;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
