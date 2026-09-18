package projet_hotelier.hotel.module.planning.service.tarification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.planning.dto.request.tarification.CreateTarificationRequest;
import projet_hotelier.hotel.module.planning.dto.request.tarification.UpdateTarificationRequest;
import projet_hotelier.hotel.module.planning.dto.response.tarification.TarificationResponse;
import projet_hotelier.hotel.module.planning.mapper.TarificationMapper;
import projet_hotelier.hotel.module.planning.model.tarification.TarificationModel;
import projet_hotelier.hotel.module.planning.repository.TarificationRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service pour la gestion des grilles tarifaires.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TarificationService {

    private final TarificationRepository tarificationRepository;
    private final TarificationMapper tarificationMapper;

    public TarificationResponse create(CreateTarificationRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation tarification code={}", request.getCodeTarif());
        if (tarificationRepository.existsByCodeTarif(request.getCodeTarif())) {
            throw ConflictException.duplicate("Tarification", "codeTarif", request.getCodeTarif());
        }
        TarificationModel entity = tarificationMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        return tarificationMapper.toResponse(tarificationRepository.save(entity));
    }

    public TarificationResponse update(String uuid, UpdateTarificationRequest request, Long organisationId, String username) {
        TarificationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        tarificationMapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return tarificationMapper.toResponse(tarificationRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public TarificationResponse getByUuid(String uuid, Long organisationId) {
        return tarificationMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public TarificationResponse getByCode(String code, Long organisationId) {
        TarificationModel entity = tarificationRepository.findByCodeTarif(code)
                .orElseThrow(() -> new ResourceNotFoundException("Tarification", "codeTarif", code));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Tarification", "codeTarif", code);
        }
        return tarificationMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<TarificationResponse> getAll(Long organisationId) {
        return tarificationMapper.toResponseList(
                tarificationRepository.findByOrganisationIdAndActifTrue(organisationId));
    }

    @Transactional(readOnly = true)
    public Page<TarificationResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return tarificationRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(tarificationMapper::toResponse);
    }

    public void delete(String uuid, Long organisationId, String username) {
        TarificationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        tarificationRepository.save(entity);
    }

    private TarificationModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        TarificationModel entity = tarificationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Tarification", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Tarification", "uuid", uuid);
        }
        return entity;
    }
}
