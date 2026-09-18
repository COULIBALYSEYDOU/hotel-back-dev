package projet_hotelier.hotel.module.reporting.audit.controller.auditlog;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.reporting.audit.dto.request.CreateAuditLogRequest;
import projet_hotelier.hotel.module.reporting.audit.dto.request.UpdateAuditLogRequest;
import projet_hotelier.hotel.module.reporting.audit.dto.response.AuditLogResponse;
import projet_hotelier.hotel.module.reporting.audit.service.auditlog.AuditLogService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reporting/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AuditLogResponse>> create(
            @Valid @RequestBody CreateAuditLogRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        AuditLogResponse r = service.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<AuditLogResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateAuditLogRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(service.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<AuditLogResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(service.getByUuid(uuid, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getAll(
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
