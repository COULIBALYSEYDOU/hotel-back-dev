package projet_hotelier.hotel.module.planning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import projet_hotelier.hotel.module.planning.dto.request.channel.CreateChannelDistributionRequest;
import projet_hotelier.hotel.module.planning.dto.request.channel.UpdateChannelDistributionRequest;
import projet_hotelier.hotel.module.planning.dto.response.channel.ChannelDistributionResponse;
import projet_hotelier.hotel.module.planning.model.channel.ChannelDistributionModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface ChannelDistributionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "actif", expression = "java(request.getActif() != null ? request.getActif() : true)")
    @Mapping(target = "modeSync", expression = "java(request.getModeSync() != null ? request.getModeSync() : \"MANUEL\")")
    @Mapping(target = "derniereSync", ignore = true)
    @Mapping(target = "statutSync", constant = "NON_SYNCHRO")
    @Mapping(target = "metadataJson", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "creePar", ignore = true)
    @Mapping(target = "modifiePar", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "supprime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "organisationId", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    ChannelDistributionModel toEntity(CreateChannelDistributionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
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
    void updateEntity(@MappingTarget ChannelDistributionModel entity, UpdateChannelDistributionRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    ChannelDistributionResponse toResponse(ChannelDistributionModel entity);

    List<ChannelDistributionResponse> toResponseList(List<ChannelDistributionModel> entities);

    default AuditDTO mapAudit(ChannelDistributionModel entity) {
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

    default TraceDTO mapTrace(ChannelDistributionModel entity) {
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
