package projet_hotelier.hotel.module.clientele.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientResponse;
import projet_hotelier.hotel.module.clientele.model.client.Client;
import projet_hotelier.hotel.module.clientele.model.client.ClientStatut;
import projet_hotelier.hotel.module.clientele.model.client.ClientSegment;
import projet_hotelier.hotel.module.clientele.model.client.TypeClient;
import projet_hotelier.hotel.module.clientele.model.client.RisqueChurn;
import projet_hotelier.hotel.module.clientele.model.client.Civilite;
import projet_hotelier.hotel.module.clientele.repository.client.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements projet_hotelier.hotel.module.clientele.service.client.ClientService {

    private final ClientRepository repository;

    @Override
    public ClientResponse create(String tenantId, CreateClientRequest request) {
        if (repository.existsByTenantIdAndEmail(tenantId, request.getEmail())) {
            throw new IllegalArgumentException("Un client avec cet email existe déjà");
        }
        Client entity = Client.builder()
                .tenantId(tenantId).organisationId(request.getOrganisationId()).hotelId(request.getHotelId())
                .nom(request.getNom()).prenom(request.getPrenom())
                .civilite(request.getCivilite() != null ? Civilite.valueOf(request.getCivilite()) : null)
                .email(request.getEmail()).telephone(request.getTelephone())
                .dateNaissance(request.getDateNaissance())
                .nationalite(request.getNationalite())
                .segment(request.getSegment() != null ? ClientSegment.valueOf(request.getSegment()) : ClientSegment.STANDARD)
                .statut(request.getStatut() != null ? ClientStatut.valueOf(request.getStatut()) : ClientStatut.ACTIF)
                .typeClient(request.getTypeClient() != null ? TypeClient.valueOf(request.getTypeClient()) : TypeClient.INDIVIDUEL)
                .risqueChurn(request.getRisqueChurn() != null ? RisqueChurn.valueOf(request.getRisqueChurn()) : RisqueChurn.FAIBLE)
                .chiffreAffairesTotal(request.getChiffreAffairesTotal() != null ? request.getChiffreAffairesTotal() : BigDecimal.ZERO)
                .nombreNuitees(request.getNombreNuitees() != null ? request.getNombreNuitees() : 0)
                .notes(request.getNotesInternes()).build();
        return mapToResponse(repository.save(entity));
    }

    @Override
    public ClientResponse update(String tenantId, Long id, UpdateClientRequest request) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        if (request.getNom() != null) entity.setNom(request.getNom());
        if (request.getPrenom() != null) entity.setPrenom(request.getPrenom());
        if (request.getCivilite() != null) entity.setCivilite(Civilite.valueOf(request.getCivilite()));
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getTelephone() != null) entity.setTelephone(request.getTelephone());
        if (request.getDateNaissance() != null) entity.setDateNaissance(request.getDateNaissance());
        if (request.getNationalite() != null) entity.setNationalite(request.getNationalite());
        if (request.getSegment() != null) entity.setSegment(ClientSegment.valueOf(request.getSegment()));
        if (request.getStatut() != null) entity.setStatut(ClientStatut.valueOf(request.getStatut()));
        if (request.getTypeClient() != null) entity.setTypeClient(TypeClient.valueOf(request.getTypeClient()));
        if (request.getRisqueChurn() != null) entity.setRisqueChurn(RisqueChurn.valueOf(request.getRisqueChurn()));
        if (request.getChiffreAffairesTotal() != null) entity.setChiffreAffairesTotal(request.getChiffreAffairesTotal());
        if (request.getNombreNuitees() != null) entity.setNombreNuitees(request.getNombreNuitees());
        if (request.getNotesInternes() != null) entity.setNotes(request.getNotesInternes());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(String tenantId, Long id) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findByEmail(String tenantId, String email) {
        return repository.findByTenantIdAndEmail(tenantId, email)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponse> findAll(String tenantId, Pageable pageable) {
        return repository.findByTenantIdAndDeletedFalse(tenantId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findBySegment(String tenantId, String segment) {
        return repository.findByTenantIdAndSegment(tenantId, segment).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByStatut(String tenantId, String statut) {
        return repository.findByTenantIdAndStatut(tenantId, statut).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByRisqueChurn(String tenantId, String risqueChurn) {
        return repository.findByTenantIdAndRisqueChurn(tenantId, risqueChurn).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String tenantId, Long id) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        if (!entity.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Accès non autorisé");
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String tenantId, String email) {
        return repository.existsByTenantIdAndEmail(tenantId, email);
    }

    private ClientResponse mapToResponse(Client entity) {
        String nomComplet = entity.getNom() + (entity.getPrenom() != null ? " " + entity.getPrenom() : "");
        return ClientResponse.builder()
                .id(entity.getId()).tenantId(entity.getTenantId()).organisationId(entity.getOrganisationId())
                .hotelId(entity.getHotelId()).nom(entity.getNom()).prenom(entity.getPrenom())
                .nomComplet(nomComplet)
                .civilite(entity.getCivilite() != null ? entity.getCivilite().name() : null)
                .email(entity.getEmail()).telephone(entity.getTelephone())
                .dateNaissance(entity.getDateNaissance())
                .nationalite(entity.getNationalite())
                .segment(entity.getSegment() != null ? entity.getSegment().name() : null)
                .statut(entity.getStatut() != null ? entity.getStatut().name() : null)
                .typeClient(entity.getTypeClient() != null ? entity.getTypeClient().name() : null)
                .risqueChurn(entity.getRisqueChurn() != null ? entity.getRisqueChurn().name() : null)
                .chiffreAffairesTotal(entity.getChiffreAffairesTotal())
                .nombreReservations(entity.getNombreSejours())
                .nombreNuitees(entity.getNombreNuitees())
                .notesInternes(entity.getNotes()).createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy()).modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy()).version(entity.getVersion()).build();
    }
}
