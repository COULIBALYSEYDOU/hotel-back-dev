package projet_hotelier.hotel.module.clientele.service.client;

import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientPreferenceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientPreferenceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientPreferenceResponse;

public interface ClientPreferenceService {
    ClientPreferenceResponse create(String tenantId, CreateClientPreferenceRequest request);
    ClientPreferenceResponse update(String tenantId, Long id, UpdateClientPreferenceRequest request);
    ClientPreferenceResponse findById(String tenantId, Long id);
    ClientPreferenceResponse findByClientId(String tenantId, Long clientId);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, Long clientId);
}
