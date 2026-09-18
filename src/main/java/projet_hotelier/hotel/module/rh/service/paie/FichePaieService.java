package projet_hotelier.hotel.module.rh.service.paie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.request.paie.CreateFichePaieRequest;
import projet_hotelier.hotel.module.rh.dto.request.paie.UpdateFichePaieRequest;
import projet_hotelier.hotel.module.rh.dto.response.paie.FichePaieResponse;
import projet_hotelier.hotel.module.rh.mapper.paie.FichePaieMapper;
import projet_hotelier.hotel.module.rh.model.paie.FichePaieModel;
import projet_hotelier.hotel.module.rh.repository.paie.FichePaieRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des fiches de paie.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FichePaieService {

    private final FichePaieRepository fichePaieRepository;
    private final FichePaieMapper fichePaieMapper;

    public FichePaieResponse create(CreateFichePaieRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation d'une fiche de paie pour l'organisation {} et l'hotel {}", organisationId, hotelId);

        // Verification unicite fiche de paie pour un employe, mois et annee
        if (fichePaieRepository.findByEmployeIdAndMoisAndAnnee(request.getEmployeId(), request.getMois(), request.getAnnee()).isPresent()) {
            throw new ConflictException("FICHE_PAIE_EXISTS",
                    String.format("Une fiche de paie existe deja pour l'employe %d pour le mois %d/%d",
                            request.getEmployeId(), request.getMois(), request.getAnnee()));
        }

        FichePaieModel entity = fichePaieMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);

        FichePaieModel saved = fichePaieRepository.save(entity);
        log.info("Fiche de paie creee avec succes: id={}, uuid={}", saved.getId(), saved.getUuid());

        return fichePaieMapper.toResponse(saved);
    }

    public FichePaieResponse update(String uuid, UpdateFichePaieRequest request, Long organisationId, String username) {
        log.info("Mise a jour de la fiche de paie avec uuid: {}", uuid);

        FichePaieModel entity = findByUuidAndOrganisation(uuid, organisationId);
        fichePaieMapper.updateEntity(entity, request);
        entity.setModifiePar(username);

        FichePaieModel updated = fichePaieRepository.save(entity);
        log.info("Fiche de paie mise a jour avec succes: id={}, uuid={}", updated.getId(), updated.getUuid());

        return fichePaieMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public FichePaieResponse getByUuid(String uuid, Long organisationId) {
        log.debug("Recuperation de la fiche de paie avec uuid: {}", uuid);
        FichePaieModel entity = findByUuidAndOrganisation(uuid, organisationId);
        return fichePaieMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public FichePaieResponse getById(Long id, Long organisationId) {
        log.debug("Recuperation de la fiche de paie avec id: {}", id);
        FichePaieModel entity = findByIdAndOrganisation(id, organisationId);
        return fichePaieMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<FichePaieResponse> getAll(Long organisationId) {
        log.debug("Recuperation de toutes les fiches de paie pour l'organisation: {}", organisationId);
        List<FichePaieModel> entities = fichePaieRepository.findByOrganisationIdAndActifTrue(organisationId, Pageable.unpaged()).getContent();
        return fichePaieMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public Page<FichePaieResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        log.debug("Recuperation paginee des fiches de paie pour l'organisation: {}", organisationId);
        Page<FichePaieModel> entities = fichePaieRepository.findByOrganisationIdAndActifTrue(organisationId, pageable);
        return entities.map(fichePaieMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<FichePaieResponse> getByEmploye(Long employeId) {
        log.debug("Recuperation des fiches de paie de l'employe: {}", employeId);
        List<FichePaieModel> entities = fichePaieRepository.findByEmployeIdAndActifTrue(employeId);
        return fichePaieMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public FichePaieResponse getByEmployeAndPeriod(Long employeId, Integer mois, Integer annee) {
        log.debug("Recuperation de la fiche de paie de l'employe {} pour {}/{}", employeId, mois, annee);
        FichePaieModel entity = fichePaieRepository.findByEmployeIdAndMoisAndAnnee(employeId, mois, annee)
                .orElseThrow(() -> new ResourceNotFoundException("FichePaie",
                        String.format("employeId=%d, mois=%d, annee=%d", employeId, mois, annee)));
        return fichePaieMapper.toResponse(entity);
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression de la fiche de paie avec uuid: {}", uuid);
        FichePaieModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setSupprime(true);
        entity.setActif(false);
        entity.setModifiePar(username);
        fichePaieRepository.save(entity);
        log.info("Fiche de paie supprimee avec succes: id={}, uuid={}", entity.getId(), entity.getUuid());
    }

    private FichePaieModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        return fichePaieRepository.findByUuid(uuid)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("FichePaie", uuid));
    }

    private FichePaieModel findByIdAndOrganisation(Long id, Long organisationId) {
        return fichePaieRepository.findById(id)
                .filter(e -> e.getOrganisationId().equals(organisationId))
                .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
                .orElseThrow(() -> new ResourceNotFoundException("FichePaie", id));
    }
}
