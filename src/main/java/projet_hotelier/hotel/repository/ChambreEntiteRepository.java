package projet_hotelier.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.chambre.ChambreModel;

/**
 * Repository pour l'entité Chambre (ancien système).
 * Note: Le nouveau système utilise projet_hotelier.hotel.module.planning.repository.ChambreRepository
 * 
 * @deprecated Utiliser ChambreRepository du module planning à la place
 */
@Repository("chambreEntiteRepository")
@Deprecated
public interface ChambreEntiteRepository extends JpaRepository<ChambreModel, Long> {

}
