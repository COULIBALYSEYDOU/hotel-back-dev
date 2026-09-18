package projet_hotelier.hotel.module.clientele.service.i18n;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.i18n.CreateTraductionRequest;
import projet_hotelier.hotel.module.clientele.dto.request.i18n.UpdateTraductionRequest;
import projet_hotelier.hotel.module.clientele.dto.response.i18n.TraductionResponse;

import java.util.List;

public interface TraductionService {
    TraductionResponse create(String tenantId, CreateTraductionRequest request);
    TraductionResponse update(String tenantId, Long id, UpdateTraductionRequest request);
    TraductionResponse findById(String tenantId, Long id);
    TraductionResponse findByCleAndLangue(String tenantId, String cleTraduction, String langue);
    Page<TraductionResponse> findAll(String tenantId, Pageable pageable);
    List<TraductionResponse> findByLangue(String tenantId, String langue);
    List<TraductionResponse> findTraductionsParPrefixe(String tenantId, String langue, String prefixe);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, String cleTraduction, String langue);
}
