package projet_hotelier.hotel.module.clientele.service.compliance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreatePolitiqueConfidentialiteRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdatePolitiqueConfidentialiteRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.PolitiqueConfidentialiteResponse;

import java.util.List;

public interface PolitiqueConfidentialiteService {

    PolitiqueConfidentialiteResponse create(String tenantId, CreatePolitiqueConfidentialiteRequest request);

    PolitiqueConfidentialiteResponse update(String tenantId, Long id, UpdatePolitiqueConfidentialiteRequest request);

    PolitiqueConfidentialiteResponse findById(String tenantId, Long id);

    Page<PolitiqueConfidentialiteResponse> findAll(String tenantId, Pageable pageable);

    List<PolitiqueConfidentialiteResponse> findByType(String tenantId, String typePolitique);

    List<PolitiqueConfidentialiteResponse> findByLangue(String tenantId, String langue);

    List<PolitiqueConfidentialiteResponse> findPolitiquesActives(String tenantId);

    void delete(String tenantId, Long id);

    boolean exists(String tenantId, String versionPolitique, String langue);
}
