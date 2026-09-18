package projet_hotelier.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projet_hotelier.hotel.module.clientele.model.avis.AvisClientModel;

public interface AvisRepository extends JpaRepository<AvisClientModel, Long> {

}
