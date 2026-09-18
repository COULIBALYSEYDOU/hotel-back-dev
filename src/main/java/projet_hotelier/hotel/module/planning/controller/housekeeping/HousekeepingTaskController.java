package projet_hotelier.hotel.module.planning.controller.housekeeping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.planning.dto.request.housekeeping.CreateHousekeepingTaskRequest;
import projet_hotelier.hotel.module.planning.dto.request.housekeeping.UpdateHousekeepingTaskRequest;
import projet_hotelier.hotel.module.planning.dto.response.housekeeping.HousekeepingTaskResponse;
import projet_hotelier.hotel.module.planning.service.housekeeping.HousekeepingTaskService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/planning/housekeeping")
@RequiredArgsConstructor
public class HousekeepingTaskController {

    private final HousekeepingTaskService housekeepingService;

    @PostMapping
    public ResponseEntity<ApiResponse<HousekeepingTaskResponse>> create(
            @Valid @RequestBody CreateHousekeepingTaskRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        HousekeepingTaskResponse r = housekeepingService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<HousekeepingTaskResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateHousekeepingTaskRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(housekeepingService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<HousekeepingTaskResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(housekeepingService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/chambre/{chambreId}")
    public ResponseEntity<ApiResponse<List<HousekeepingTaskResponse>>> getByChambre(
            @PathVariable Long chambreId,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(housekeepingService.getByChambre(chambreId, organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<HousekeepingTaskResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(housekeepingService.getAllPaginated(organisationId, pageable)));
    }

    @PatchMapping("/{uuid}/executee")
    public ResponseEntity<ApiResponse<HousekeepingTaskResponse>> marquerExecutee(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(housekeepingService.marquerExecutee(uuid, organisationId, username)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        housekeepingService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
