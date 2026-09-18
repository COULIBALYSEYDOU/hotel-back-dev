package projet_hotelier.hotel.module.clientele.service.compliance.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreateConsentementClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdateConsentementClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.ConsentementClientResponse;
import projet_hotelier.hotel.module.clientele.model.compliance.ConsentementClient;
import projet_hotelier.hotel.module.clientele.repository.compliance.ConsentementClientRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsentementClientServiceImpl implements projet_hotelier.hotel.module.clientele.service.compliance.ConsentementClientService {

    private final ConsentementClientRepository repository;

    @Override
    public ConsentementClientResponse create(String tenantId, CreateConsentementClientRequest request) {
        ConsentementClient entity = ConsentementClient.builder()
                .tenantId(tenantId)
                .organisationId(request.getOrganisationId())
                .hotelId(request.getHotelId())
                .clientId(request.getClientId())
                .typeConsentement(request.getTypeConsentement())
                .consentementDonne(request.getConsentementDonne())
                .dateConsentement(request.getDateConsentement() != null ? request.getDateConsentement() : LocalDateTime.now())
                .dateExpiration(request.getDateExpiration())
                .methodeConsentement(request.getMethodeConsentement())
                .ipAddress(request.getIpAddress())
                .userAgent(request.getUserAgent())
                .versionPolitique(request.getVersionPolitique())
                .politiqueId(request.getPolitiqueId())
                .notes(request.getNotes())
                .build();

        ConsentementClient saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public ConsentementClientResponse update(String tenantId, Long id, UpdateConsentementClientRequest request) {
        ConsentementClient entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consentement non trouvé"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        if (request.getConsentementDonne() != null) entity.setConsentementDonne(request.getConsentementDonne());
        if (request.getDateConsentement() != null) entity.setDateConsentement(request.getDateConsentement());
        if (request.getDateExpiration() != null) entity.setDateExpiration(request.getDateExpiration());
        if (request.getMethodeConsentement() != null) entity.setMethodeConsentement(request.getMethodeConsentement());
        if (request.getVersionPolitique() != null) entity.setVersionPolitique(request.getVersionPolitique());
        if (request.getPolitiqueId() != null) entity.setPolitiqueId(request.getPolitiqueId());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());

        ConsentementClient updated = repository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ConsentementClientResponse findById(String tenantId, Long id) {
        ConsentementClient entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consentement non trouvé"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsentementClientResponse> findByClientId(String tenantId, Long clientId) {
        return repository.findByClientIdAndDeletedFalse(clientId)
                .stream()
                .filter(c -> c.getTenantId().equals(tenantId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsentementClientResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsentementClientResponse> findConsentementsValides(String tenantId) {
        return repository.findConsentementsValides(tenantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsentementClientResponse> findConsentementsValidesParClient(String tenantId, Long clientId) {
        return repository.findConsentementsValidesParClient(clientId)
                .stream()
                .filter(c -> c.getTenantId().equals(tenantId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsentementClientResponse> findConsentementsExpirantAvant(String tenantId, LocalDateTime dateLimite) {
        return repository.findConsentementsExpirantAvant(tenantId, dateLimite)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        ConsentementClient entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consentement non trouvé"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, Long clientId, String typeConsentement) {
        return repository.existsByClientIdAndTypeConsentementAndConsentementDonneTrue(clientId, typeConsentement);
    }

    private ConsentementClientResponse mapToResponse(ConsentementClient entity) {
        return ConsentementClientResponse.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId())
                .clientId(entity.getClientId())
                .typeConsentement(entity.getTypeConsentement())
                .consentementDonne(entity.getConsentementDonne())
                .dateConsentement(entity.getDateConsentement())
                .dateExpiration(entity.getDateExpiration())
                .methodeConsentement(entity.getMethodeConsentement())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .versionPolitique(entity.getVersionPolitique())
                .politiqueId(entity.getPolitiqueId())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion())
                .build();
    }
}
