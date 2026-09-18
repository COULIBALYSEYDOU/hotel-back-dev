package projet_hotelier.hotel.module.clientele.service.reporting.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateRapportPersonnaliseRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateRapportPersonnaliseRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.RapportPersonnaliseResponse;
import projet_hotelier.hotel.module.clientele.model.reporting.RapportPersonnalise;
import projet_hotelier.hotel.module.clientele.repository.reporting.RapportPersonnaliseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RapportPersonnaliseServiceImpl implements projet_hotelier.hotel.module.clientele.service.reporting.RapportPersonnaliseService {

    private final RapportPersonnaliseRepository repository;

    @Override
    public RapportPersonnaliseResponse create(String tenantId, CreateRapportPersonnaliseRequest request) {
        if (repository.existsByTenantIdAndNom(tenantId, request.getNom())) {
            throw new IllegalArgumentException("Un rapport avec ce nom existe déjà");
        }
        RapportPersonnalise entity = RapportPersonnalise.builder()
                .tenantId(tenantId).organisationId(request.getOrganisationId()).hotelId(request.getHotelId())
                .nom(request.getNom()).description(request.getDescription()).categorie(request.getCategorie())
                .requeteSql(request.getRequeteSql()).parametresJson(request.getParametresJson())
                .formatSortie(request.getFormatSortie()).frequenceExecution(request.getFrequenceExecution())
                .prochaineExecution(request.getProchaineExecution()).active(request.getActive())
                .partageAutorise(request.getPartageAutorise()).rolesAutorises(request.getRolesAutorises())
                .notes(request.getNotes()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public RapportPersonnaliseResponse update(String tenantId, Long id, UpdateRapportPersonnaliseRequest request) {
        RapportPersonnalise entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rapport non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getNom() != null) entity.setNom(request.getNom());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getCategorie() != null) entity.setCategorie(request.getCategorie());
        if (request.getRequeteSql() != null) entity.setRequeteSql(request.getRequeteSql());
        if (request.getParametresJson() != null) entity.setParametresJson(request.getParametresJson());
        if (request.getFormatSortie() != null) entity.setFormatSortie(request.getFormatSortie());
        if (request.getFrequenceExecution() != null) entity.setFrequenceExecution(request.getFrequenceExecution());
        if (request.getProchaineExecution() != null) entity.setProchaineExecution(request.getProchaineExecution());
        if (request.getActive() != null) entity.setActive(request.getActive());
        if (request.getPartageAutorise() != null) entity.setPartageAutorise(request.getPartageAutorise());
        if (request.getRolesAutorises() != null) entity.setRolesAutorises(request.getRolesAutorises());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public RapportPersonnaliseResponse findById(String tenantId, Long id) {
        RapportPersonnalise entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rapport non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RapportPersonnaliseResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RapportPersonnaliseResponse> findRapportsAScheduler(String tenantId, LocalDateTime dateLimite) {
        return repository.findRapportsAScheduler(tenantId, dateLimite).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RapportPersonnaliseResponse> findRapportsPartages(String tenantId) {
        return repository.findRapportsPartages(tenantId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        RapportPersonnalise entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rapport non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String nom) {
        return repository.existsByTenantIdAndNom(tenantId, nom);
    }

    private RapportPersonnaliseResponse mapToResponse(RapportPersonnalise entity) {
        return RapportPersonnaliseResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId()).organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId()).nom(entity.getNom()).description(entity.getDescription())
                .categorie(entity.getCategorie()).formatSortie(entity.getFormatSortie())
                .frequenceExecution(entity.getFrequenceExecution()).prochaineExecution(entity.getProchaineExecution())
                .derniereExecution(entity.getDerniereExecution()).active(entity.getActive())
                .partageAutorise(entity.getPartageAutorise()).rolesAutorises(entity.getRolesAutorises())
                .nombreExecutions(entity.getNombreExecutions()).createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy()).modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy()).version(entity.getVersion()).build();
    }
}
