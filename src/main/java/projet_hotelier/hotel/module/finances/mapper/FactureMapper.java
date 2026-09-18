package projet_hotelier.hotel.module.finances.mapper;

import org.mapstruct.*;
import projet_hotelier.hotel.module.finances.dto.request.facture.CreateFactureRequest;
import projet_hotelier.hotel.module.finances.dto.request.facture.LigneFactureRequest;
import projet_hotelier.hotel.module.finances.dto.response.facture.FactureResponse;
import projet_hotelier.hotel.module.finances.dto.response.facture.LigneFactureResponse;
import projet_hotelier.hotel.module.finances.model.facturationDocument.FactureModel;
import projet_hotelier.hotel.module.finances.model.facturationDocument.LigneFactureModel;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface FactureMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "numeroFacture", ignore = true)
    @Mapping(target = "numeroSequence", ignore = true)
    @Mapping(target = "prefixe", ignore = true)
    @Mapping(target = "suffixe", ignore = true)
    @Mapping(target = "annee", ignore = true)
    @Mapping(target = "mois", ignore = true)
    @Mapping(target = "montantHT", ignore = true)
    @Mapping(target = "montantTVA", ignore = true)
    @Mapping(target = "montantTTC", ignore = true)
    @Mapping(target = "montantPaye", ignore = true)
    @Mapping(target = "montantRestant", ignore = true)
    @Mapping(target = "dateEmission", ignore = true)
    @Mapping(target = "emetteurNom", ignore = true)
    @Mapping(target = "emetteurAdresse", ignore = true)
    @Mapping(target = "emetteurVille", ignore = true)
    @Mapping(target = "emetteurCodePostal", ignore = true)
    @Mapping(target = "emetteurPays", ignore = true)
    @Mapping(target = "emetteurNIF", ignore = true)
    @Mapping(target = "emetteurRCCM", ignore = true)
    @Mapping(target = "emetteurCapital", ignore = true)
    @Mapping(target = "montantRemise", ignore = true)
    @Mapping(target = "tauxRemise", ignore = true)
    @Mapping(target = "montantHTApresRemise", ignore = true)
    @Mapping(target = "tauxTVA", ignore = true)
    @Mapping(target = "montantAutresTaxes", ignore = true)
    @Mapping(target = "detailTVAJson", ignore = true)
    @Mapping(target = "tauxPenaliteRetard", ignore = true)
    @Mapping(target = "montantEscompte", ignore = true)
    @Mapping(target = "conditionsEscompte", ignore = true)
    @Mapping(target = "modesPaiementAcceptes", ignore = true)
    @Mapping(target = "contentieux", ignore = true)
    @Mapping(target = "avoirLieId", ignore = true)
    @Mapping(target = "numeroAvoirLie", ignore = true)
    @Mapping(target = "factureOrigineId", ignore = true)
    @Mapping(target = "numeroFactureOrigine", ignore = true)
    @Mapping(target = "numeroReservation", ignore = true)
    @Mapping(target = "compteComptable", ignore = true)
    @Mapping(target = "codeJournal", ignore = true)
    @Mapping(target = "ecritureComptableId", ignore = true)
    @Mapping(target = "mentionsLegales", ignore = true)
    @Mapping(target = "conditionsGeneralesVente", ignore = true)
    @Mapping(target = "documentUrl", ignore = true)
    @Mapping(target = "documentNom", ignore = true)
    @Mapping(target = "documentGenere", ignore = true)
    @Mapping(target = "siteId", ignore = true)
    @Mapping(target = "siteNom", ignore = true)
    @Mapping(target = "nombreRelances", ignore = true)
    @Mapping(target = "derniereRelance", ignore = true)
    @Mapping(target = "prochaineRelance", ignore = true)
    @Mapping(target = "referenceExterne", ignore = true)
    @Mapping(target = "signaleeAnomalie", ignore = true)
    @Mapping(target = "valideurId", ignore = true)
    @Mapping(target = "valideurNom", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    @Mapping(target = "statutFacture", constant = "BROUILLON")
    @Mapping(target = "emise", constant = "false")
    @Mapping(target = "envoyee", constant = "false")
    @Mapping(target = "payee", constant = "false")
    @Mapping(target = "partiellementPayee", constant = "false")
    @Mapping(target = "annulee", constant = "false")
    @Mapping(target = "comptabilisee", constant = "false")
    @Mapping(target = "lignes", ignore = true)
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
    FactureModel toEntity(CreateFactureRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "facture", ignore = true)
    @Mapping(target = "numeroLigne", ignore = true)
    @Mapping(target = "montantHT", ignore = true)
    @Mapping(target = "montantTVA", ignore = true)
    @Mapping(target = "montantTTC", ignore = true)
    @Mapping(target = "montantRemise", ignore = true)
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
    @Mapping(target = "axeAnalytique", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "metadataJson", ignore = true)
    LigneFactureModel toEntity(LigneFactureRequest request);

    @Mapping(target = "audit", expression = "java(mapAudit(entity))")
    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    FactureResponse toResponse(FactureModel entity);

    @Mapping(target = "trace", expression = "java(mapTrace(entity))")
    LigneFactureResponse toResponse(LigneFactureModel entity);

    List<FactureResponse> toResponseList(List<FactureModel> entities);

    List<LigneFactureResponse> toLineResponseList(List<LigneFactureModel> entities);

    default AuditDTO mapAudit(FactureModel entity) {
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

    default TraceDTO mapTrace(FactureModel entity) {
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

    default TraceDTO mapTrace(LigneFactureModel entity) {
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
