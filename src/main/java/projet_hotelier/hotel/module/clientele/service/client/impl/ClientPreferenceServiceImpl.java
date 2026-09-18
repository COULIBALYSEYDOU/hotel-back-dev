package projet_hotelier.hotel.module.clientele.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientPreferenceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientPreferenceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientPreferenceResponse;
import projet_hotelier.hotel.module.clientele.model.client.Client;
import projet_hotelier.hotel.module.clientele.model.client.ClientPreference;
import projet_hotelier.hotel.module.clientele.repository.client.ClientPreferenceRepository;
import projet_hotelier.hotel.module.clientele.repository.client.ClientRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientPreferenceServiceImpl implements projet_hotelier.hotel.module.clientele.service.client.ClientPreferenceService {

    private final ClientPreferenceRepository repository;
    private final ClientRepository clientRepository;

    @Override
    public ClientPreferenceResponse create(String tenantId, CreateClientPreferenceRequest request) {
        if (repository.existsByClientId(request.getClientId())) {
            throw new IllegalArgumentException("Des préférences existent déjà pour ce client");
        }
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        if (!client.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        ClientPreference entity = ClientPreference.builder()
                .tenantId(tenantId).organisationId(client.getOrganisationId()).hotelId(client.getHotelId())
                .client(client).typeChambrePreferee(request.getTypeChambrePreferee())
                .etagePrefere(request.getEtagePrefere()).vuePreferee(request.getVuePreferee())
                .typeOreiller(request.getTypeOreiller())
                .temperatureChambre(request.getTemperatureChambre())
                .minibarPersonnalise(request.getMinibarPersonnalise())
                .journauxPreferes(request.getJournauxPreferes())
                .heureReveilPreferee(request.getHeureReveilPreferee())
                .preferencesRestaurant(request.getPreferencesRestaurant())
                .regimeAlimentaire(request.getRegimeAlimentaire()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public ClientPreferenceResponse update(String tenantId, Long id, UpdateClientPreferenceRequest request) {
        ClientPreference entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préférences non trouvées"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getTypeChambrePreferee() != null) entity.setTypeChambrePreferee(request.getTypeChambrePreferee());
        if (request.getEtagePrefere() != null) entity.setEtagePrefere(request.getEtagePrefere());
        if (request.getVuePreferee() != null) entity.setVuePreferee(request.getVuePreferee());
        if (request.getTypeOreiller() != null) entity.setTypeOreiller(request.getTypeOreiller());
        if (request.getTemperatureChambre() != null) entity.setTemperatureChambre(request.getTemperatureChambre());
        if (request.getMinibarPersonnalise() != null) entity.setMinibarPersonnalise(request.getMinibarPersonnalise());
        if (request.getJournauxPreferes() != null) entity.setJournauxPreferes(request.getJournauxPreferes());
        if (request.getHeureReveilPreferee() != null) entity.setHeureReveilPreferee(request.getHeureReveilPreferee());
        if (request.getPreferencesRestaurant() != null) entity.setPreferencesRestaurant(request.getPreferencesRestaurant());
        if (request.getRegimeAlimentaire() != null) entity.setRegimeAlimentaire(request.getRegimeAlimentaire());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientPreferenceResponse findById(String tenantId, Long id) {
        ClientPreference entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préférences non trouvées"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientPreferenceResponse findByClientId(String tenantId, Long clientId) {
        return repository.findByTenantIdAndClientId(tenantId, clientId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Préférences non trouvées"));
    }

    @Override
    public void delete(String tenantId, Long id) {
        ClientPreference entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préférences non trouvées"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, Long clientId) {
        return repository.existsByClientId(clientId);
    }

    private ClientPreferenceResponse mapToResponse(ClientPreference entity) {
        return ClientPreferenceResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId())
                .clientId(entity.getClient() != null ? entity.getClient().getId() : null)
                .typeChambrePreferee(entity.getTypeChambrePreferee())
                .etagePrefere(entity.getEtagePrefere()).vuePreferee(entity.getVuePreferee())
                .typeOreiller(entity.getTypeOreiller())
                .temperatureChambre(entity.getTemperatureChambre())
                .minibarPersonnalise(entity.getMinibarPersonnalise())
                .journauxPreferes(entity.getJournauxPreferes())
                .heureReveilPreferee(entity.getHeureReveilPreferee())
                .preferencesRestaurant(entity.getPreferencesRestaurant())
                .regimeAlimentaire(entity.getRegimeAlimentaire())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt()).modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion()).build();
    }
}
