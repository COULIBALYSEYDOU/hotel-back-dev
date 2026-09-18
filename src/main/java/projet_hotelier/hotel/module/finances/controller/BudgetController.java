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
import projet_hotelier.hotel.module.finances.dto.request.budget.CreateBudgetRequest;
import projet_hotelier.hotel.module.finances.dto.request.budget.UpdateBudgetRequest;
import projet_hotelier.hotel.module.finances.dto.response.budget.BudgetResponse;
import projet_hotelier.hotel.module.finances.service.budget.BudgetService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/finances/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<ApiResponse<BudgetResponse>> create(
            @Valid @RequestBody CreateBudgetRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        BudgetResponse r = budgetService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<BudgetResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateBudgetRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(budgetService.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<BudgetResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.getByUuid(uuid, organisationId)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<BudgetResponse>> getByCode(
            @PathVariable String code,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.getByCode(code, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.getAll(organisationId)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<BudgetResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.getAllPaginated(organisationId, pageable)));
    }

    @GetMapping("/actifs")
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getActifs(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.getActifs(organisationId)));
    }

    @GetMapping("/pending-approval")
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getPendingApproval(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.getPendingApproval(organisationId)));
    }

    @PatchMapping("/{uuid}/soumettre")
    public ResponseEntity<ApiResponse<BudgetResponse>> soumettre(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.soumettre(uuid, organisationId, username)));
    }

    @PatchMapping("/{uuid}/approuver")
    public ResponseEntity<ApiResponse<BudgetResponse>> approuver(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.success(budgetService.approuver(uuid, organisationId, username)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        budgetService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
