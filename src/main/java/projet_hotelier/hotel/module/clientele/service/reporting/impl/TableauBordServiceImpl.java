package projet_hotelier.hotel.module.clientele.service.reporting.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateTableauBordRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateTableauBordRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.TableauBordResponse;
import projet_hotelier.hotel.module.clientele.model.reporting.TableauBord;
import projet_hotelier.hotel.module.clientele.repository.reporting.TableauBordRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TableauBordServiceImpl implements projet_hotelier.hotel.module.clientele.service.reporting.TableauBordService {

    private final TableauBordRepository repository;

    @Override
    public TableauBordResponse create(String tenantId, CreateTableauBordRequest request) {
        if (repository.existsByTenantIdAndNom(tenantId, request.getNom())) {
            throw new IllegalArgumentException("Un tableau de bord avec ce nom existe déjà");
        }
        TableauBord entity = TableauBord.builder()
                .tenantId(tenantId).organisationId(request.getOrganisationId()).hotelId(request.getHotelId())
                .nom(request.getNom()).description(request.getDescription()).roleCible(request.getRoleCible())
                .utilisateurId(request.getUtilisateurId()).widgetsJson(request.getWidgetsJson())
                .layoutJson(request.getLayoutJson()).filtresParDefautJson(request.getFiltresParDefautJson())
                .actif(request.getActif()).parDefaut(request.getParDefaut())
                .partageAutorise(request.getPartageAutorise()).ordreAffichage(request.getOrdreAffichage())
                .notes(request.getNotes()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public TableauBordResponse update(String tenantId, Long id, UpdateTableauBordRequest request) {
        TableauBord entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tableau de bord non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getNom() != null) entity.setNom(request.getNom());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getRoleCible() != null) entity.setRoleCible(request.getRoleCible());
        if (request.getUtilisateurId() != null) entity.setUtilisateurId(request.getUtilisateurId());
        if (request.getWidgetsJson() != null) entity.setWidgetsJson(request.getWidgetsJson());
        if (request.getLayoutJson() != null) entity.setLayoutJson(request.getLayoutJson());
        if (request.getFiltresParDefautJson() != null) entity.setFiltresParDefautJson(request.getFiltresParDefautJson());
        if (request.getActif() != null) entity.setActif(request.getActif());
        if (request.getParDefaut() != null) entity.setParDefaut(request.getParDefaut());
        if (request.getPartageAutorise() != null) entity.setPartageAutorise(request.getPartageAutorise());
        if (request.getOrdreAffichage() != null) entity.setOrdreAffichage(request.getOrdreAffichage());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public TableauBordResponse findById(String tenantId, Long id) {
        TableauBord entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tableau de bord non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TableauBordResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableauBordResponse> findTableauxParDefaut(String tenantId) {
        return repository.findTableauxParDefaut(tenantId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableauBordResponse> findTableauxParRole(String tenantId, String roleCible) {
        return repository.findTableauxParRole(tenantId, roleCible).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableauBordResponse> findTableauxParUtilisateur(String tenantId, Long utilisateurId) {
        return repository.findTableauxParUtilisateur(tenantId, utilisateurId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        TableauBord entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tableau de bord non trouvé"));
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

    private TableauBordResponse mapToResponse(TableauBord entity) {
        return TableauBordResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId()).organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId()).nom(entity.getNom()).description(entity.getDescription())
                .roleCible(entity.getRoleCible()).utilisateurId(entity.getUtilisateurId())
                .widgetsJson(entity.getWidgetsJson()).layoutJson(entity.getLayoutJson())
                .filtresParDefautJson(entity.getFiltresParDefautJson()).actif(entity.getActif())
                .parDefaut(entity.getParDefaut()).partageAutorise(entity.getPartageAutorise())
                .ordreAffichage(entity.getOrdreAffichage()).nombreVues(entity.getNombreVues())
                .derniereVue(entity.getDerniereVue()).createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy()).modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy()).version(entity.getVersion()).build();
    }
}
