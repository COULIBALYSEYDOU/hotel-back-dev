package projet_hotelier.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projet_hotelier.hotel.core.organisation.Abonnement;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

}
