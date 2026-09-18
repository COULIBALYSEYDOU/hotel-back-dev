package projet_hotelier.hotel.module.planning.service.evenement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.planning.dto.request.evenement.CreateEvenementHotelRequest;
import projet_hotelier.hotel.module.planning.dto.request.evenement.UpdateEvenementHotelRequest;
import projet_hotelier.hotel.module.planning.dto.response.evenement.EvenementHotelResponse;
import projet_hotelier.hotel.module.planning.mapper.EvenementHotelMapper;
import projet_hotelier.hotel.module.planning.model.evenement.EvenementHotelModel;
import projet_hotelier.hotel.module.planning.repository.EvenementHotelRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;
import projet_hotelier.hotel.shared.exception.ValidationException;

import java.util.List;

/**
 * Service pour la gestion des evenements hoteliers (mariages, conferences, etc.).
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EvenementHotelService {

    private final EvenementHotelRepository evenementRepository;
    private final EvenementHotelMapper evenementMapper;

    public EvenementHotelResponse create(CreateEvenementHotelRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation evenement code={}", request.getCodeEvenement());
        if (request.getDateFin() != null && request.getDateDebut() != null
                && !request.getDateFin().isAfter(request.getDateDebut())) {
            throw new ValidationException("La date de fin doit etre posterieure a la date de debut");
        }
        if (evenementRepository.existsByCodeEvenement(request.getCodeEvenement())) {
            throw ConflictException.duplicate("Evenement", "codeEvenement", request.getCodeEvenement());
        }
        EvenementHotelModel entity = evenementMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getStatut() == null || entity.getStatut().isBlank()) {
            entity.setStatut("PLANIFIE");
        }
        return evenementMapper.toResponse(evenementRepository.save(entity));
    }

    public EvenementHotelResponse update(String uuid, UpdateEvenementHotelRequest request, Long organisationId, String username) {
        EvenementHotelModel entity = findByUuidAndOrganisation(uuid, organisationId);
        evenementMapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return evenementMapper.toResponse(evenementRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public EvenementHotelResponse getByUuid(String uuid, Long organisationId) {
        return evenementMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public EvenementHotelResponse getByCode(String code, Long organisationId) {
        EvenementHotelModel entity = evenementRepository.findByCodeEvenement(code)
                .orElseThrow(() -> new ResourceNotFoundException("Evenement", "codeEvenement", code));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Evenement", "codeEvenement", code);
        }
        return evenementMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<EvenementHotelResponse> getAll(Long organisationId) {
        return evenementMapper.toResponseList(
                evenementRepository.findByOrganisationIdAndActifTrue(organisationId));
    }

    @Transactional(readOnly = true)
    public Page<EvenementHotelResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return evenementRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(evenementMapper::toResponse);
    }

    public void delete(String uuid, Long organisationId, String username) {
        EvenementHotelModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setStatut("ANNULE");
        entity.setModifiePar(username);
        evenementRepository.save(entity);
    }

    private EvenementHotelModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        EvenementHotelModel entity = evenementRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Evenement", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Evenement", "uuid", uuid);
        }
        return entity;
    }
}
