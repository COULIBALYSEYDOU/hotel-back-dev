package projet_hotelier.hotel.module.rh.service.formation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.formation.CreateFormationRequest;
import projet_hotelier.hotel.module.rh.dto.request.formation.UpdateFormationRequest;
import projet_hotelier.hotel.module.rh.dto.response.formation.FormationResponse;
import projet_hotelier.hotel.module.rh.mapper.formation.FormationMapper;
import projet_hotelier.hotel.module.rh.model.formation.FormationModel;
import projet_hotelier.hotel.module.rh.repository.formation.FormationRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des formations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FormationService {

    private final FormationRepository formationRepository;
    private final FormationMapper formationMapper;

    public FormationResponse create(CreateFormationRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'une formation pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        FormationModel entity = formationMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        FormationModel saved = formationRepository.save(entity);
        log.info("Formation creee avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return formationMapper.toResponse(saved);
    }

    public FormationResponse update(String uuid, UpdateFormationRequest request, Long organisationId, String username) {
        log.info("Mise a jour de la formation avec uuid: {}", uuid);

        FormationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        formationMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        FormationModel updated = formationRepository.save(entity);
        log.info("Formation mise a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return formationMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public FormationResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation de la formation avec uuid: {}", uuid);
        FormationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return formationMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public FormationResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation de la formation avec id: {}", id);
        FormationModel entity = findByIdAndOrganisation(id, organisationId);
        return formationMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<FormationResponse> getAll(Long organisationId) {
        log.debug("Recuperation de toutes les formations pour l'organisation: {}", organisationId);
        List<FormationModel> entities = formationRepository.findByOrganisationIdAndActifTrue(organisationId, Pageable.unpaged()).getContent();
        return formationMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<FormationResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des formations pour l'organisation: {}", organisationId);
        Page<FormationModel> entities = formationRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(formationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<FormationResponse> getByEmploye(Long employeId) {
        log.debug("Recuperation des formations de l'employe: {}", employeId);
        List<FormationModel> entities = formationRepository.findByEmployeIdAndActifTrue(employeId);
        return formationMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public List<FormationResponse> getByStatut(Long organisationId, String statutFormation) {
        log.debug("Recuperation des formations avec statut: {}", statutFormation);
        List<FormationModel> entities = formationRepository.findByOrganisationIdAndStatutFormationAndActifTrue(organisationId, statutFormation);
        return formationMapper.toResponseList(entities);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression de la formation avec uuid: {}", uuid);
        FormationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        formationRepository.save(entity);
        log.info("Formation supprimee avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private FormationModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return formationRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Formation", uuid));
    }

    private FormationModel findByIdAndOrganisation(Long id, Long organisationId) {
        return formationRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Formation", id));
    }
}
