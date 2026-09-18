package projet_hotelier.hotel.module.reporting.document.controller.document;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.reporting.document.dto.request.CreateDocumentRequest;
import projet_hotelier.hotel.module.reporting.document.dto.request.UpdateDocumentRequest;
import projet_hotelier.hotel.module.reporting.document.dto.response.DocumentResponse;
import projet_hotelier.hotel.module.reporting.document.service.document.DocumentService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reporting/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<DocumentResponse>> create(
            @Valid @RequestBody CreateDocumentRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        DocumentResponse r = service.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(r));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<DocumentResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateDocumentRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(ApiResponse.updated(service.update(uuid, request, organisationId, username)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<DocumentResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(service.getByUuid(uuid, organisationId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        return ResponseEntity.ok(ApiResponse.success(service.getAll(organisationId)));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        service.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
