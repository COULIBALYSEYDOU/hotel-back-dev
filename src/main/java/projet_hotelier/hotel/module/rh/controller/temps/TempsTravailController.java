package projet_hotelier.hotel.module.rh.controller.temps;

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
import projet_hotelier.hotel.module.rh.dto.request.temps.CreateTempsTravailRequest;
import projet_hotelier.hotel.module.rh.dto.request.temps.UpdateTempsTravailRequest;
import projet_hotelier.hotel.module.rh.dto.response.temps.TempsTravailResponse;
import projet_hotelier.hotel.module.rh.service.temps.TempsTravailService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controleur REST pour la gestion des temps de travail.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/temps-travail")
@RequiredArgsConstructor
public class TempsTravailController {

    private final TempsTravailService tempsTravailService;

    @PostMapping
    public ResponseEntity<ApiResponse<TempsTravailResponse>> create(
            @Valid @RequestBody CreateTempsTravailRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'un temps de travail pour l'organisation {}", organisationId);
        TempsTravailResponse response = tempsTravailService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<TempsTravailResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateTempsTravailRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour du temps de travail avec uuid: {}", uuid);
        TempsTravailResponse response = tempsTravailService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<TempsTravailResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du temps de travail avec uuid: {}", uuid);
        TempsTravailResponse response = tempsTravailService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<TempsTravailResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du temps de travail avec id: {}", id);
        TempsTravailResponse response = tempsTravailService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TempsTravailResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de tous les temps de travail");
        List<TempsTravailResponse> responses = tempsTravailService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<TempsTravailResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des temps de travail");
        Page<TempsTravailResponse> responses = tempsTravailService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<TempsTravailResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Recuperation des temps de travail de l'employe: {}", employeId);
        List<TempsTravailResponse> responses = tempsTravailService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<TempsTravailResponse>>> getByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation des temps de travail pour la date: {}", date);
        List<TempsTravailResponse> responses = tempsTravailService.getByDate(organisationId, date);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/{uuid}/validate")
    public ResponseEntity<ApiResponse<TempsTravailResponse>> validate(
            @PathVariable String uuid,
            @RequestBody Map<String, Long> body,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Validation du temps de travail avec uuid: {}", uuid);
        Long validateurId = body.get("validateurId");
        TempsTravailResponse response = tempsTravailService.validate(uuid, validateurId, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Temps de travail valide avec succes"));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression du temps de travail avec uuid: {}", uuid);
        tempsTravailService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
