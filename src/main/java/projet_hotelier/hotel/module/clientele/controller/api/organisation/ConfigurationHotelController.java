package projet_hotelier.hotel.module.clientele.controller.api.organisation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.organisation.CreateConfigurationHotelRequest;
import projet_hotelier.hotel.module.clientele.dto.request.organisation.UpdateConfigurationHotelRequest;
import projet_hotelier.hotel.module.clientele.dto.response.organisation.ConfigurationHotelResponse;
import projet_hotelier.hotel.module.clientele.service.organisation.ConfigurationHotelService;

import java.util.List;

@RestController
@RequestMapping("/api/clientele/organisation/configurations-hotel")
@RequiredArgsConstructor
public class ConfigurationHotelController {

    private final ConfigurationHotelService service;

    @PostMapping
    public ResponseEntity<ConfigurationHotelResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateConfigurationHotelRequest request) {
        ConfigurationHotelResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationHotelResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateConfigurationHotelRequest request) {
        ConfigurationHotelResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationHotelResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        ConfigurationHotelResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<ConfigurationHotelResponse> findByHotelId(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String hotelId) {
        ConfigurationHotelResponse response = service.findByTenantIdAndHotelId(tenantId, hotelId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConfigurationHotelResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<ConfigurationHotelResponse> responses = service.findAll(tenantId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/hotel/{hotelId}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String hotelId) {
        boolean exists = service.exists(tenantId, hotelId);
        return ResponseEntity.ok(exists);
    }
}
