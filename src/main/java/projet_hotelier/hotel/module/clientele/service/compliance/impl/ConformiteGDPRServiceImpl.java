package projet_hotelier.hotel.module.clientele.service.compliance.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreateConformiteGDPRRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdateConformiteGDPRRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.ConformiteGDPRResponse;
import projet_hotelier.hotel.module.clientele.model.compliance.ConformiteGDPR;
import projet_hotelier.hotel.module.clientele.repository.compliance.ConformiteGDPRRepository;
import projet_hotelier.hotel.module.clientele.service.compliance.ConformiteGDPRService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConformiteGDPRServiceImpl implements ConformiteGDPRService {

    private final ConformiteGDPRRepository repository;

    @Override
    public ConformiteGDPRResponse create(String tenantId, CreateConformiteGDPRRequest request) {
        if (repository.existsByTenantIdAndHotelId(tenantId, request.getHotelId())) {
            throw new IllegalArgumentException("Une conformité GDPR existe déjà pour cet hôtel");
        }

        ConformiteGDPR entity = ConformiteGDPR.builder()
                .tenantId(tenantId)
                .organisationId(request.getOrganisationId())
                .hotelId(request.getHotelId())
                .conformeGdpr(request.getConformeGdpr())
                .dateConformite(request.getDateConformite())
                .dateProchaineAudit(request.getDateProchaineAudit())
                .dpoNom(request.getDpoNom())
                .dpoEmail(request.getDpoEmail())
                .dpoTelephone(request.getDpoTelephone())
                .registreTraitements(request.getRegistreTraitements())
                .analyseImpact(request.getAnalyseImpact())
                .mesuresSecurite(request.getMesuresSecurite())
                .notes(request.getNotes())
                .build();

        ConformiteGDPR saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public ConformiteGDPRResponse update(String tenantId, Long id, UpdateConformiteGDPRRequest request) {
        ConformiteGDPR entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conformité GDPR non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        if (request.getConformeGdpr() != null) entity.setConformeGdpr(request.getConformeGdpr());
        if (request.getDateConformite() != null) entity.setDateConformite(request.getDateConformite());
        if (request.getDateProchaineAudit() != null) entity.setDateProchaineAudit(request.getDateProchaineAudit());
        if (request.getDpoNom() != null) entity.setDpoNom(request.getDpoNom());
        if (request.getDpoEmail() != null) entity.setDpoEmail(request.getDpoEmail());
        if (request.getDpoTelephone() != null) entity.setDpoTelephone(request.getDpoTelephone());
        if (request.getRegistreTraitements() != null) entity.setRegistreTraitements(request.getRegistreTraitements());
        if (request.getAnalyseImpact() != null) entity.setAnalyseImpact(request.getAnalyseImpact());
        if (request.getMesuresSecurite() != null) entity.setMesuresSecurite(request.getMesuresSecurite());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());

        ConformiteGDPR updated = repository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ConformiteGDPRResponse findById(String tenantId, Long id) {
        ConformiteGDPR entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conformité GDPR non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ConformiteGDPRResponse findByTenantIdAndHotelId(String tenantId, String hotelId) {
        ConformiteGDPR entity = repository.findByTenantIdAndHotelId(tenantId, hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Conformité GDPR non trouvée"));
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConformiteGDPRResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConformiteGDPRResponse> findConformes(String tenantId) {
        return repository.findByTenantIdAndConformeGdprTrue(tenantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConformiteGDPRResponse> findAuditsProchains(String tenantId) {
        return repository.findAuditsProchains(tenantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        ConformiteGDPR entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conformité GDPR non trouvée"));

        if (!entity.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Accès non autorisé");
        }

        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String hotelId) {
        return repository.existsByTenantIdAndHotelId(tenantId, hotelId);
    }

    private ConformiteGDPRResponse mapToResponse(ConformiteGDPR entity) {
        return ConformiteGDPRResponse.builder()
                .id(entity.getId())
                .tenantId(entity.getTenantId())
                .organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId())
                .conformeGdpr(entity.getConformeGdpr())
                .dateConformite(entity.getDateConformite())
                .dateProchaineAudit(entity.getDateProchaineAudit())
                .dpoNom(entity.getDpoNom())
                .dpoEmail(entity.getDpoEmail())
                .dpoTelephone(entity.getDpoTelephone())
                .registreTraitements(entity.getRegistreTraitements())
                .analyseImpact(entity.getAnalyseImpact())
                .mesuresSecurite(entity.getMesuresSecurite())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion())
                .build();
    }
}
