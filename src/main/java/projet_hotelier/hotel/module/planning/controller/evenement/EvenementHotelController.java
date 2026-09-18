package projet_hotelier.hotel.module.planning.controller.evenement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.planning.dto.request.evenement.CreateEvenementHotelRequest;
import projet_hotelier.hotel.module.planning.dto.request.evenement.UpdateEvenementHotelRequest;
import projet_hotelier.hotel.module.planning.dto.response.evenement.EvenementHotelResponse;
import projet_hotelier.hotel.module.planning.service.evenement.EvenementHotelService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/planning/evenements")
@RequiredArgsConstructor
public class EvenementHotelController {

    private final EvenementHotelService evenementService;

    @PostMapping
    public ResponseEntity<ApiResponse<EvenementHotelResponse>> create(
            @Valid @RequestBody CreateEvenementHotelRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        EvenementHotelResponse r = evenementService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EvenementHotelResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateEvenementHotelRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(evenementService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EvenementHotelResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(evenementService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<EvenementHotelResponse>> getByCode(
            @PathVariable String code,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(evenementService.getByCode(code, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EvenementHotelResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(evenementService.getAll(organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<EvenementHotelResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(evenementService.getAllPaginated(organisationId, pageable)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        evenementService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
