package projet_hotelier.hotel.module.clientele.service.organisation.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.organisation.CreateConfigurationHotelRequest;
import projet_hotelier.hotel.module.clientele.dto.request.organisation.UpdateConfigurationHotelRequest;
import projet_hotelier.hotel.module.clientele.dto.response.organisation.ConfigurationHotelResponse;
import projet_hotelier.hotel.module.clientele.model.organisation.ConfigurationHotel;
import projet_hotelier.hotel.module.clientele.repository.organisation.ConfigurationHotelRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfigurationHotelServiceImpl implements projet_hotelier.hotel.module.clientele.service.organisation.ConfigurationHotelService {

    private final ConfigurationHotelRepository repository;

    @Override
    public ConfigurationHotelResponse create(String tenantId, CreateConfigurationHotelRequest request) {
        if (repository.existsByTenantIdAndHotelId(tenantId, request.getHotelId())) {
            throw new IllegalArgumentException("Une configuration existe déjà pour cet hôtel");
        }
        ConfigurationHotel entity = ConfigurationHotel.builder()
                .tenantId(tenantId).organisationId(request.getOrganisationId()).hotelId(request.getHotelId())
                .heureCheckIn(request.getHeureCheckIn()).heureCheckOut(request.getHeureCheckOut())
                .delaiAnnulationHeures(request.getDelaiAnnulationHeures())
                .cautionObligatoire(request.getCautionObligatoire())
                .montantCautionDefaut(request.getMontantCautionDefaut())
                .paiementAvantArrivee(request.getPaiementAvantArrivee())
                .confirmationEmailAuto(request.getConfirmationEmailAuto())
                .confirmationSmsAuto(request.getConfirmationSmsAuto())
                .politiqueAnnulation(request.getPolitiqueAnnulation())
                .configJson(request.getConfigJson()).notes(request.getNotes()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public ConfigurationHotelResponse update(String tenantId, Long id, UpdateConfigurationHotelRequest request) {
        ConfigurationHotel entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Configuration non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getHeureCheckIn() != null) entity.setHeureCheckIn(request.getHeureCheckIn());
        if (request.getHeureCheckOut() != null) entity.setHeureCheckOut(request.getHeureCheckOut());
        if (request.getDelaiAnnulationHeures() != null) entity.setDelaiAnnulationHeures(request.getDelaiAnnulationHeures());
        if (request.getCautionObligatoire() != null) entity.setCautionObligatoire(request.getCautionObligatoire());
        if (request.getMontantCautionDefaut() != null) entity.setMontantCautionDefaut(request.getMontantCautionDefaut());
        if (request.getPaiementAvantArrivee() != null) entity.setPaiementAvantArrivee(request.getPaiementAvantArrivee());
        if (request.getConfirmationEmailAuto() != null) entity.setConfirmationEmailAuto(request.getConfirmationEmailAuto());
        if (request.getConfirmationSmsAuto() != null) entity.setConfirmationSmsAuto(request.getConfirmationSmsAuto());
        if (request.getPolitiqueAnnulation() != null) entity.setPolitiqueAnnulation(request.getPolitiqueAnnulation());
        if (request.getConfigJson() != null) entity.setConfigJson(request.getConfigJson());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ConfigurationHotelResponse findById(String tenantId, Long id) {
        ConfigurationHotel entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Configuration non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ConfigurationHotelResponse findByTenantIdAndHotelId(String tenantId, String hotelId) {
        return repository.findByTenantIdAndHotelId(tenantId, hotelId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Configuration non trouvée"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigurationHotelResponse> findAll(String tenantId) {
        return repository.findByTenantIdAndDeletedFalse(tenantId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        ConfigurationHotel entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Configuration non trouvée"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String hotelId) {
        return repository.existsByTenantIdAndHotelId(tenantId, hotelId);
    }

    private ConfigurationHotelResponse mapToResponse(ConfigurationHotel entity) {
        return ConfigurationHotelResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId()).organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId()).heureCheckIn(entity.getHeureCheckIn())
                .heureCheckOut(entity.getHeureCheckOut()).delaiAnnulationHeures(entity.getDelaiAnnulationHeures())
                .cautionObligatoire(entity.getCautionObligatoire())
                .montantCautionDefaut(entity.getMontantCautionDefaut())
                .paiementAvantArrivee(entity.getPaiementAvantArrivee())
                .confirmationEmailAuto(entity.getConfirmationEmailAuto())
                .confirmationSmsAuto(entity.getConfirmationSmsAuto())
                .politiqueAnnulation(entity.getPolitiqueAnnulation()).configJson(entity.getConfigJson())
                .notes(entity.getNotes()).createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt()).modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion()).build();
    }
}
