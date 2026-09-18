package projet_hotelier.hotel.module.rh.service.recrutement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.recrutement.CreateRecrutementRequest;
import projet_hotelier.hotel.module.rh.dto.request.recrutement.UpdateRecrutementRequest;
import projet_hotelier.hotel.module.rh.dto.response.recrutement.RecrutementResponse;
import projet_hotelier.hotel.module.rh.mapper.recrutement.RecrutementMapper;
import projet_hotelier.hotel.module.rh.model.recrutement.RecrutementModel;
import projet_hotelier.hotel.module.rh.repository.recrutement.RecrutementRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des recrutements.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecrutementService {

    private final RecrutementRepository recrutementRepository;
    private final RecrutementMapper recrutementMapper;

    public RecrutementResponse create(CreateRecrutementRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'un recrutement pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        RecrutementModel entity = recrutementMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        RecrutementModel saved = recrutementRepository.save(entity);
        log.info("Recrutement cree avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return recrutementMapper.toResponse(saved);
    }

    public RecrutementResponse update(String uuid, UpdateRecrutementRequest request, Long organisationId, String username) {
        log.info("Mise a jour du recrutement avec uuid: {}", uuid);

        RecrutementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        recrutementMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        RecrutementModel updated = recrutementRepository.save(entity);
        log.info("Recrutement mis a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return recrutementMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public RecrutementResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation du recrutement avec uuid: {}", uuid);
        RecrutementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return recrutementMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public RecrutementResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation du recrutement avec id: {}", id);
        RecrutementModel entity = findByIdAndOrganisation(id, organisationId);
        return recrutementMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<RecrutementResponse> getAll(Long organisationId) {
        log.debug("Recuperation de tous les recrutements pour l'organisation: {}", organisationId);
        List<RecrutementModel> entities = recrutementRepository.findByOrganisationIdAndActifTrue(organisationId, Pageable.unpaged()).getContent();
        return recrutementMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<RecrutementResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des recrutements pour l'organisation: {}", organisationId);
        Page<RecrutementModel> entities = recrutementRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(recrutementMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RecrutementResponse> getByStatut(Long organisationId, String statutCandidature) {
        log.debug("Recuperation des recrutements avec statut: {}", statutCandidature);
        List<RecrutementModel> entities = recrutementRepository.findByOrganisationIdAndStatutCandidatureAndActifTrue(organisationId, statutCandidature);
        return recrutementMapper.toResponseList(entities);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression du recrutement avec uuid: {}", uuid);
        RecrutementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        recrutementRepository.save(entity);
        log.info("Recrutement supprime avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private RecrutementModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return recrutementRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Recrutement", uuid));
    }

    private RecrutementModel findByIdAndOrganisation(Long id, Long organisationId) {
        return recrutementRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Recrutement", id));
    }
}
