package projet_hotelier.hotel.module.finances.mapper;

import org.mapstruct.*;
import projet_hotelier.hotel.module.finances.dto.request.depense.CreateDepenseRequest;
import projet_hotelier.hotel.module.finances.dto.response.depense.DepenseResponse;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.DepenseModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface DepenseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "codeDepense", ignore = true)
    @Mapping(target = "montantTVA", ignore = true)
    @Mapping(target = "montantTTC", ignore = true)
    @Mapping(target = "montantPaye", ignore = true)
    @Mapping(target = "montantRestant", ignore = true)
    @Mapping(target = "statutDepense", constant = "BROUILLON")
    @Mapping(target = "validee", constant = "false")
    @Mapping(target = "comptabilisee", constant = "false")
    @Mapping(target = "payee", constant = "false")
    @Mapping(target = "annulee", constant = "false")
    @Mapping(target = "dateComptabilisation", ignore = true)
    @Mapping(target = "compteContrepartie", ignore = true)
    @Mapping(target = "codeJournal", ignore = true)
    @Mapping(target = "ecritureComptableId", ignore = true)
    @Mapping(target = "fournisseurNom", ignore = true)
    @Mapping(target = "fournisseurCode", ignore = true)
    @Mapping(target = "codeBudget", ignore = true)
    @Mapping(target = "imputeeSurBudget", ignore = true)
    @Mapping(target = "numeroCommande", ignore = true)
    @Mapping(target = "dateBonReception", ignore = true)
    @Mapping(target = "valideurId", ignore = true)
    @Mapping(target = "valideurNom", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    @Mapping(target = "commentaireValidation", ignore = true)
    @Mapping(target = "approbationRequise", ignore = true)
    @Mapping(target = "approbateurId", ignore = true)
    @Mapping(target = "approbateurNom", ignore = true)
    @Mapping(target = "dateApprobation", ignore = true)
    @Mapping(target = "commentaireApprobation", ignore = true)
    @Mapping(target = "compteBancaireNom", ignore = true)
    @Mapping(target = "referencePaiement", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    @Mapping(target = "tvaRecuperable", ignore = true)
    @Mapping(target = "montantTVARecuperable", ignore = true)
    @Mapping(target = "justificatifNom", ignore = true)
    @Mapping(target = "justificatifPresent", ignore = true)
    @Mapping(target = "depenseRecurrente", ignore = true)
    @Mapping(target = "frequenceRecurrence", ignore = true)
    @Mapping(target = "prochaineOccurrence", ignore = true)
    @Mapping(target = "projetCode", ignore = true)
    @Mapping(target = "siteId", ignore = true)
    @Mapping(target = "siteNom", ignore = true)
    @Mapping(target = "lieeImmobilisation", ignore = true)
    @Mapping(target = "immobilisationId", ignore = true)
    @Mapping(target = "referenceExterne", ignore = true)
    @Mapping(target = "signaleeAnomalie", ignore = true)
    @Mapping(target = "descriptionAnomalie", ignore = true)
    @Mapping(target = "donneesSensibles", ignore = true)
    @Mapping(target = "baseLegale", ignore = true)
    @Mapping(target = "dureeRetention", ignore = true)
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
    DepenseModel toEntity(CreateDepenseRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    DepenseResponse toResponse(DepenseModel entity);

    List<DepenseResponse> toResponseList(List<DepenseModel> entities);

    default AuditDTO mapAudit(DepenseModel entity) {
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

    default TraceDTO mapTrace(DepenseModel entity) {
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
