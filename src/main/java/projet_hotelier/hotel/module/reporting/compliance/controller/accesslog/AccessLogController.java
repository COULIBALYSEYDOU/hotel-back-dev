package projet_hotelier.hotel.module.reporting.compliance.controller.accesslog;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.CreateAccessLogRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.UpdateAccessLogRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.response.AccessLogResponse;
import projet_hotelier.hotel.module.reporting.compliance.service.accesslog.AccessLogService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reporting/access-logs")
@RequiredArgsConstructor
public class AccessLogController {

    private final AccessLogService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AccessLogResponse>> create(
            @Valid @RequestBody CreateAccessLogRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        AccessLogResponse r = service.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<AccessLogResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateAccessLogRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(service.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<AccessLogResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(service.getByUuid(uuid, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccessLogResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(service.getAll(organisationId)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        service.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
