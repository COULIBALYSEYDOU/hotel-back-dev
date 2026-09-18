package projet_hotelier.hotel.module.rh.controller.employe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.employe.CreateEmployeRequest;
import projet_hotelier.hotel.module.rh.dto.request.employe.UpdateEmployeRequest;
import projet_hotelier.hotel.module.rh.dto.response.employe.EmployeResponse;
import projet_hotelier.hotel.module.rh.service.employe.EmployeService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

/**
 * Controleur REST pour la gestion des employes.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeResponse>> create(
            @Valid @RequestBody CreateEmployeRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'un employe pour l'organisation {}", organisationId);
        EmployeResponse response = employeService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EmployeResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateEmployeRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour de l'employe avec uuid: {}", uuid);
        EmployeResponse response = employeService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EmployeResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de l'employe avec uuid: {}", uuid);
        EmployeResponse response = employeService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<EmployeResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de l'employe avec id: {}", id);
        EmployeResponse response = employeService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId) {
        log.debug("Recuperation de tous les employes");
        List<EmployeResponse> responses = hotelId != null
                ? employeService.getAllByHotel(organisationId, hotelId)
                : employeService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<EmployeResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des employes");
        Page<EmployeResponse> responses = hotelId != null
                ? employeService.getAllByHotelPaginated(organisationId, hotelId, pageable)
                : employeService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/departement/{departement}")
    public ResponseEntity<ApiResponse<List<EmployeResponse>>> getByDepartement(
            @PathVariable String departement,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation des employes du departement: {}", departement);
        List<EmployeResponse> responses = employeService.getByDepartement(organisationId, departement);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression de l'employe avec uuid: {}", uuid);
        employeService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }

    @PatchMapping("/{uuid}/activate")
    public ResponseEntity<ApiResponse<EmployeResponse>> activate(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Activation de l'employe avec uuid: {}", uuid);
        EmployeResponse response = employeService.activate(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Employe active avec succes"));
    }

    @PatchMapping("/{uuid}/deactivate")
    public ResponseEntity<ApiResponse<EmployeResponse>> deactivate(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Desactivation de l'employe avec uuid: {}", uuid);
        EmployeResponse response = employeService.deactivate(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Employe desactive avec succes"));
    }
}
