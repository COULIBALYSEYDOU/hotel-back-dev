package projet_hotelier.hotel.module.clientele.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientProfilRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientProfilRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientProfilResponse;
import projet_hotelier.hotel.module.clientele.model.client.Client;
import projet_hotelier.hotel.module.clientele.model.client.ClientProfil;
import projet_hotelier.hotel.module.clientele.repository.client.ClientProfilRepository;
import projet_hotelier.hotel.module.clientele.repository.client.ClientRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientProfilServiceImpl implements projet_hotelier.hotel.module.clientele.service.client.ClientProfilService {

    private final ClientProfilRepository repository;
    private final ClientRepository clientRepository;

    @Override
    public ClientProfilResponse create(String tenantId, CreateClientProfilRequest request) {
        if (repository.existsByClientId(request.getClientId())) {
            throw new IllegalArgumentException("Un profil existe déjà pour ce client");
        }
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        if (!client.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        ClientProfil entity = ClientProfil.builder()
                .tenantId(tenantId).organisationId(client.getOrganisationId()).hotelId(client.getHotelId())
                .client(client).profession(request.getProfession()).entreprise(request.getEntreprise())
                .secteurActivite(request.getSecteurActivite()).nombreEnfants(request.getNombreEnfants())
                .budgetMoyenNuitee(request.getBudgetMoyenNuitee())
                .accepteMarketing(request.getAccepteMarketing())
                .accepteNewsletter(request.getAccepteNewsletter())
                .accepteSms(request.getAccepteSms())
                .notesInternes(request.getNotesInternes())
                .allergies(request.getAllergies())
                .besoinsSpeciaux(request.getBesoinsSpeciaux()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public ClientProfilResponse update(String tenantId, Long id, UpdateClientProfilRequest request) {
        ClientProfil entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getProfession() != null) entity.setProfession(request.getProfession());
        if (request.getEntreprise() != null) entity.setEntreprise(request.getEntreprise());
        if (request.getSecteurActivite() != null) entity.setSecteurActivite(request.getSecteurActivite());
        if (request.getNombreEnfants() != null) entity.setNombreEnfants(request.getNombreEnfants());
        if (request.getBudgetMoyenNuitee() != null) entity.setBudgetMoyenNuitee(request.getBudgetMoyenNuitee());
        if (request.getAccepteMarketing() != null) entity.setAccepteMarketing(request.getAccepteMarketing());
        if (request.getAccepteNewsletter() != null) entity.setAccepteNewsletter(request.getAccepteNewsletter());
        if (request.getAccepteSms() != null) entity.setAccepteSms(request.getAccepteSms());
        if (request.getNotesInternes() != null) entity.setNotesInternes(request.getNotesInternes());
        if (request.getAllergies() != null) entity.setAllergies(request.getAllergies());
        if (request.getBesoinsSpeciaux() != null) entity.setBesoinsSpeciaux(request.getBesoinsSpeciaux());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientProfilResponse findById(String tenantId, Long id) {
        ClientProfil entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientProfilResponse findByClientId(String tenantId, Long clientId) {
        return repository.findByTenantIdAndClientId(tenantId, clientId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé"));
    }

    @Override
    public void delete(String tenantId, Long id) {
        ClientProfil entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé"));
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

    private ClientProfilResponse mapToResponse(ClientProfil entity) {
        return ClientProfilResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId())
                .clientId(entity.getClient() != null ? entity.getClient().getId() : null)
                .profession(entity.getProfession()).entreprise(entity.getEntreprise())
                .secteurActivite(entity.getSecteurActivite()).nombreEnfants(entity.getNombreEnfants())
                .budgetMoyenNuitee(entity.getBudgetMoyenNuitee())
                .accepteMarketing(entity.getAccepteMarketing())
                .accepteNewsletter(entity.getAccepteNewsletter())
                .accepteSms(entity.getAccepteSms())
                .notesInternes(entity.getNotesInternes())
                .allergies(entity.getAllergies())
                .besoinsSpeciaux(entity.getBesoinsSpeciaux())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt()).modifiedBy(entity.getModifiedBy())
                .version(entity.getVersion()).build();
    }
}
