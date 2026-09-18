package projet_hotelier.hotel.module.clientele.service.compliance.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreatePolitiqueConfidentialiteRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdatePolitiqueConfidentialiteRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.PolitiqueConfidentialiteResponse;
import projet_hotelier.hotel.module.clientele.model.compliance.PolitiqueConfidentialite;
import projet_hotelier.hotel.module.clientele.repository.compliance.PolitiqueConfidentialiteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PolitiqueConfidentialiteServiceImpl implements projet_hotelier.hotel.module.clientele.service.compliance.PolitiqueConfidentialiteService {

    private final PolitiqueConfidentialiteRepository repository;

    @Override
    public PolitiqueConfidentialiteResponse create(String tenantId, CreatePolitiqueConfidentialiteRequest request) {
        if (repository.existsByTenantIdAndVersionPolitiqueAndLangue(tenantId, request.getVersionPolitique(), request.getLangue())) {
            throw new IllegalArgumentException("Une politique avec cette version et langue existe déjà");
        }

        PolitiqueConfidentialite entity = PolitiqueConfidentialite.builder()
                .tenantId(tenantId)
                .organisationId(request.getOrganisationId())
                .hotelId(request.getHotelId())
                .versionPolitique(request.getVersionPolitique())
                .typePolitique(request.getTypePolitique())
                .langue(request.getLangue())
                .titre(request.getTitre())
                .contenu(request.getContenu())
                .dateEntreeVigueur(request.getDateEntreeVigueur())
                .dateFinVigueur(request.getDateFinVigueur())
                .active(request.getActive())
                .obligatoireAcceptation(request.getObligatoireAcceptation())
                .build();

        PolitiqueConfidentialite saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public PolitiqueConfidentialiteResponse update(String tenantId, Long id, UpdatePolitiqueConfidentialiteRequest request) {
        PolitiqueConfidentialite entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Politique non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        if (request.getVersionPolitique() != null) entity.setVersionPolitique(request.getVersionPolitique());
        if (request.getTypePolitique() != null) entity.setTypePolitique(request.getTypePolitique());
        if (request.getLangue() != null) entity.setLangue(request.getLangue());
        if (request.getTitre() != null) entity.setTitre(request.getTitre());
        if (request.getContenu() != null) entity.setContenu(request.getContenu());
        if (request.getDateEntreeVigueur() != null) entity.setDateEntreeVigueur(request.getDateEntreeVigueur());
        if (request.getDateFinVigueur() != null) entity.setDateFinVigueur(request.getDateFinVigueur());
        if (request.getActive() != null) entity.setActive(request.getActive());
        if (request.getObligatoireAcceptation() != null) entity.setObligatoireAcceptation(request.getObligatoireAcceptation());

        PolitiqueConfidentialite updated = repository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PolitiqueConfidentialiteResponse findById(String tenantId, Long id) {
        PolitiqueConfidentialite entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Politique non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PolitiqueConfidentialiteResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolitiqueConfidentialiteResponse> findByType(String tenantId, String typePolitique) {
        return repository.findByTenantIdAndTypePolitiqueAndActiveTrue(tenantId, typePolitique)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolitiqueConfidentialiteResponse> findByLangue(String tenantId, String langue) {
        return repository.findByTenantIdAndLangueAndActiveTrue(tenantId, langue)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolitiqueConfidentialiteResponse> findPolitiquesActives(String tenantId) {
        return repository.findPolitiquesActives(tenantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        PolitiqueConfidentialite entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Politique non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String versionPolitique, String langue) {
        return repository.existsByTenantIdAndVersionPolitiqueAndLangue(tenantId, versionPolitique, langue);
    }

    private PolitiqueConfidentialiteResponse mapToResponse(PolitiqueConfidentialite entity) {
        return PolitiqueConfidentialiteResponse.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId())
                .versionPolitique(entity.getVersionPolitique())
                .typePolitique(entity.getTypePolitique())
                .langue(entity.getLangue())
                .titre(entity.getTitre())
                .contenu(entity.getContenu())
                .dateEntreeVigueur(entity.getDateEntreeVigueur())
                .dateFinVigueur(entity.getDateFinVigueur())
                .active(entity.getActive())
                .obligatoireAcceptation(entity.getObligatoireAcceptation())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion())
                .build();
    }
}
