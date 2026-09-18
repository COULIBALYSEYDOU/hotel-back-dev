package projet_hotelier.hotel.module.reporting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import projet_hotelier.hotel.module.reporting.dto.request.CreateRapportRequest;
import projet_hotelier.hotel.module.reporting.dto.request.UpdateRapportRequest;
import projet_hotelier.hotel.module.reporting.dto.response.RapportResponse;
import projet_hotelier.hotel.module.reporting.model.RapportModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface RapportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "statutRapport", constant = "BROUILLON")
    @Mapping(target = "urlFichier", ignore = true)
    @Mapping(target = "dateGeneration", ignore = true)
    @Mapping(target = "metadataJson", ignore = true)
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
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "spanId", ignore = true)
    @Mapping(target = "correlationId", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "sourceSystem", ignore = true)
    @Mapping(target = "sourceIp", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    RapportModel toEntity(CreateRapportRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "codeRapport", ignore = true)
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
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "spanId", ignore = true)
    @Mapping(target = "correlationId", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "sourceSystem", ignore = true)
    @Mapping(target = "sourceIp", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    void updateEntity(@MappingTarget RapportModel entity, UpdateRapportRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    RapportResponse toResponse(RapportModel entity);

    List<RapportResponse> toResponseList(List<RapportModel> entities);

    default AuditDTO mapAudit(RapportModel entity) {
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

    default TraceDTO mapTrace(RapportModel entity) {
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
