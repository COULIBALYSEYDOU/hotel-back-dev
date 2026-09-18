package projet_hotelier.hotel.module.clientele.service.client;

import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientProfilRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientProfilRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientProfilResponse;

public interface ClientProfilService {
    ClientProfilResponse create(String tenantId, CreateClientProfilRequest request);
    ClientProfilResponse update(String tenantId, Long id, UpdateClientProfilRequest request);
    ClientProfilResponse findById(String tenantId, Long id);
    ClientProfilResponse findByClientId(String tenantId, Long clientId);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, Long clientId);
}
