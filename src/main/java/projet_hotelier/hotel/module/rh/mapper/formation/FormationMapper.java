package projet_hotelier.hotel.module.rh.mapper.formation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import projet_hotelier.hotel.module.rh.dto.request.formation.CreateFormationRequest;
import projet_hotelier.hotel.module.rh.dto.request.formation.UpdateFormationRequest;
import projet_hotelier.hotel.module.rh.dto.response.formation.FormationResponse;
import projet_hotelier.hotel.module.rh.model.formation.FormationModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface FormationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "statutFormation", constant = "PLANIFIEE")
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
    FormationModel toEntity(CreateFormationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "employeId", ignore = true)
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
    void updateEntity(@MappingTarget FormationModel entity, UpdateFormationRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    FormationResponse toResponse(FormationModel entity);

    List<FormationResponse> toResponseList(List<FormationModel> entities);

    default AuditDTO mapAudit(FormationModel entity) {
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

    default TraceDTO mapTrace(FormationModel entity) {
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
