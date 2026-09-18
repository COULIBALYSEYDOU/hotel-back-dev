package projet_hotelier.hotel.module.clientele.service.i18n.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.i18n.CreateTraductionRequest;
import projet_hotelier.hotel.module.clientele.dto.request.i18n.UpdateTraductionRequest;
import projet_hotelier.hotel.module.clientele.dto.response.i18n.TraductionResponse;
import projet_hotelier.hotel.module.clientele.model.i18n.Traduction;
import projet_hotelier.hotel.module.clientele.repository.i18n.TraductionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TraductionServiceImpl implements projet_hotelier.hotel.module.clientele.service.i18n.TraductionService {

    private final TraductionRepository repository;

    @Override
    public TraductionResponse create(String tenantId, CreateTraductionRequest request) {
        if (repository.existsByTenantIdAndCleTraductionAndLangue(tenantId, request.getCleTraduction(), request.getLangue())) {
            throw new IllegalArgumentException("Une traduction avec cette clé et langue existe déjà");
        }
        Traduction entity = Traduction.builder()
                .tenantId(tenantId).organisationId(request.getOrganisationId()).hotelId(request.getHotelId())
                .cleTraduction(request.getCleTraduction()).langue(request.getLangue()).valeur(request.getValeur())
                .categorie(request.getCategorie()).contexte(request.getContexte()).notes(request.getNotes()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public TraductionResponse update(String tenantId, Long id, UpdateTraductionRequest request) {
        Traduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Traduction non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getCleTraduction() != null) entity.setCleTraduction(request.getCleTraduction());
        if (request.getLangue() != null) entity.setLangue(request.getLangue());
        if (request.getValeur() != null) entity.setValeur(request.getValeur());
        if (request.getCategorie() != null) entity.setCategorie(request.getCategorie());
        if (request.getContexte() != null) entity.setContexte(request.getContexte());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public TraductionResponse findById(String tenantId, Long id) {
        Traduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Traduction non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public TraductionResponse findByCleAndLangue(String tenantId, String cleTraduction, String langue) {
        return repository.findByTenantIdAndCleTraductionAndLangue(tenantId, cleTraduction, langue)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Traduction non trouvée"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TraductionResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraductionResponse> findByLangue(String tenantId, String langue) {
        return repository.findByTenantIdAndLangue(tenantId, langue).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraductionResponse> findTraductionsParPrefixe(String tenantId, String langue, String prefixe) {
        return repository.findTraductionsParPrefixe(tenantId, langue, prefixe).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        Traduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Traduction non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String cleTraduction, String langue) {
        return repository.existsByTenantIdAndCleTraductionAndLangue(tenantId, cleTraduction, langue);
    }

    private TraductionResponse mapToResponse(Traduction entity) {
        return TraductionResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId()).organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId()).cleTraduction(entity.getCleTraduction()).langue(entity.getLangue())
                .valeur(entity.getValeur()).categorie(entity.getCategorie()).contexte(entity.getContexte())
                .notes(entity.getNotes()).createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt()).modifiedBy(entity.getModifiedBy()).version(entity.getVersion()).build();
    }
}
