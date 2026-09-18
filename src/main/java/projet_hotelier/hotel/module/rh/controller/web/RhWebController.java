package projet_hotelier.hotel.module.rh.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.response.employe.EmployeResponse;
import projet_hotelier.hotel.module.rh.dto.response.conge.CongeResponse;
import projet_hotelier.hotel.module.rh.dto.response.formation.FormationResponse;
import projet_hotelier.hotel.module.rh.dto.response.paie.FichePaieResponse;
import projet_hotelier.hotel.module.rh.pattern.facade.RhFacade;
import projet_hotelier.hotel.module.rh.service.employe.EmployeService;
import projet_hotelier.hotel.module.rh.service.conge.CongeService;
import projet_hotelier.hotel.module.rh.service.formation.FormationService;
import projet_hotelier.hotel.module.rh.service.paie.FichePaieService;

import java.util.List;

/**
 * Controleur web Thymeleaf pour le module RH.
 */
@Slf4j
@Controller
@RequestMapping("/rh")
@RequiredArgsConstructor
public class RhWebController {

    private final EmployeService employeService;
    private final CongeService congeService;
    private final FormationService formationService;
    private final FichePaieService fichePaieService;
    private final RhFacade rhFacade;

    // Valeurs par defaut pour la demonstration
    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long DEFAULT_HOTEL_ID = 1L;
    private static final String DEFAULT_USERNAME = "admin";

    @GetMapping
    public String index(Model model) {
        log.info("Acces a la page d'accueil RH");
        return "rh/index";
    }

    @GetMapping("/employes")
    public String listEmployes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            Model model) {
        log.info("Liste des employes - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<EmployeResponse> employes = employeService.getAllPaginated(DEFAULT_ORG_ID, pageable);

        model.addAttribute("employes", employes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employes.getTotalPages());
        model.addAttribute("totalItems", employes.getTotalElements());
        model.addAttribute("sortBy", sortBy);

        return "rh/employes/list";
    }

    @GetMapping("/employes/{uuid}")
    public String viewEmploye(@PathVariable String uuid, Model model) {
        log.info("Vue detaillee de l'employe: {}", uuid);

        EmployeResponse employe = employeService.getByUuid(uuid, DEFAULT_ORG_ID);
        List<CongeResponse> conges = congeService.getByEmploye(employe.getId());
        List<FormationResponse> formations = formationService.getByEmploye(employe.getId());
        List<FichePaieResponse> fichesPaie = fichePaieService.getByEmploye(employe.getId());

        model.addAttribute("employe", employe);
        model.addAttribute("conges", conges);
        model.addAttribute("formations", formations);
        model.addAttribute("fichesPaie", fichesPaie);

        return "rh/employes/view";
    }

    @GetMapping("/employes/new")
    public String newEmployeForm(Model model) {
        log.info("Formulaire de creation d'employe");
        return "rh/employes/form";
    }

    @GetMapping("/conges")
    public String listConges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        log.info("Liste des conges - page: {}", page);

        Pageable pageable = PageRequest.of(page, size);
        Page<CongeResponse> conges = congeService.getAllPaginated(DEFAULT_ORG_ID, pageable);

        model.addAttribute("conges", conges);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", conges.getTotalPages());

        return "rh/conges/list";
    }

    @GetMapping("/formations")
    public String listFormations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        log.info("Liste des formations - page: {}", page);

        Pageable pageable = PageRequest.of(page, size);
        Page<FormationResponse> formations = formationService.getAllPaginated(DEFAULT_ORG_ID, pageable);

        model.addAttribute("formations", formations);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", formations.getTotalPages());

        return "rh/formations/list";
    }

    @GetMapping("/fiches-paie")
    public String listFichesPaie(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        log.info("Liste des fiches de paie - page: {}", page);

        Pageable pageable = PageRequest.of(page, size);
        Page<FichePaieResponse> fichesPaie = fichePaieService.getAllPaginated(DEFAULT_ORG_ID, pageable);

        model.addAttribute("fichesPaie", fichesPaie);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", fichesPaie.getTotalPages());

        return "rh/fiches-paie/list";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        log.info("Tableau de bord RH");

        RhFacade.RhOrganisationView view = rhFacade.getOrganisationView(
                DEFAULT_ORG_ID,
                PageRequest.of(0, 5)
        );

        model.addAttribute("view", view);
        return "rh/dashboard";
    }
}
