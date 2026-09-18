package projet_hotelier.hotel.module.clientele.service.organisation;

import projet_hotelier.hotel.module.clientele.dto.request.organisation.CreateConfigurationHotelRequest;
import projet_hotelier.hotel.module.clientele.dto.request.organisation.UpdateConfigurationHotelRequest;
import projet_hotelier.hotel.module.clientele.dto.response.organisation.ConfigurationHotelResponse;

import java.util.List;

public interface ConfigurationHotelService {
    ConfigurationHotelResponse create(String tenantId, CreateConfigurationHotelRequest request);
    ConfigurationHotelResponse update(String tenantId, Long id, UpdateConfigurationHotelRequest request);
    ConfigurationHotelResponse findById(String tenantId, Long id);
    ConfigurationHotelResponse findByTenantIdAndHotelId(String tenantId, String hotelId);
    List<ConfigurationHotelResponse> findAll(String tenantId);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, String hotelId);
}
