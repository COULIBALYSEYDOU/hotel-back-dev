package projet_hotelier.hotel.module.clientele.service.integration.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.integration.CreateLogIntegrationRequest;
import projet_hotelier.hotel.module.clientele.dto.request.integration.UpdateLogIntegrationRequest;
import projet_hotelier.hotel.module.clientele.dto.response.integration.LogIntegrationResponse;
import projet_hotelier.hotel.module.clientele.model.integration.LogIntegration;
import projet_hotelier.hotel.module.clientele.repository.integration.LogIntegrationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LogIntegrationServiceImpl implements projet_hotelier.hotel.module.clientele.service.integration.LogIntegrationService {

    private final LogIntegrationRepository repository;

    @Override
    public LogIntegrationResponse create(String tenantId, CreateLogIntegrationRequest request) {
        LogIntegration entity = LogIntegration.builder()
                .tenantId(tenantId)
                .organisationId(request.getOrganisationId())
                .hotelId(request.getHotelId())
                .integrationId(request.getIntegrationId())
                .typeIntegration(request.getTypeIntegration())
                .operation(request.getOperation())
                .dateExecution(request.getDateExecution())
                .statut(request.getStatut())
                .dureeMs(request.getDureeMs())
                .requete(request.getRequete())
                .reponse(request.getReponse())
                .codeHttp(request.getCodeHttp())
                .messageErreur(request.getMessageErreur())
                .nombreTentatives(request.getNombreTentatives())
                .ipSource(request.getIpSource())
                .userAgent(request.getUserAgent())
                .metadataJson(request.getMetadataJson())
                .build();

        LogIntegration saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public LogIntegrationResponse update(String tenantId, Long id, UpdateLogIntegrationRequest request) {
        LogIntegration entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log non trouvé"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        if (request.getStatut() != null) entity.setStatut(request.getStatut());
        if (request.getDureeMs() != null) entity.setDureeMs(request.getDureeMs());
        if (request.getReponse() != null) entity.setReponse(request.getReponse());
        if (request.getCodeHttp() != null) entity.setCodeHttp(request.getCodeHttp());
        if (request.getMessageErreur() != null) entity.setMessageErreur(request.getMessageErreur());
        if (request.getNombreTentatives() != null) entity.setNombreTentatives(request.getNombreTentatives());

        LogIntegration updated = repository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public LogIntegrationResponse findById(String tenantId, Long id) {
        LogIntegration entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log non trouvé"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogIntegrationResponse> findByIntegrationId(String tenantId, Long integrationId) {
        return repository.findByIntegrationIdOrderByDateExecutionDesc(integrationId)
                .stream()
                .filter(l -> l.getTenantId().equals(tenantId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogIntegrationResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogIntegrationResponse> findLogsParPeriode(String tenantId, LocalDateTime dateDebut, LocalDateTime dateFin) {
        return repository.findLogsParPeriode(tenantId, dateDebut, dateFin)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogIntegrationResponse> findLogsEchecs(String tenantId, Long integrationId) {
        return repository.findLogsEchecs(integrationId)
                .stream()
                .filter(l -> l.getTenantId().equals(tenantId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countSucces(String tenantId, Long integrationId) {
        return repository.countSuccesByIntegrationId(integrationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countEchecs(String tenantId, Long integrationId) {
        return repository.countEchecsByIntegrationId(integrationId);
    }

    private LogIntegrationResponse mapToResponse(LogIntegration entity) {
        return LogIntegrationResponse.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId())
                .integrationId(entity.getIntegrationId())
                .typeIntegration(entity.getTypeIntegration())
                .operation(entity.getOperation())
                .dateExecution(entity.getDateExecution())
                .statut(entity.getStatut())
                .dureeMs(entity.getDureeMs())
                .codeHttp(entity.getCodeHttp())
                .messageErreur(entity.getMessageErreur())
                .nombreTentatives(entity.getNombreTentatives())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }
}
