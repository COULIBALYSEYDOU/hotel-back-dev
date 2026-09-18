package projet_hotelier.hotel.module.clientele.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.fidelite.ProgrammeFideliteModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgrammeFideliteRepository extends JpaRepository<ProgrammeFideliteModel, Long> {

    Optional<ProgrammeFideliteModel> findByUuid(String uuid);

    Optional<ProgrammeFideliteModel> findByCodeProgramme(String codeProgramme);

    List<ProgrammeFideliteModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<ProgrammeFideliteModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    boolean existsByCodeProgramme(String codeProgramme);
}
