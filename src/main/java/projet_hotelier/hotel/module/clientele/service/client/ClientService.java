package projet_hotelier.hotel.module.clientele.service.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientResponse;

import java.util.List;

public interface ClientService {
    ClientResponse create(String tenantId, CreateClientRequest request);
    ClientResponse update(String tenantId, Long id, UpdateClientRequest request);
    ClientResponse findById(String tenantId, Long id);
    ClientResponse findByEmail(String tenantId, String email);
    Page<ClientResponse> findAll(String tenantId, Pageable pageable);
    List<ClientResponse> findBySegment(String tenantId, String segment);
    List<ClientResponse> findByStatut(String tenantId, String statut);
    List<ClientResponse> findByRisqueChurn(String tenantId, String risqueChurn);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, String email);
}
