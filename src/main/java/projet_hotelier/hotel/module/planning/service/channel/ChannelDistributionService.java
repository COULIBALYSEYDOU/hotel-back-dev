package projet_hotelier.hotel.module.planning.service.channel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.planning.dto.request.channel.CreateChannelDistributionRequest;
import projet_hotelier.hotel.module.planning.dto.request.channel.UpdateChannelDistributionRequest;
import projet_hotelier.hotel.module.planning.dto.response.channel.ChannelDistributionResponse;
import projet_hotelier.hotel.module.planning.mapper.ChannelDistributionMapper;
import projet_hotelier.hotel.module.planning.model.channel.ChannelDistributionModel;
import projet_hotelier.hotel.module.planning.repository.ChannelDistributionRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service pour la gestion des canaux de distribution (Booking.com, Airbnb, etc.).
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChannelDistributionService {

    private final ChannelDistributionRepository channelRepository;
    private final ChannelDistributionMapper channelMapper;

    public ChannelDistributionResponse create(CreateChannelDistributionRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation canal de distribution canal={}", request.getCanal());
        ChannelDistributionModel entity = channelMapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        return channelMapper.toResponse(channelRepository.save(entity));
    }

    public ChannelDistributionResponse update(String uuid, UpdateChannelDistributionRequest request, Long organisationId, String username) {
        ChannelDistributionModel entity = findByUuidAndOrganisation(uuid, organisationId);
        channelMapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return channelMapper.toResponse(channelRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public ChannelDistributionResponse getByUuid(String uuid, Long organisationId) {
        return channelMapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<ChannelDistributionResponse> getAll(Long organisationId) {
        return channelMapper.toResponseList(
                channelRepository.findByOrganisationIdAndActifTrue(organisationId));
    }

    @Transactional(readOnly = true)
    public Page<ChannelDistributionResponse> getAllPaginated(Long organisationId, Pageable pageable) {
        return channelRepository.findByOrganisationIdAndActifTrue(organisationId, pageable)
                .map(channelMapper::toResponse);
    }

    public ChannelDistributionResponse marquerSynchronise(String uuid, String statutSync, Long organisationId, String username) {
        ChannelDistributionModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setDerniereSync(LocalDateTime.now());
        entity.setStatutSync(statutSync != null ? statutSync : "OK");
        entity.setModifiePar(username);
        return channelMapper.toResponse(channelRepository.save(entity));
    }

    public void delete(String uuid, Long organisationId, String username) {
        ChannelDistributionModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        channelRepository.save(entity);
    }

    private ChannelDistributionModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        ChannelDistributionModel entity = channelRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("ChannelDistribution", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("ChannelDistribution", "uuid", uuid);
        }
        return entity;
    }
}
