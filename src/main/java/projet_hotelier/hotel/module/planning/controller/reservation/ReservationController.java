package projet_hotelier.hotel.module.planning.controller.reservation;

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
import projet_hotelier.hotel.module.planning.dto.request.reservation.CreateReservationRequest;
import projet_hotelier.hotel.module.planning.dto.request.reservation.UpdateReservationRequest;
import projet_hotelier.hotel.module.planning.dto.response.reservation.ReservationResponse;
import projet_hotelier.hotel.module.planning.service.reservation.ReservationService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/planning/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> create(
            @Valid @RequestBody CreateReservationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        ReservationResponse response = reservationService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ReservationResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateReservationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(reservationService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getByCode(
            @PathVariable String code,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getByCode(code, organisationId)));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getByClient(
            @PathVariable Long clientId,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getByClient(clientId, organisationId)));
    }

    @GetMapping("/periode")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getByPeriode(organisationId, dateDebut, dateFin)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<ReservationResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getAllPaginated(organisationId, pageable)));
    }

    @GetMapping("/disponibilite")
    public ResponseEntity<ApiResponse<Map<String, Object>>> verifierDisponibilite(
            @RequestParam Long chambreId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        boolean disponible = reservationService.isChambreDisponible(organisationId, chambreId, dateDebut, dateFin);
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "chambreId", chambreId,
                "dateDebut", dateDebut,
                "dateFin", dateFin,
                "disponible", disponible
        )));
    }

    @PatchMapping("/{uuid}/statut")
    public ResponseEntity<ApiResponse<ReservationResponse>> changerStatut(
            @PathVariable String uuid,
            @RequestParam String statut,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(
                reservationService.changerStatut(uuid, statut, organisationId, username)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        reservationService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
