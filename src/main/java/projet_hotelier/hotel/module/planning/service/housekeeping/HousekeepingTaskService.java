package projet_hotelier.hotel.module.planning.service.housekeeping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.planning.dto.request.housekeeping.CreateHousekeepingTaskRequest;
import projet_hotelier.hotel.module.planning.dto.request.housekeeping.UpdateHousekeepingTaskRequest;
import projet_hotelier.hotel.module.planning.dto.response.housekeeping.HousekeepingTaskResponse;
import projet_hotelier.hotel.module.planning.mapper.HousekeepingTaskMapper;
import projet_hotelier.hotel.module.planning.model.housekeeping.HousekeepingTaskModel;
import projet_hotelier.hotel.module.planning.repository.HousekeepingTaskRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service pour la gestion des taches de housekeeping (menage, maintenance chambres).
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HousekeepingTaskService {

    private final HousekeepingTaskRepository housekeepingRepository;
    private final HousekeepingTaskMapper housekeepingMapper;

    public HousekeepingTaskResponse create(CreateHousekeepingTaskRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation tache housekeeping chambre={} type={}", request.getChambreId(), request.getTypeTache());
        HousekeepingTaskModel entity = housekeepingMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getStatut() == null || entity.getStatut().isBlank()) {
            entity.setStatut("A_FAIRE");
        }
        return housekeepingMapper.toResponse(housekeepingRepository.save(entity));
    }

    public HousekeepingTaskResponse update(String uuid, UpdateHousekeepingTaskRequest request, Long organisationId, String username) {
        HousekeepingTaskModel entity = findByUuidAndOrganisation(uuid, organisationId);
        housekeepingMapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return housekeepingMapper.toResponse(housekeepingRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public HousekeepingTaskResponse getByUuid(String uuid, Long organisationId) {
        return housekeepingMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<HousekeepingTaskResponse> getByChambre(Long chambreId, Long organisationId) {
        return housekeepingRepository.findByChambreIdAndActifTrue(chambreId).stream()
                .filter(t -> t.getOrganisationId().equals(organisationId))
                .map(housekeepingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<HousekeepingTaskResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return housekeepingRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(housekeepingMapper::toResponse);
    }

    public HousekeepingTaskResponse marquerExecutee(String uuid, Long organisationId, String username) {
        HousekeepingTaskModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setStatut("EXECUTEE");
        entity.setDateExecution(LocalDateTime.now());
        entity.setModifiePar(username);
        return housekeepingMapper.toResponse(housekeepingRepository.save(entity));
    }

    public void delete(String uuid, Long organisationId, String username) {
        HousekeepingTaskModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        housekeepingRepository.save(entity);
    }

    private HousekeepingTaskModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        HousekeepingTaskModel entity = housekeepingRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("HousekeepingTask", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("HousekeepingTask", "uuid", uuid);
        }
        return entity;
    }
}
