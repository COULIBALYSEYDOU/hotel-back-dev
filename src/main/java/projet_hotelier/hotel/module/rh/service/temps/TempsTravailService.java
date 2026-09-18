package projet_hotelier.hotel.module.rh.service.temps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.temps.CreateTempsTravailRequest;
import projet_hotelier.hotel.module.rh.dto.request.temps.UpdateTempsTravailRequest;
import projet_hotelier.hotel.module.rh.dto.response.temps.TempsTravailResponse;
import projet_hotelier.hotel.module.rh.mapper.temps.TempsTravailMapper;
import projet_hotelier.hotel.module.rh.model.temps.TempsTravailModel;
import projet_hotelier.hotel.module.rh.repository.temps.TempsTravailRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des temps de travail.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TempsTravailService {

    private final TempsTravailRepository tempsTravailRepository;
    private final TempsTravailMapper tempsTravailMapper;

    public TempsTravailResponse create(CreateTempsTravailRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'un temps de travail pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        TempsTravailModel entity = tempsTravailMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        TempsTravailModel saved = tempsTravailRepository.save(entity);
        log.info("Temps de travail cree avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return tempsTravailMapper.toResponse(saved);
    }

    public TempsTravailResponse update(String uuid, UpdateTempsTravailRequest request, Long organisationId, String username) {
        log.info("Mise a jour du temps de travail avec uuid: {}", uuid);

        TempsTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        tempsTravailMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        TempsTravailModel updated = tempsTravailRepository.save(entity);
        log.info("Temps de travail mis a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return tempsTravailMapper.toResponse(updated);
    }

    public TempsTravailResponse validate(String uuid, Long validateurId, Long organisationId, String username) {
        log.info("Validation du temps de travail avec uuid: {}", uuid);
        TempsTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setValide(true);
        entity.setStatutValidation("VALIDE");
        entity.setValidateurId(validateurId);
        entity.setModifiePar(username);
        TempsTravailModel updated = tempsTravailRepository.save(entity);
        return tempsTravailMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public TempsTravailResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation du temps de travail avec uuid: {}", uuid);
        TempsTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return tempsTravailMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public TempsTravailResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation du temps de travail avec id: {}", id);
        TempsTravailModel entity = findByIdAndOrganisation(id, organisationId);
        return tempsTravailMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<TempsTravailResponse> getAll(Long organisationId) {
        log.debug("Recuperation de tous les temps de travail pour l'organisation: {}", organisationId);
        List<TempsTravailModel> entities = tempsTravailRepository.findByOrganisationIdAndActifTrue(organisationId, Pageable.unpaged()).getContent();
        return tempsTravailMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<TempsTravailResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des temps de travail pour l'organisation: {}", organisationId);
        Page<TempsTravailModel> entities = tempsTravailRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(tempsTravailMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<TempsTravailResponse> getByEmploye(Long employeId) {
        log.debug("Recuperation des temps de travail de l'employe: {}", employeId);
        List<TempsTravailModel> entities = tempsTravailRepository.findByEmployeIdAndActifTrue(employeId);
        return tempsTravailMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public List<TempsTravailResponse> getByDate(Long organisationId, LocalDate dateJour) {
        log.debug("Recuperation des temps de travail pour la date: {}", dateJour);
        List<TempsTravailModel> entities = tempsTravailRepository.findByOrganisationIdAndDateJourAndActifTrue(organisationId, dateJour);
        return tempsTravailMapper.toResponseList(entities);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression du temps de travail avec uuid: {}", uuid);
        TempsTravailModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        tempsTravailRepository.save(entity);
        log.info("Temps de travail supprime avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private TempsTravailModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return tempsTravailRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("TempsTravail", uuid));
    }

    private TempsTravailModel findByIdAndOrganisation(Long id, Long organisationId) {
        return tempsTravailRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("TempsTravail", id));
    }
}
