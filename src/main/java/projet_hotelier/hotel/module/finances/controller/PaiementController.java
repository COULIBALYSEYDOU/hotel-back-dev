package projet_hotelier.hotel.module.finances.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.finances.dto.request.paiement.CreatePaiementRequest;
import projet_hotelier.hotel.module.finances.dto.response.paiement.PaiementResponse;
import projet_hotelier.hotel.module.finances.service.paiement.PaiementService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/finances/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaiementResponse>> create(
            @Valid @RequestBody CreatePaiementRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        PaiementResponse r = paiementService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<PaiementResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(paiementService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/facture/{factureId}")
    public ResponseEntity<ApiResponse<List<PaiementResponse>>> getByFacture(
            @PathVariable Long factureId,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(paiementService.getByFacture(factureId, organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<PaiementResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(paiementService.getAllPaginated(organisationId, pageable)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        paiementService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
