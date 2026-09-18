package projet_hotelier.hotel.module.rh.service.contrat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.contrat.CreateContratTravailRequest;
import projet_hotelier.hotel.module.rh.dto.request.contrat.UpdateContratTravailRequest;
import projet_hotelier.hotel.module.rh.dto.response.contrat.ContratTravailResponse;
import projet_hotelier.hotel.module.rh.mapper.contrat.ContratTravailMapper;
import projet_hotelier.hotel.module.rh.model.contrat.ContratTravailModel;
import projet_hotelier.hotel.module.rh.repository.contrat.ContratTravailRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des contrats de travail.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ContratTravailService {

    private final ContratTravailRepository contratTravailRepository;
    private final ContratTravailMapper contratTravailMapper;

    public ContratTravailResponse create(CreateContratTravailRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'un contrat de travail pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        ContratTravailModel entity = contratTravailMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        ContratTravailModel saved = contratTravailRepository.save(entity);
        log.info("Contrat de travail cree avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return contratTravailMapper.toResponse(saved);
    }

    public ContratTravailResponse update(String uuid, UpdateContratTravailRequest request, Long organisationId, String username) {
        log.info("Mise a jour du contrat de travail avec uuid: {}", uuid);

        ContratTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        contratTravailMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        ContratTravailModel updated = contratTravailRepository.save(entity);
        log.info("Contrat de travail mis a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return contratTravailMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public ContratTravailResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation du contrat de travail avec uuid: {}", uuid);
        ContratTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return contratTravailMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public ContratTravailResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation du contrat de travail avec id: {}", id);
        ContratTravailModel entity = findByIdAndOrganisation(id, organisationId);
        return contratTravailMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ContratTravailResponse> getAll(Long organisationId) {
        log.debug("Recuperation de tous les contrats de travail pour l'organisation: {}", organisationId);
        List<ContratTravailModel> entities = contratTravailRepository.findByOrganisationIdAndActifTrue(organisationId);
        return contratTravailMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<ContratTravailResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des contrats de travail pour l'organisation: {}", organisationId);
        Page<ContratTravailModel> entities = contratTravailRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(contratTravailMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ContratTravailResponse> getByEmploye(Long employeId) {
        log.debug("Recuperation des contrats de travail de l'employe: {}", employeId);
        List<ContratTravailModel> entities = contratTravailRepository.findByEmployeIdAndActifTrue(employeId);
        return contratTravailMapper.toResponseList(entities);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression du contrat de travail avec uuid: {}", uuid);
        ContratTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        contratTravailRepository.save(entity);
        log.info("Contrat de travail supprime avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private ContratTravailModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return contratTravailRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("ContratTravail", uuid));
    }

    private ContratTravailModel findByIdAndOrganisation(Long id, Long organisationId) {
        return contratTravailRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("ContratTravail", id));
    }
}
