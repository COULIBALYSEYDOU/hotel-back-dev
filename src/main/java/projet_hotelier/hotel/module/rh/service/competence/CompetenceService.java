package projet_hotelier.hotel.module.rh.service.competence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.competence.CreateCompetenceRequest;
import projet_hotelier.hotel.module.rh.dto.request.competence.UpdateCompetenceRequest;
import projet_hotelier.hotel.module.rh.dto.response.competence.CompetenceResponse;
import projet_hotelier.hotel.module.rh.mapper.competence.CompetenceMapper;
import projet_hotelier.hotel.module.rh.model.competence.CompetenceModel;
import projet_hotelier.hotel.module.rh.repository.competence.CompetenceRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des compétences.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompetenceService {

    private final CompetenceRepository competenceRepository;
    private final CompetenceMapper competenceMapper;

    public CompetenceResponse create(CreateCompetenceRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'une competence pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        CompetenceModel entity = competenceMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        CompetenceModel saved = competenceRepository.save(entity);
        log.info("Competence creee avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return competenceMapper.toResponse(saved);
    }

    public CompetenceResponse update(String uuid, UpdateCompetenceRequest request, Long organisationId, String username) {
        log.info("Mise a jour de la competence avec uuid: {}", uuid);

        CompetenceModel entity = findByUuidAndOrganisation(uuid, organisationId);
        competenceMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        CompetenceModel updated = competenceRepository.save(entity);
        log.info("Competence mise a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return competenceMapper.toResponse(updated);
    }

    public CompetenceResponse valider(String uuid, Long validateurId, Long organisationId, String username) {
        log.info("Validation de la competence avec uuid: {}", uuid);
        CompetenceModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setStatutValidation("VALIDE");
        entity.setValideParId(validateurId);
        entity.setDateValidation(LocalDate.now());
        entity.setModifiePar(username);
        CompetenceModel updated = competenceRepository.save(entity);
        return competenceMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public CompetenceResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation de la competence avec uuid: {}", uuid);
        CompetenceModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return competenceMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<CompetenceResponse> getByEmploye(Long employeId) {
        log.debug("Recuperation des competences de l'employe: {}", employeId);
        List<CompetenceModel> entities = competenceRepository.findByEmployeIdAndActifTrue(employeId);
        return competenceMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public List<CompetenceResponse> getExpirantBientot(Long organisationId, LocalDate dateLimite) {
        log.debug("Recuperation des competences expirant avant: {}", dateLimite);
        List<CompetenceModel> entities = competenceRepository.findByOrganisationIdAndDateExpirationBeforeAndActifTrue(organisationId, dateLimite);
        return competenceMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<CompetenceResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des competences pour l'organisation: {}", organisationId);
        Page<CompetenceModel> entities = competenceRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(competenceMapper::toResponse);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression de la competence avec uuid: {}", uuid);
        CompetenceModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        competenceRepository.save(entity);
        log.info("Competence supprimee avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private CompetenceModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return competenceRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("Competence", uuid));
    }
}
