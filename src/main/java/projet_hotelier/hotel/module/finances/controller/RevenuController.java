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
import projet_hotelier.hotel.module.finances.dto.request.revenu.CreateRevenuRequest;
import projet_hotelier.hotel.module.finances.dto.response.revenu.RevenuResponse;
import projet_hotelier.hotel.module.finances.service.revenu.RevenuService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/finances/revenus")
@RequiredArgsConstructor
public class RevenuController {

    private final RevenuService revenuService;

    @PostMapping
    public ResponseEntity<ApiResponse<RevenuResponse>> create(
            @Valid @RequestBody CreateRevenuRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        RevenuResponse r = revenuService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<RevenuResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(revenuService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<RevenuResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(revenuService.getAllPaginated(organisationId, pageable)));
    }

    @GetMapping("/periode")
    public ResponseEntity<ApiResponse<List<RevenuResponse>>> getByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(revenuService.getByPeriode(organisationId, dateDebut, dateFin)));
    }

    @GetMapping("/totaux")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTotaux(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        BigDecimal total = revenuService.sumByPeriode(organisationId, dateDebut, dateFin);
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "dateDebut", dateDebut, "dateFin", dateFin, "totalTTC", total)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        revenuService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
