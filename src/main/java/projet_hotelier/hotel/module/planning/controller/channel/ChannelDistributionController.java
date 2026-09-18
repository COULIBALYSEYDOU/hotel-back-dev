package projet_hotelier.hotel.module.planning.controller.channel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.planning.dto.request.channel.CreateChannelDistributionRequest;
import projet_hotelier.hotel.module.planning.dto.request.channel.UpdateChannelDistributionRequest;
import projet_hotelier.hotel.module.planning.dto.response.channel.ChannelDistributionResponse;
import projet_hotelier.hotel.module.planning.service.channel.ChannelDistributionService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/planning/channels")
@RequiredArgsConstructor
public class ChannelDistributionController {

    private final ChannelDistributionService channelService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChannelDistributionResponse>> create(
            @Valid @RequestBody CreateChannelDistributionRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        ChannelDistributionResponse r = channelService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ChannelDistributionResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateChannelDistributionRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(channelService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ChannelDistributionResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(channelService.getByUuid(uuid, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChannelDistributionResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(channelService.getAll(organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<ChannelDistributionResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(channelService.getAllPaginated(organisationId, pageable)));
    }

    @PatchMapping("/{uuid}/sync")
    public ResponseEntity<ApiResponse<ChannelDistributionResponse>> marquerSynchronise(
            @PathVariable String uuid,
            @RequestParam(required = false) String statut,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(channelService.marquerSynchronise(uuid, statut, organisationId, username)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        channelService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
