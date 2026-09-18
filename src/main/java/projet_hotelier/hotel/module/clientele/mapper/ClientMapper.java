package projet_hotelier.hotel.module.clientele.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientResponse;
import projet_hotelier.hotel.module.clientele.model.client.ClientModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "spanId", ignore = true)
    @Mapping(target = "correlationId", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "sourceSystem", ignore = true)
    @Mapping(target = "sourceIp", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    @Mapping(target = "codeClient", ignore = true)
    @Mapping(target = "adresse", ignore = true)
    @Mapping(target = "ville", ignore = true)
    @Mapping(target = "pays", ignore = true)
    @Mapping(target = "languePreferee", ignore = true)
    @Mapping(target = "statutClient", ignore = true)
    @Mapping(target = "sourceAcquisition", ignore = true)
    @Mapping(target = "preferencesJson", ignore = true)
    @Mapping(target = "consentementRgpd", ignore = true)
    @Mapping(target = "derniereInteraction", ignore = true)
    @Mapping(target = "chiffreAffairesAnneeEnCours", ignore = true)
    @Mapping(target = "panierMoyen", ignore = true)
    @Mapping(target = "scoreSatisfaction", ignore = true)
    @Mapping(target = "lastAutoSegmentation", ignore = true)
    @Mapping(target = "entreprise", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "pointsFidelite", constant = "0")
    @Mapping(target = "niveauFidelite", constant = "STANDARD")
    @Mapping(target = "dateConsentement", ignore = true)
    @Mapping(target = "derniereVisite", ignore = true)
    @Mapping(target = "nombreSejours", ignore = true)
    @Mapping(target = "valeurVieClient", ignore = true)
    @Mapping(target = "statutRelation", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "creePar", ignore = true)
    @Mapping(target = "modifiePar", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "supprime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "organisationId", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    @Mapping(target = "metadataJson", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    ClientModel toEntity(CreateClientRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "spanId", ignore = true)
    @Mapping(target = "correlationId", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "sourceSystem", ignore = true)
    @Mapping(target = "sourceIp", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    @Mapping(target = "codeClient", ignore = true)
    @Mapping(target = "adresse", ignore = true)
    @Mapping(target = "ville", ignore = true)
    @Mapping(target = "pays", ignore = true)
    @Mapping(target = "languePreferee", ignore = true)
    @Mapping(target = "statutClient", ignore = true)
    @Mapping(target = "sourceAcquisition", ignore = true)
    @Mapping(target = "preferencesJson", ignore = true)
    @Mapping(target = "consentementRgpd", ignore = true)
    @Mapping(target = "niveauFidelite", ignore = true)
    @Mapping(target = "derniereInteraction", ignore = true)
    @Mapping(target = "chiffreAffairesAnneeEnCours", ignore = true)
    @Mapping(target = "panierMoyen", ignore = true)
    @Mapping(target = "scoreSatisfaction", ignore = true)
    @Mapping(target = "lastAutoSegmentation", ignore = true)
    @Mapping(target = "entreprise", ignore = true)
    @Mapping(target = "statutRelation", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "creePar", ignore = true)
    @Mapping(target = "modifiePar", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "supprime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "organisationId", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    @Mapping(target = "metadataJson", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "dateConsentement", ignore = true)
    @Mapping(target = "pointsFidelite", ignore = true)
    @Mapping(target = "derniereVisite", ignore = true)
    @Mapping(target = "nombreSejours", ignore = true)
    @Mapping(target = "valeurVieClient", ignore = true)
    void updateEntity(@MappingTarget ClientModel entity, UpdateClientRequest request);

    @Mapping(target = "nomComplet", expression = "java(entity.getPrenom() != null && !entity.getPrenom().isEmpty() ? entity.getNom() + \" \" + entity.getPrenom() : entity.getNom())")
    @Mapping(target = "statut", expression = "java(entity.getStatus() != null ? entity.getStatus().toString() : null)")
    @Mapping(target = "nombreReservations", ignore = true)
    @Mapping(target = "createdAt", source = "dateCreation")
    @Mapping(target = "createdBy", source = "creePar")
    @Mapping(target = "modifiedAt", source = "dateModification")
    @Mapping(target = "modifiedBy", source = "modifiePar")
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "organisationId", expression = "java(entity.getOrganisationId() != null ? entity.getOrganisationId().toString() : null)")
    @Mapping(target = "hotelId", expression = "java(entity.getHotelId() != null ? entity.getHotelId().toString() : null)")
    @Mapping(target = "segment", expression = "java(entity.getSegment() != null ? entity.getSegment().toString() : null)")
    @Mapping(target = "typeClient", expression = "java(entity.getTypeClient() != null ? entity.getTypeClient().toString() : null)")
    @Mapping(target = "risqueChurn", expression = "java(entity.getRisqueChurn() != null ? entity.getRisqueChurn().toString() : null)")
    @Mapping(target = "civilite", expression = "java(entity.getCivilite() != null ? entity.getCivilite().toString() : null)")
    ClientResponse toResponse(ClientModel entity);

    List<ClientResponse> toResponseList(List<ClientModel> entities);

    default AuditDTO mapAudit(ClientModel entity) {
        if (entity == null) return null;
        return AuditDTO.builder()
                .uuid(entity.getUuid())
                .dateCreation(entity.getDateCreation())
                .dateModification(entity.getDateModification())
                .creePar(entity.getCreePar())
                .modifiePar(entity.getModifiePar())
                .version(entity.getVersion())
                .actif(entity.getActif())
                .build();
    }

    default TraceDTO mapTrace(ClientModel entity) {
        if (entity == null) return null;
        return TraceDTO.builder()
                .traceId(entity.getTraceId())
                .spanId(entity.getSpanId())
                .correlationId(entity.getCorrelationId())
                .requestId(entity.getRequestId())
                .operationId(entity.getOperationId())
                .idempotencyKey(entity.getIdempotencyKey())
                .sourceSystem(entity.getSourceSystem())
                .sourceIp(entity.getSourceIp())
                .userAgent(entity.getUserAgent())
                .build();
    }
}
