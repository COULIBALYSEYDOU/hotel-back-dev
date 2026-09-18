package projet_hotelier.hotel.module.finances.mapper;

import org.mapstruct.*;
import projet_hotelier.hotel.module.finances.dto.request.budget.CreateBudgetRequest;
import projet_hotelier.hotel.module.finances.dto.request.budget.UpdateBudgetRequest;
import projet_hotelier.hotel.module.finances.dto.response.budget.BudgetResponse;
import projet_hotelier.hotel.module.finances.model.budget.BudgetModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface BudgetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "montantTotalReel", ignore = true)
    @Mapping(target = "montantTotalEcart", ignore = true)
    @Mapping(target = "montantTotalReste", ignore = true)
    @Mapping(target = "scenarioBaseJson", ignore = true)
    @Mapping(target = "scenarioOptimisteJson", ignore = true)
    @Mapping(target = "scenarioPessimisteJson", ignore = true)
    @Mapping(target = "statutBudget", constant = "BROUILLON")
    @Mapping(target = "soumis", constant = "false")
    @Mapping(target = "approuve", constant = "false")
    @Mapping(target = "archivé", constant = "false")
    @Mapping(target = "dateSoumission", ignore = true)
    @Mapping(target = "dateApprobation", ignore = true)
    @Mapping(target = "dateRejet", ignore = true)
    @Mapping(target = "tauxExecution", ignore = true)
    @Mapping(target = "burnRate", ignore = true)
    @Mapping(target = "forecast", ignore = true)
    @Mapping(target = "versionRevision", constant = "1")
    @Mapping(target = "dateRevision", ignore = true)
    @Mapping(target = "revisionCommentaire", ignore = true)
    @Mapping(target = "lignes", ignore = true)
    @Mapping(target = "responsableBudgetNom", ignore = true)
    @Mapping(target = "responsableBudgetEmail", ignore = true)
    @Mapping(target = "integrationFacturation", ignore = true)
    @Mapping(target = "integrationAchat", ignore = true)
    @Mapping(target = "integrationRH", ignore = true)
    @Mapping(target = "integrationStock", ignore = true)
    @Mapping(target = "donneesSensibles", ignore = true)
    @Mapping(target = "donneesFinancieres", ignore = true)
    @Mapping(target = "rgpdApplicable", ignore = true)
    @Mapping(target = "baseLegale", ignore = true)
    @Mapping(target = "retention", ignore = true)
    @Mapping(target = "referenceAudit", ignore = true)
    @Mapping(target = "checksum", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "creePar", ignore = true)
    @Mapping(target = "modifiePar", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "supprime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "organisationId", ignore = true)
    @Mapping(target = "metadataJson", ignore = true)
    BudgetModel toEntity(CreateBudgetRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "codeBudget", ignore = true)
    @Mapping(target = "devise", ignore = true)
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
    @Mapping(target = "lignes", ignore = true)
    @Mapping(target = "montantTotalReel", ignore = true)
    @Mapping(target = "montantTotalEcart", ignore = true)
    @Mapping(target = "montantTotalReste", ignore = true)
    @Mapping(target = "scenarioBaseJson", ignore = true)
    @Mapping(target = "scenarioOptimisteJson", ignore = true)
    @Mapping(target = "scenarioPessimisteJson", ignore = true)
    @Mapping(target = "statutBudget", ignore = true)
    @Mapping(target = "validationRequise", ignore = true)
    @Mapping(target = "soumis", ignore = true)
    @Mapping(target = "approuve", ignore = true)
    @Mapping(target = "archivé", ignore = true)
    @Mapping(target = "dateSoumission", ignore = true)
    @Mapping(target = "dateApprobation", ignore = true)
    @Mapping(target = "dateRejet", ignore = true)
    @Mapping(target = "responsableBudgetNom", ignore = true)
    @Mapping(target = "responsableBudgetEmail", ignore = true)
    @Mapping(target = "tauxExecution", ignore = true)
    @Mapping(target = "burnRate", ignore = true)
    @Mapping(target = "forecast", ignore = true)
    @Mapping(target = "versionRevision", ignore = true)
    @Mapping(target = "dateRevision", ignore = true)
    @Mapping(target = "integrationFacturation", ignore = true)
    @Mapping(target = "integrationAchat", ignore = true)
    @Mapping(target = "integrationRH", ignore = true)
    @Mapping(target = "integrationStock", ignore = true)
    @Mapping(target = "donneesSensibles", ignore = true)
    @Mapping(target = "donneesFinancieres", ignore = true)
    @Mapping(target = "rgpdApplicable", ignore = true)
    @Mapping(target = "baseLegale", ignore = true)
    @Mapping(target = "retention", ignore = true)
    @Mapping(target = "referenceAudit", ignore = true)
    @Mapping(target = "checksum", ignore = true)
    @Mapping(target = "metadataJson", ignore = true)
    void updateEntity(@MappingTarget BudgetModel entity, UpdateBudgetRequest request);

    @Mapping(target = "archive", source = "archivé")
    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    BudgetResponse toResponse(BudgetModel entity);

    List<BudgetResponse> toResponseList(List<BudgetModel> entities);

    default AuditDTO mapAudit(BudgetModel entity) {
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

    default TraceDTO mapTrace(BudgetModel entity) {
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
