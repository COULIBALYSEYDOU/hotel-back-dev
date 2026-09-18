package projet_hotelier.hotel.module.finances.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.finances.dto.request.depense.CreateDepenseRequest;
import projet_hotelier.hotel.module.finances.dto.response.depense.DepenseResponse;
import projet_hotelier.hotel.module.finances.service.depense.DepenseService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/finances/depenses")
@RequiredArgsConstructor
public class DepenseController {

    private final DepenseService depenseService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepenseResponse>> create(
            @Valid @RequestBody CreateDepenseRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        DepenseResponse r = depenseService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<DepenseResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(depenseService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<DepenseResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(depenseService.getAllPaginated(organisationId, pageable)));
    }

    @GetMapping("/periode")
    public ResponseEntity<ApiResponse<List<DepenseResponse>>> getByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(depenseService.getByPeriode(organisationId, dateDebut, dateFin)));
    }

    @GetMapping("/pending-validation")
    public ResponseEntity<ApiResponse<List<DepenseResponse>>> getPendingValidation(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(depenseService.getPendingValidation(organisationId)));
    }

    @PatchMapping("/{uuid}/valider")
    public ResponseEntity<ApiResponse<DepenseResponse>> valider(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(depenseService.valider(uuid, organisationId, username)));
    }

    @GetMapping("/totaux")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTotaux(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        BigDecimal total = depenseService.sumByPeriode(organisationId, dateDebut, dateFin);
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "dateDebut", dateDebut, "dateFin", dateFin, "totalTTC", total)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        depenseService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
