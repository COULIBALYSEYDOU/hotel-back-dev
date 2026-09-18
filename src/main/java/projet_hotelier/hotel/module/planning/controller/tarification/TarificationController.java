package projet_hotelier.hotel.module.planning.controller.tarification;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.planning.dto.request.tarification.CreateTarificationRequest;
import projet_hotelier.hotel.module.planning.dto.request.tarification.UpdateTarificationRequest;
import projet_hotelier.hotel.module.planning.dto.response.tarification.TarificationResponse;
import projet_hotelier.hotel.module.planning.service.tarification.TarificationService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/planning/tarifications")
@RequiredArgsConstructor
public class TarificationController {

    private final TarificationService tarificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<TarificationResponse>> create(
            @Valid @RequestBody CreateTarificationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        TarificationResponse r = tarificationService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<TarificationResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateTarificationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(tarificationService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<TarificationResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(tarificationService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<TarificationResponse>> getByCode(
            @PathVariable String code,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(tarificationService.getByCode(code, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TarificationResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(tarificationService.getAll(organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<TarificationResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(tarificationService.getAllPaginated(organisationId, pageable)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        tarificationService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
