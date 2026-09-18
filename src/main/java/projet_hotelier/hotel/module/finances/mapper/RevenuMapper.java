package projet_hotelier.hotel.module.finances.mapper;

import org.mapstruct.*;
import projet_hotelier.hotel.module.finances.dto.request.revenu.CreateRevenuRequest;
import projet_hotelier.hotel.module.finances.dto.response.revenu.RevenuResponse;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.RevenuModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface RevenuMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "codeRevenu", ignore = true)
    @Mapping(target = "montantTVA", ignore = true)
    @Mapping(target = "montantTTC", ignore = true)
    @Mapping(target = "montantEncaisse", ignore = true)
    @Mapping(target = "montantRestant", ignore = true)
    @Mapping(target = "dateEncaissement", ignore = true)
    @Mapping(target = "dateComptabilisation", ignore = true)
    @Mapping(target = "compteContrepartie", ignore = true)
    @Mapping(target = "codeJournal", ignore = true)
    @Mapping(target = "ecritureComptableId", ignore = true)
    @Mapping(target = "clientNom", ignore = true)
    @Mapping(target = "clientCode", ignore = true)
    @Mapping(target = "numeroFacture", ignore = true)
    @Mapping(target = "numeroReservation", ignore = true)
    @Mapping(target = "tvaCollectee", ignore = true)
    @Mapping(target = "montantTVACollectee", ignore = true)
    @Mapping(target = "compteBancaireNom", ignore = true)
    @Mapping(target = "referencePaiement", ignore = true)
    @Mapping(target = "montantCommission", ignore = true)
    @Mapping(target = "montantNetCommission", ignore = true)
    @Mapping(target = "siteId", ignore = true)
    @Mapping(target = "siteNom", ignore = true)
    @Mapping(target = "revenuRecurrent", ignore = true)
    @Mapping(target = "frequenceRecurrence", ignore = true)
    @Mapping(target = "prochaineOccurrence", ignore = true)
    @Mapping(target = "referenceExterne", ignore = true)
    @Mapping(target = "notesInternes", ignore = true)
    @Mapping(target = "signaleAnomalie", ignore = true)
    @Mapping(target = "descriptionAnomalie", ignore = true)
    @Mapping(target = "donneesSensibles", ignore = true)
    @Mapping(target = "baseLegale", ignore = true)
    @Mapping(target = "dureeRetention", ignore = true)
    @Mapping(target = "statutRevenu", constant = "BROUILLON")
    @Mapping(target = "encaisse", constant = "false")
    @Mapping(target = "comptabilise", constant = "false")
    @Mapping(target = "annule", constant = "false")
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
    RevenuModel toEntity(CreateRevenuRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    RevenuResponse toResponse(RevenuModel entity);

    List<RevenuResponse> toResponseList(List<RevenuModel> entities);

    default AuditDTO mapAudit(RevenuModel entity) {
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

    default TraceDTO mapTrace(RevenuModel entity) {
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
