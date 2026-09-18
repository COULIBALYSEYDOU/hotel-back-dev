package projet_hotelier.hotel.module.clientele.service.reporting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateTableauBordRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateTableauBordRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.TableauBordResponse;

import java.util.List;

public interface TableauBordService {
    TableauBordResponse create(String tenantId, CreateTableauBordRequest request);
    TableauBordResponse update(String tenantId, Long id, UpdateTableauBordRequest request);
    TableauBordResponse findById(String tenantId, Long id);
    Page<TableauBordResponse> findAll(String tenantId, Pageable pageable);
    List<TableauBordResponse> findTableauxParDefaut(String tenantId);
    List<TableauBordResponse> findTableauxParRole(String tenantId, String roleCible);
    List<TableauBordResponse> findTableauxParUtilisateur(String tenantId, Long utilisateurId);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, String nom);
}
