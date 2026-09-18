package projet_hotelier.hotel.module.reporting.ia.service.iarecommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.ia.dto.request.CreateIaRecommendationRequest;
import projet_hotelier.hotel.module.reporting.ia.dto.request.UpdateIaRecommendationRequest;
import projet_hotelier.hotel.module.reporting.ia.dto.response.IaRecommendationResponse;
import projet_hotelier.hotel.module.reporting.ia.mapper.IaRecommendationMapper;
import projet_hotelier.hotel.module.reporting.ia.model.IaRecommendationModel;
import projet_hotelier.hotel.module.reporting.ia.repository.IaRecommendationRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class IaRecommendationService {

    private final IaRecommendationRepository repository;
    private final IaRecommendationMapper mapper;

    public IaRecommendationResponse create(CreateIaRecommendationRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation IaRecommendation pour organisation={}", organisationId);
        IaRecommendationModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodeRecommendation() != null && repository.existsByCodeRecommendation(entity.getCodeRecommendation())) {
            throw ConflictException.duplicate("IaRecommendation", "codeRecommendation", entity.getCodeRecommendation());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public IaRecommendationResponse update(String uuid, UpdateIaRecommendationRequest request, Long organisationId, String username) {
        IaRecommendationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public IaRecommendationResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<IaRecommendationResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        IaRecommendationModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private IaRecommendationModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        IaRecommendationModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("IaRecommendation", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("IaRecommendation", "uuid", uuid);
        }
        return entity;
    }
}
