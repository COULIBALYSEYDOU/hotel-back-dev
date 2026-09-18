package projet_hotelier.hotel.module.finances.mapper;

import org.mapstruct.*;
import projet_hotelier.hotel.module.finances.dto.request.paiement.CreatePaiementRequest;
import projet_hotelier.hotel.module.finances.dto.response.paiement.PaiementResponse;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.PaiementModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface PaiementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "codePaiement", ignore = true)
    @Mapping(target = "dateComptabilisation", ignore = true)
    @Mapping(target = "montantNet", ignore = true)
    @Mapping(target = "montantDeviseOrigine", ignore = true)
    @Mapping(target = "deviseOrigine", ignore = true)
    @Mapping(target = "tauxChange", ignore = true)
    @Mapping(target = "compteBancaireNom", ignore = true)
    @Mapping(target = "iban", ignore = true)
    @Mapping(target = "numeroFacture", ignore = true)
    @Mapping(target = "clientNom", ignore = true)
    @Mapping(target = "fournisseurNom", ignore = true)
    @Mapping(target = "compteComptable", ignore = true)
    @Mapping(target = "compteContrepartie", ignore = true)
    @Mapping(target = "codeJournal", ignore = true)
    @Mapping(target = "ecritureComptableId", ignore = true)
    @Mapping(target = "valideurId", ignore = true)
    @Mapping(target = "valideurNom", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    @Mapping(target = "commentaireValidation", ignore = true)
    @Mapping(target = "rapprochementId", ignore = true)
    @Mapping(target = "dateRapprochement", ignore = true)
    @Mapping(target = "siteId", ignore = true)
    @Mapping(target = "siteNom", ignore = true)
    @Mapping(target = "justificatifNom", ignore = true)
    @Mapping(target = "justificatifPresent", ignore = true)
    @Mapping(target = "referenceExterne", ignore = true)
    @Mapping(target = "signaleAnomalie", ignore = true)
    @Mapping(target = "descriptionAnomalie", ignore = true)
    @Mapping(target = "statutPaiement", constant = "BROUILLON")
    @Mapping(target = "valide", constant = "false")
    @Mapping(target = "comptabilise", constant = "false")
    @Mapping(target = "rapproche", constant = "false")
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
    PaiementModel toEntity(CreatePaiementRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    PaiementResponse toResponse(PaiementModel entity);

    List<PaiementResponse> toResponseList(List<PaiementModel> entities);

    default AuditDTO mapAudit(PaiementModel entity) {
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

    default TraceDTO mapTrace(PaiementModel entity) {
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
