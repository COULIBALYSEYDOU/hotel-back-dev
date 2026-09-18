package projet_hotelier.hotel.module.clientele.service.integration.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.integration.CreateIntegrationTierceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.integration.UpdateIntegrationTierceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.integration.IntegrationTierceResponse;
import projet_hotelier.hotel.module.clientele.model.integration.IntegrationTierce;
import projet_hotelier.hotel.module.clientele.repository.integration.IntegrationTierceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IntegrationTierceServiceImpl implements projet_hotelier.hotel.module.clientele.service.integration.IntegrationTierceService {

    private final IntegrationTierceRepository repository;

    @Override
    public IntegrationTierceResponse create(String tenantId, CreateIntegrationTierceRequest request) {
        if (repository.existsByTenantIdAndNom(tenantId, request.getNom())) {
            throw new IllegalArgumentException("Une intégration avec ce nom existe déjà");
        }

        IntegrationTierce entity = IntegrationTierce.builder()
                .tenantId(tenantId)
                .organisationId(request.getOrganisationId())
                .hotelId(request.getHotelId())
                .nom(request.getNom())
                .typeIntegration(request.getTypeIntegration())
                .fournisseur(request.getFournisseur())
                .urlApi(request.getUrlApi())
                .apiKey(request.getApiKey())
                .apiSecret(request.getApiSecret())
                .active(request.getActive())
                .synchronisationAuto(request.getSynchronisationAuto())
                .frequenceSync(request.getFrequenceSync())
                .configJson(request.getConfigJson())
                .notes(request.getNotes())
                .build();

        IntegrationTierce saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public IntegrationTierceResponse update(String tenantId, Long id, UpdateIntegrationTierceRequest request) {
        IntegrationTierce entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Intégration non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        if (request.getNom() != null) entity.setNom(request.getNom());
        if (request.getTypeIntegration() != null) entity.setTypeIntegration(request.getTypeIntegration());
        if (request.getFournisseur() != null) entity.setFournisseur(request.getFournisseur());
        if (request.getUrlApi() != null) entity.setUrlApi(request.getUrlApi());
        if (request.getApiKey() != null) entity.setApiKey(request.getApiKey());
        if (request.getApiSecret() != null) entity.setApiSecret(request.getApiSecret());
        if (request.getActive() != null) entity.setActive(request.getActive());
        if (request.getSynchronisationAuto() != null) entity.setSynchronisationAuto(request.getSynchronisationAuto());
        if (request.getFrequenceSync() != null) entity.setFrequenceSync(request.getFrequenceSync());
        if (request.getProchaineSync() != null) entity.setProchaineSync(request.getProchaineSync());
        if (request.getConfigJson() != null) entity.setConfigJson(request.getConfigJson());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());

        IntegrationTierce updated = repository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public IntegrationTierceResponse findById(String tenantId, Long id) {
        IntegrationTierce entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Intégration non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntegrationTierceResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationTierceResponse> findByType(String tenantId, String typeIntegration) {
        return repository.findByTenantIdAndTypeIntegration(tenantId, typeIntegration)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationTierceResponse> findIntegrationsAutoActives(String tenantId) {
        return repository.findIntegrationsAutoActives(tenantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationTierceResponse> findIntegrationsASynchroniser(String tenantId, LocalDateTime dateLimite) {
        return repository.findIntegrationsASynchroniser(tenantId, dateLimite)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        IntegrationTierce entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Intégration non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String nom) {
        return repository.existsByTenantIdAndNom(tenantId, nom);
    }

    private IntegrationTierceResponse mapToResponse(IntegrationTierce entity) {
        return IntegrationTierceResponse.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId())
                .nom(entity.getNom())
                .typeIntegration(entity.getTypeIntegration())
                .fournisseur(entity.getFournisseur())
                .urlApi(entity.getUrlApi())
                .active(entity.getActive())
                .synchronisationAuto(entity.getSynchronisationAuto())
                .frequenceSync(entity.getFrequenceSync())
                .derniereSync(entity.getDerniereSync())
                .prochaineSync(entity.getProchaineSync())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion())
                .build();
    }
}
