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
import projet_hotelier.hotel.module.finances.dto.request.facture.CreateFactureRequest;
import projet_hotelier.hotel.module.finances.dto.response.facture.FactureResponse;
import projet_hotelier.hotel.module.finances.service.facture.FactureService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/finances/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @PostMapping
    public ResponseEntity<ApiResponse<FactureResponse>> create(
            @Valid @RequestBody CreateFactureRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        FactureResponse r = factureService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<FactureResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<ApiResponse<FactureResponse>> getByNumero(
            @PathVariable String numero,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getByNumero(numero, organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<FactureResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getAllPaginated(organisationId, pageable)));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> getByClient(
            @PathVariable Long clientId,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getByClient(clientId, organisationId)));
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> getByReservation(
            @PathVariable Long reservationId,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getByReservation(reservationId, organisationId)));
    }

    @GetMapping("/non-payees")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> getNonPayees(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getNonPayees(organisationId)));
    }

    @GetMapping("/echues")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> getEchuesNonPayees(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(factureService.getEchuesNonPayees(organisationId)));
    }

    @PatchMapping("/{uuid}/emettre")
    public ResponseEntity<ApiResponse<FactureResponse>> emettre(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(factureService.emettre(uuid, organisationId, username)));
    }

    @PatchMapping("/{uuid}/paiement")
    public ResponseEntity<ApiResponse<FactureResponse>> appliquerPaiement(
            @PathVariable String uuid,
            @RequestParam BigDecimal montant,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(
                factureService.appliquerPaiement(uuid, montant, organisationId, username)));
    }

    @PatchMapping("/{uuid}/annuler")
    public ResponseEntity<ApiResponse<FactureResponse>> annuler(
            @PathVariable String uuid,
            @RequestParam(required = false) String motif,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(factureService.annuler(uuid, motif, organisationId, username)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        factureService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
