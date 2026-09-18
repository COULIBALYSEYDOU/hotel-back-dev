package projet_hotelier.hotel.module.clientele.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import projet_hotelier.hotel.module.clientele.dto.request.avis.CreateAvisClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.avis.UpdateAvisClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.avis.AvisClientResponse;
import projet_hotelier.hotel.module.clientele.model.avis.AvisClientModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface AvisClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "statutTraitement", constant = "EN_ATTENTE")
    @Mapping(target = "reponse", ignore = true)
    @Mapping(target = "dateReponse", ignore = true)
    @Mapping(target = "sejourId", ignore = true)
    @Mapping(target = "noteChambre", ignore = true)
    @Mapping(target = "noteService", ignore = true)
    @Mapping(target = "noteRestauration", ignore = true)
    @Mapping(target = "notePersonnel", ignore = true)
    @Mapping(target = "recommandationProbable", ignore = true)
    @Mapping(target = "sourceAvis", ignore = true)
    @Mapping(target = "identifiantAvisExterne", ignore = true)
    @Mapping(target = "nombreLikes", ignore = true)
    @Mapping(target = "nombreDislikes", ignore = true)
    @Mapping(target = "nombreSignales", ignore = true)
    @Mapping(target = "modereParId", ignore = true)
    @Mapping(target = "modereParNom", ignore = true)
    @Mapping(target = "modereParEmail", ignore = true)
    @Mapping(target = "dateModeration", ignore = true)
    @Mapping(target = "reponseParId", ignore = true)
    @Mapping(target = "reponseParNom", ignore = true)
    @Mapping(target = "reponseParEmail", ignore = true)
    @Mapping(target = "datePublication", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "notes", ignore = true)
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
    AvisClientModel toEntity(CreateAvisClientRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "reservationId", ignore = true)
    @Mapping(target = "sejourId", ignore = true)
    @Mapping(target = "noteChambre", ignore = true)
    @Mapping(target = "noteService", ignore = true)
    @Mapping(target = "noteRestauration", ignore = true)
    @Mapping(target = "notePersonnel", ignore = true)
    @Mapping(target = "recommandationProbable", ignore = true)
    @Mapping(target = "sourceAvis", ignore = true)
    @Mapping(target = "identifiantAvisExterne", ignore = true)
    @Mapping(target = "nombreLikes", ignore = true)
    @Mapping(target = "nombreDislikes", ignore = true)
    @Mapping(target = "nombreSignales", ignore = true)
    @Mapping(target = "modereParId", ignore = true)
    @Mapping(target = "modereParNom", ignore = true)
    @Mapping(target = "modereParEmail", ignore = true)
    @Mapping(target = "dateModeration", ignore = true)
    @Mapping(target = "reponseParId", ignore = true)
    @Mapping(target = "reponseParNom", ignore = true)
    @Mapping(target = "reponseParEmail", ignore = true)
    @Mapping(target = "datePublication", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "notes", ignore = true)
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
    void updateEntity(@MappingTarget AvisClientModel entity, UpdateAvisClientRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    AvisClientResponse toResponse(AvisClientModel entity);

    List<AvisClientResponse> toResponseList(List<AvisClientModel> entities);

    default AuditDTO mapAudit(AvisClientModel entity) {
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

    default TraceDTO mapTrace(AvisClientModel entity) {
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
