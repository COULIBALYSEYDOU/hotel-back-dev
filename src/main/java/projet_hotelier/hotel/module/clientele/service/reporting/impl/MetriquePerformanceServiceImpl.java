package projet_hotelier.hotel.module.clientele.service.reporting.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateMetriquePerformanceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateMetriquePerformanceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.MetriquePerformanceResponse;
import projet_hotelier.hotel.module.clientele.model.reporting.MetriquePerformance;
import projet_hotelier.hotel.module.clientele.repository.reporting.MetriquePerformanceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MetriquePerformanceServiceImpl implements projet_hotelier.hotel.module.clientele.service.reporting.MetriquePerformanceService {

    private final MetriquePerformanceRepository repository;

    @Override
    public MetriquePerformanceResponse create(String tenantId, CreateMetriquePerformanceRequest request) {
        MetriquePerformance entity = MetriquePerformance.builder()
                .tenantId(tenantId).organisationId(request.getOrganisationId()).hotelId(request.getHotelId())
                .dateMetrique(request.getDateMetrique()).typeMetrique(request.getTypeMetrique())
                .valeur(request.getValeur()).valeurNumerique(request.getValeurNumerique()).unite(request.getUnite())
                .periode(request.getPeriode()).categorieChambre(request.getCategorieChambre())
                .canalReservation(request.getCanalReservation())
                .comparaisonPeriodePrecedente(request.getComparaisonPeriodePrecedente())
                .evolutionPourcentage(request.getEvolutionPourcentage()).objectif(request.getObjectif())
                .ecartObjectif(request.getEcartObjectif()).metadataJson(request.getMetadataJson())
                .notes(request.getNotes()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public MetriquePerformanceResponse update(String tenantId, Long id, UpdateMetriquePerformanceRequest request) {
        MetriquePerformance entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Métrique non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getDateMetrique() != null) entity.setDateMetrique(request.getDateMetrique());
        if (request.getTypeMetrique() != null) entity.setTypeMetrique(request.getTypeMetrique());
        if (request.getValeur() != null) entity.setValeur(request.getValeur());
        if (request.getValeurNumerique() != null) entity.setValeurNumerique(request.getValeurNumerique());
        if (request.getUnite() != null) entity.setUnite(request.getUnite());
        if (request.getPeriode() != null) entity.setPeriode(request.getPeriode());
        if (request.getCategorieChambre() != null) entity.setCategorieChambre(request.getCategorieChambre());
        if (request.getCanalReservation() != null) entity.setCanalReservation(request.getCanalReservation());
        if (request.getComparaisonPeriodePrecedente() != null) entity.setComparaisonPeriodePrecedente(request.getComparaisonPeriodePrecedente());
        if (request.getEvolutionPourcentage() != null) entity.setEvolutionPourcentage(request.getEvolutionPourcentage());
        if (request.getObjectif() != null) entity.setObjectif(request.getObjectif());
        if (request.getEcartObjectif() != null) entity.setEcartObjectif(request.getEcartObjectif());
        if (request.getMetadataJson() != null) entity.setMetadataJson(request.getMetadataJson());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MetriquePerformanceResponse findById(String tenantId, Long id) {
        MetriquePerformance entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Métrique non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MetriquePerformanceResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetriquePerformanceResponse> findMetriquesParTypeEtPeriode(String tenantId, String typeMetrique, LocalDate dateDebut, LocalDate dateFin) {
        return repository.findMetriquesParTypeEtPeriode(tenantId, typeMetrique, dateDebut, dateFin).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetriquePerformanceResponse> findMetriquesParDate(String tenantId, LocalDate date) {
        return repository.findMetriquesParDate(tenantId, date).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculerMoyenneParType(String tenantId, String typeMetrique, LocalDate dateDebut, LocalDate dateFin) {
        return repository.calculerMoyenneParType(tenantId, typeMetrique, dateDebut, dateFin);
    }

    private MetriquePerformanceResponse mapToResponse(MetriquePerformance entity) {
        return MetriquePerformanceResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId()).organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId()).dateMetrique(entity.getDateMetrique()).typeMetrique(entity.getTypeMetrique())
                .valeur(entity.getValeur()).valeurNumerique(entity.getValeurNumerique()).unite(entity.getUnite())
                .periode(entity.getPeriode()).categorieChambre(entity.getCategorieChambre())
                .canalReservation(entity.getCanalReservation())
                .comparaisonPeriodePrecedente(entity.getComparaisonPeriodePrecedente())
                .evolutionPourcentage(entity.getEvolutionPourcentage()).objectif(entity.getObjectif())
                .ecartObjectif(entity.getEcartObjectif()).createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy()).modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy()).version(entity.getVersion()).build();
    }
}
