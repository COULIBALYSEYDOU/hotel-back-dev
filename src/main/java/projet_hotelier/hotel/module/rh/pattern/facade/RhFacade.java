package projet_hotelier.hotel.module.rh.pattern.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.dto.response.employe.EmployeResponse;
import projet_hotelier.hotel.module.rh.dto.response.conge.CongeResponse;
import projet_hotelier.hotel.module.rh.dto.response.formation.FormationResponse;
import projet_hotelier.hotel.module.rh.dto.response.paie.FichePaieResponse;
import projet_hotelier.hotel.module.rh.service.employe.EmployeService;
import projet_hotelier.hotel.module.rh.service.conge.CongeService;
import projet_hotelier.hotel.module.rh.service.formation.FormationService;
import projet_hotelier.hotel.module.rh.service.paie.FichePaieService;

import java.util.List;

/**
 * Facade Pattern pour le module RH.
 * Fournit une interface simplifiee et unifiee pour acceder aux services RH.
 * Cache la complexite des interactions entre les differents services.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RhFacade {

    private final EmployeService employeService;
    private final CongeService congeService;
    private final FormationService formationService;
    private final FichePaieService fichePaieService;

    /**
     * Recupere le tableau de bord RH pour un employe.
     */
    public RhDashboard getDashboard(Long employeId, Long organisationId) {
        log.info("Recuperation du tableau de bord RH pour l'employe {}", employeId);

        EmployeResponse employe = employeService.getById(employeId, organisationId);
        List<CongeResponse> conges = congeService.getByEmploye(employeId);
        List<FormationResponse> formations = formationService.getByEmploye(employeId);
        List<FichePaieResponse> fichesPaie = fichePaieService.getByEmploye(employeId);

        return RhDashboard.builder()
                .employe(employe)
                .conges(conges)
                .formations(formations)
                .fichesPaie(fichesPaie)
                .build();
    }

    /**
     * Recupere toutes les donnees RH d'une organisation avec pagination.
     */
    public RhOrganisationView getOrganisationView(Long organisationId, Pageable pageable) {
        log.info("Recuperation de la vue organisation RH pour l'organisation {}", organisationId);

        Page<EmployeResponse> employes = employeService.getAllPaginated(organisationId, pageable);
        Page<CongeResponse> conges = congeService.getAllPaginated(organisationId, pageable);
        Page<FormationResponse> formations = formationService.getAllPaginated(organisationId, pageable);
        Page<FichePaieResponse> fichesPaie = fichePaieService.getAllPaginated(organisationId, pageable);

        return RhOrganisationView.builder()
                .employes(employes)
                .conges(conges)
                .formations(formations)
                .fichesPaie(fichesPaie)
                .build();
    }

    /**
     * DTO pour le tableau de bord RH d'un employe.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class RhDashboard {
        private EmployeResponse employe;
        private List<CongeResponse> conges;
        private List<FormationResponse> formations;
        private List<FichePaieResponse> fichesPaie;
    }

    /**
     * DTO pour la vue organisation RH.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class RhOrganisationView {
        private Page<EmployeResponse> employes;
        private Page<CongeResponse> conges;
        private Page<FormationResponse> formations;
        private Page<FichePaieResponse> fichesPaie;
    }
}
