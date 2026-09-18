package projet_hotelier.hotel.module.rh.mapper.evaluation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import projet_hotelier.hotel.module.rh.dto.request.evaluation.CreateEvaluationRequest;
import projet_hotelier.hotel.module.rh.dto.request.evaluation.UpdateEvaluationRequest;
import projet_hotelier.hotel.module.rh.dto.response.evaluation.EvaluationResponse;
import projet_hotelier.hotel.module.rh.model.evaluation.EvaluationPerformanceModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface EvaluationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "statutEvaluation", constant = "EN_COURS")
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
    @Mapping(target = "scoreGlobal", ignore = true)
    @Mapping(target = "scoreCompetences", ignore = true)
    @Mapping(target = "scoreObjectifs", ignore = true)
    @Mapping(target = "scoreComportement", ignore = true)
    @Mapping(target = "objectifsAtteints", ignore = true)
    @Mapping(target = "objectifsNonAtteints", ignore = true)
    @Mapping(target = "objectifsFuturs", ignore = true)
    @Mapping(target = "pointsForts", ignore = true)
    @Mapping(target = "pointsAmelioration", ignore = true)
    @Mapping(target = "planAction", ignore = true)
    @Mapping(target = "recommandation", ignore = true)
    @Mapping(target = "commentairesEvaluateur", ignore = true)
    @Mapping(target = "commentairesEmploye", ignore = true)
    @Mapping(target = "valideParId", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    @Mapping(target = "notesInternes", ignore = true)
    @Mapping(target = "dateProchaineEvaluation", ignore = true)
    @Mapping(target = "actionsCorrectives", ignore = true)
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "spanId", ignore = true)
    @Mapping(target = "correlationId", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "sourceSystem", ignore = true)
    @Mapping(target = "sourceIp", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    EvaluationPerformanceModel toEntity(CreateEvaluationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "employeId", ignore = true)
    @Mapping(target = "typeEvaluation", ignore = true)
    @Mapping(target = "dateEvaluation", ignore = true)
    @Mapping(target = "periodeDebut", ignore = true)
    @Mapping(target = "periodeFin", ignore = true)
    @Mapping(target = "evaluateurId", ignore = true)
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
    @Mapping(target = "scoreGlobal", ignore = true)
    @Mapping(target = "valideParId", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    @Mapping(target = "notesInternes", ignore = true)
    @Mapping(target = "traceId", ignore = true)
    @Mapping(target = "spanId", ignore = true)
    @Mapping(target = "correlationId", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "sourceSystem", ignore = true)
    @Mapping(target = "sourceIp", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    void updateEntity(@MappingTarget EvaluationPerformanceModel entity, UpdateEvaluationRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    EvaluationResponse toResponse(EvaluationPerformanceModel entity);

    List<EvaluationResponse> toResponseList(List<EvaluationPerformanceModel> entities);

    default AuditDTO mapAudit(EvaluationPerformanceModel entity) {
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

    default TraceDTO mapTrace(EvaluationPerformanceModel entity) {
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
