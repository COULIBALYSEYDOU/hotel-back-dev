package projet_hotelier.hotel.module.rh.service.conge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.conge.CreateCongeRequest;
import projet_hotelier.hotel.module.rh.dto.request.conge.UpdateCongeRequest;
import projet_hotelier.hotel.module.rh.dto.response.conge.CongeResponse;
import projet_hotelier.hotel.module.rh.mapper.conge.CongeMapper;
import projet_hotelier.hotel.module.rh.model.conge.CongeModel;
import projet_hotelier.hotel.module.rh.repository.conge.CongeRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des conges.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CongeService {

    private final CongeRepository congeRepository;
    private final CongeMapper congeMapper;

    public CongeResponse create(CreateCongeRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'un conge pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        CongeModel entity = congeMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        CongeModel saved = congeRepository.save(entity);
        log.info("Conge cree avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return congeMapper.toResponse(saved);
    }

    public CongeResponse update(String uuid, UpdateCongeRequest request, Long organisationId, String username) {
        log.info("Mise a jour du conge avec uuid: {}", uuid);

        CongeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        congeMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        CongeModel updated = congeRepository.save(entity);
        log.info("Conge mis a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return congeMapper.toResponse(updated);
    }

    public CongeResponse approve(String uuid, Long approbateurId, Long organisationId, String username) {
        log.info("Approbation du conge avec uuid: {}", uuid);
        CongeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setStatutConge("APPROUVE");
        entity.setApprouvePar(approbateurId);
        entity.setDateApprobation(LocalDate.now());
        entity.setModifiePar(username);
        CongeModel updated = congeRepository.save(entity);
        return congeMapper.toResponse(updated);
    }

    public CongeResponse reject(String uuid, String motif, Long organisationId, String username) {
        log.info("Rejet du conge avec uuid: {}", uuid);
        CongeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setStatutConge("REJETE");
        entity.setMotif(motif);
        entity.setModifiePar(username);
        CongeModel updated = congeRepository.save(entity);
        return congeMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public CongeResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation du conge avec uuid: {}", uuid);
        CongeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return congeMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public CongeResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation du conge avec id: {}", id);
        CongeModel entity = findByIdAndOrganisation(id, organisationId);
        return congeMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<CongeResponse> getAll(Long organisationId) {
        log.debug("Recuperation de tous les conges pour l'organisation: {}", organisationId);
        List<CongeModel> entities = congeRepository.findByOrganisationIdAndActifTrue(organisationId, Pageable.unpaged()).getContent();
        return congeMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<CongeResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des conges pour l'organisation: {}", organisationId);
        Page<CongeModel> entities = congeRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(congeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<CongeResponse> getByEmploye(Long employeId) {
        log.debug("Recuperation des conges de l'employe: {}", employeId);
        List<CongeModel> entities = congeRepository.findByEmployeIdAndActifTrue(employeId);
        return congeMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public List<CongeResponse> getByStatut(Long organisationId, String statutConge) {
        log.debug("Recuperation des conges avec statut: {}", statutConge);
        List<CongeModel> entities = congeRepository.findByOrganisationIdAndStatutCongeAndActifTrue(organisationId, statutConge);
        return congeMapper.toResponseList(entities);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression du conge avec uuid: {}", uuid);
        CongeModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        congeRepository.save(entity);
        log.info("Conge supprime avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private CongeModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return congeRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Conge", uuid));
    }

    private CongeModel findByIdAndOrganisation(Long id, Long organisationId) {
        return congeRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Conge", id));
    }
}
