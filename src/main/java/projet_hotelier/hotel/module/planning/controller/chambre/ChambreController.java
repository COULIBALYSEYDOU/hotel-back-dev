package projet_hotelier.hotel.module.planning.controller.chambre;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.planning.dto.request.chambre.CreateChambreRequest;
import projet_hotelier.hotel.module.planning.dto.request.chambre.UpdateChambreRequest;
import projet_hotelier.hotel.module.planning.dto.response.chambre.ChambreResponse;
import projet_hotelier.hotel.module.planning.service.chambre.ChambreService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/planning/chambres")
@RequiredArgsConstructor
public class ChambreController {

    private final ChambreService chambreService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChambreResponse>> create(
            @Valid @RequestBody CreateChambreRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        ChambreResponse response = chambreService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ChambreResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateChambreRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(chambreService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ChambreResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(chambreService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<ApiResponse<ChambreResponse>> getByNumero(
            @PathVariable String numero,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(chambreService.getByNumero(numero, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChambreResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(chambreService.getAll(organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<ChambreResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(chambreService.getAllPaginated(organisationId, pageable)));
    }

    @PatchMapping("/{uuid}/hors-service")
    public ResponseEntity<ApiResponse<ChambreResponse>> setHorsService(
            @PathVariable String uuid,
            @RequestParam boolean horsService,
            @RequestParam(required = false) String motif,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(
                chambreService.setHorsService(uuid, horsService, motif, organisationId, username)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        chambreService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
