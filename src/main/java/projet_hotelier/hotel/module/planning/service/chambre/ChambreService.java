package projet_hotelier.hotel.module.planning.service.chambre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.planning.dto.request.chambre.CreateChambreRequest;
import projet_hotelier.hotel.module.planning.dto.request.chambre.UpdateChambreRequest;
import projet_hotelier.hotel.module.planning.dto.response.chambre.ChambreResponse;
import projet_hotelier.hotel.module.planning.mapper.ChambreMapper;
import projet_hotelier.hotel.module.planning.model.chambre.ChambreModel;
import projet_hotelier.hotel.module.planning.repository.ChambreRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des chambres d'hotel.
 * CRUD complet, multi-tenant (filtrage par organisationId).
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChambreService {

    private final ChambreRepository chambreRepository;
    private final ChambreMapper chambreMapper;

    public ChambreResponse create(CreateChambreRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation chambre numero={} pour organisation={}", request.getNumero(), organisationId);
        if (chambreRepository.existsByNumero(request.getNumero())) {
            throw ConflictException.duplicate("Chambre", "numero", request.getNumero());
        }
        ChambreModel entity = chambreMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        ChambreModel saved = chambreRepository.save(entity);
        log.info("Chambre creee id={}, uuid={}", saved.getId(), saved.getUuid());
        return chambreMapper.toResponse(saved);
    }

    public ChambreResponse update(String uuid, UpdateChambreRequest request, Long organisationId, String username) {
        log.info("Mise a jour chambre uuid={}", uuid);
        ChambreModel entity = findByUuidAndOrganisation(uuid, organisationId);
        chambreMapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        ChambreModel updated = chambreRepository.save(entity);
        return chambreMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public ChambreResponse getByUuid(String uuid, Long organisationId) {
        return chambreMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public ChambreResponse getByNumero(String numero, Long organisationId) {
        ChambreModel entity = chambreRepository.findByNumero(numero)
                .orElseThrow(() -> new ResourceNotFoundException("Chambre", "numero", numero));
        ensureSameOrganisation(entity, organisationId);
        return chambreMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ChambreResponse> getAll(Long organisationId) {
        return chambreMapper.toResponseList(
                chambreRepository.findByOrganisationIdAndActifTrue(organisationId));
    }

    @Transactional(readOnly = true)
    public Page<ChambreResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return chambreRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(chambreMapper::toResponse);
    }

    public ChambreResponse setHorsService(String uuid, boolean horsService, String motif, Long organisationId, String username) {
        log.info("Chambre uuid={} hors-service={} motif={}", uuid, horsService, motif);
        ChambreModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setHorsService(horsService);
        entity.setMotifHorsService(horsService ? motif : null);
        entity.setModifiePar(username);
        return chambreMapper.toResponse(chambreRepository.save(entity));
    }

    public void delete(String uuid, Long organisationId, String username) {
        log.info("Suppression logique chambre uuid={}", uuid);
        ChambreModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        chambreRepository.save(entity);
    }

    private ChambreModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        ChambreModel entity = chambreRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Chambre", "uuid", uuid));
        ensureSameOrganisation(entity, organisationId);
        return entity;
    }

    private void ensureSameOrganisation(ChambreModel entity, Long organisationId) {
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Chambre", "uuid", entity.getUuid());
        }
    }
}
