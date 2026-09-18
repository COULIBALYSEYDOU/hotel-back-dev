package projet_hotelier.hotel.module.clientele.service.compliance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreateConformiteGDPRRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdateConformiteGDPRRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.ConformiteGDPRResponse;

import java.util.List;

public interface ConformiteGDPRService {

    ConformiteGDPRResponse create(String tenantId, CreateConformiteGDPRRequest request);

    ConformiteGDPRResponse update(String tenantId, Long id, UpdateConformiteGDPRRequest request);

    ConformiteGDPRResponse findById(String tenantId, Long id);

    ConformiteGDPRResponse findByTenantIdAndHotelId(String tenantId, String hotelId);

    Page<ConformiteGDPRResponse> findAll(String tenantId, Pageable pageable);

    List<ConformiteGDPRResponse> findConformes(String tenantId);

    List<ConformiteGDPRResponse> findAuditsProchains(String tenantId);

    void delete(String tenantId, Long id);

    boolean exists(String tenantId, String hotelId);
}
