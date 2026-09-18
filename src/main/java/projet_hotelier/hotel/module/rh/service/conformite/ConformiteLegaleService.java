package projet_hotelier.hotel.module.rh.service.conformite;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.rh.model.personnel.EmployeModel;
import projet_hotelier.hotel.module.rh.repository.employe.EmployeRepository;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service enterprise-grade pour la conformité légale multi-pays.
 * Gestion des obligations légales, réglementations, conformité RGPD, normes locales.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ConformiteLegaleService {

    private final EmployeRepository employeRepository;

    /**
     * Vérifie la conformité légale complète d'un employé.
     */
    public Map<String, Object> verifierConformite(String employeUuid, Long organisationId) {
        log.info("Vérification de la conformité légale pour l'employé: {}", employeUuid);

        EmployeModel employe = trouverEmploye(employeUuid, organisationId);

        Map<String, Object> rapport = new HashMap<>();
        List<String> nonConformites = new ArrayList<>();
        List<String> alertes = new ArrayList<>();
        List<String> conformites = new ArrayList<>();

        // Vérification des documents légaux
        verifierDocumentsLegaux(employe, nonConformites, alertes, conformites);

        // Vérification de la conformité RGPD
        verifierConformiteRGPD(employe, nonConformites, alertes, conformites);

        // Vérification des obligations légales par pays
        verifierObligationsParPays(employe, nonConformites, alertes, conformites);

        // Vérification des visas et permis
        verifierVisasPermis(employe, nonConformites, alertes, conformites);

        // Vérification des formations obligatoires
        verifierFormationsObligatoires(employe, nonConformites, alertes, conformites);

        // Vérification de la conformité fiscale
        verifierConformiteFiscale(employe, nonConformites, alertes, conformites);

        // Compilation du rapport
        rapport.put("employeUuid", employeUuid);
        rapport.put("paysCode", employe.getPaysResidenceCode());
        rapport.put("conforme", nonConformites.isEmpty());
        rapport.put("scoreConformite", calculerScoreConformite(conformites.size(), nonConformites.size()));
        rapport.put("nonConformites", nonConformites);
        rapport.put("alertes", alertes);
        rapport.put("conformites", conformites);
        rapport.put("dateVerification", LocalDate.now());

        log.info("Vérification de conformité terminée pour l'employé: {}", employeUuid);
        return rapport;
    }

    /**
     * Vérifie les documents légaux requis.
     */
    private void verifierDocumentsLegaux(EmployeModel employe, List<String> nonConformites, 
                                          List<String> alertes, List<String> conformites) {
        String paysCode = employe.getPaysResidenceCode();

        if (paysCode == null) {
            nonConformites.add("Pays de résidence non défini");
            return;
        }

        // Vérifications communes
        if (employe.getNumeroCarteIdentite() == null && employe.getNumeroPasseport() == null) {
            nonConformites.add("Document d'identité manquant");
        } else {
            conformites.add("Document d'identité présent");
        }

        // Vérifications spécifiques par pays
        switch (paysCode) {
            case "FRA":
                if (employe.getCnpsNumero() == null) {
                    nonConformites.add("Numéro CNPS manquant (obligatoire en France)");
                } else {
                    conformites.add("Numéro CNPS présent");
                }
                if (employe.getNif() == null) {
                    alertes.add("Numéro d'identification fiscale recommandé");
                }
                break;

            case "CMR":
                if (employe.getCnpsNumero() == null) {
                    nonConformites.add("Numéro CNPS manquant (obligatoire au Cameroun)");
                } else {
                    conformites.add("Numéro CNPS présent");
                }
                if (employe.getNif() == null) {
                    nonConformites.add("NIF manquant (obligatoire au Cameroun)");
                } else {
                    conformites.add("NIF présent");
                }
                break;

            // Ajouter d'autres pays selon les besoins
        }
    }

    /**
     * Vérifie la conformité RGPD.
     */
    private void verifierConformiteRGPD(EmployeModel employe, List<String> nonConformites,
                                        List<String> alertes, List<String> conformites) {
        if (employe.getRgpdApplicable() == null || !employe.getRgpdApplicable()) {
            alertes.add("RGPD non marqué comme applicable");
        } else {
            conformites.add("RGPD marqué comme applicable");
        }

        if (employe.getBaseLegale() == null || employe.getBaseLegale().isEmpty()) {
            nonConformites.add("Base légale du traitement RGPD non définie");
        } else {
            conformites.add("Base légale RGPD définie");
        }

        if (employe.getRetention() == null || employe.getRetention().isEmpty()) {
            alertes.add("Durée de rétention des données non définie");
        } else {
            conformites.add("Durée de rétention définie");
        }

        if (employe.getConsentementDonnees() == null || !employe.getConsentementDonnees()) {
            alertes.add("Consentement aux données non enregistré");
        } else {
            conformites.add("Consentement aux données enregistré");
        }
    }

    /**
     * Vérifie les obligations légales spécifiques par pays.
     */
    private void verifierObligationsParPays(EmployeModel employe, List<String> nonConformites,
                                             List<String> alertes, List<String> conformites) {
        String paysCode = employe.getPaysResidenceCode();

        if (paysCode == null) return;

        switch (paysCode) {
            case "FRA":
                // Obligations spécifiques France
                if (employe.getDateEmbauche() != null) {
                    // Vérifier déclaration URSSAF dans les 8 jours
                    LocalDate dateLimite = employe.getDateEmbauche().plusDays(8);
                    if (LocalDate.now().isAfter(dateLimite)) {
                        alertes.add("Déclaration URSSAF doit être effectuée dans les 8 jours");
                    }
                }
                break;

            case "CMR":
                // Obligations spécifiques Cameroun
                if (employe.getDateEmbauche() != null) {
                    // Vérifier déclaration CNPS dans les 30 jours
                    LocalDate dateLimite = employe.getDateEmbauche().plusDays(30);
                    if (LocalDate.now().isAfter(dateLimite)) {
                        alertes.add("Déclaration CNPS doit être effectuée dans les 30 jours");
                    }
                }
                break;

            // Ajouter d'autres pays selon les besoins
        }
    }

    /**
     * Vérifie les visas et permis de travail.
     */
    private void verifierVisasPermis(EmployeModel employe, List<String> nonConformites,
                                      List<String> alertes, List<String> conformites) {
        // Vérifier si l'employé est étranger
        if (employe.getNationaliteCode() != null && 
            !employe.getNationaliteCode().equals(employe.getPaysResidenceCode())) {
            
            // Employé étranger - vérifier les documents
            if (employe.getNumeroPermisTravail() == null) {
                nonConformites.add("Permis de travail manquant pour employé étranger");
            } else {
                conformites.add("Permis de travail présent");
            }

            if (employe.getDateExpirationPermisTravail() != null) {
                LocalDate dateExpiration = employe.getDateExpirationPermisTravail();
                if (dateExpiration.isBefore(LocalDate.now())) {
                    nonConformites.add("Permis de travail expiré");
                } else if (dateExpiration.isBefore(LocalDate.now().plusMonths(3))) {
                    alertes.add("Permis de travail expire dans moins de 3 mois");
                } else {
                    conformites.add("Permis de travail valide");
                }
            }

            if (employe.getNumeroVisa() == null) {
                alertes.add("Visa manquant (peut être requis selon le pays)");
            }

            if (employe.getNumeroCarteSejour() == null) {
                alertes.add("Carte de séjour manquante (peut être requise selon le pays)");
            }
        }
    }

    /**
     * Vérifie les formations obligatoires.
     */
    private void verifierFormationsObligatoires(EmployeModel employe, List<String> nonConformites,
                                                 List<String> alertes, List<String> conformites) {
        // TODO: Implémenter avec le service de compétences
        // Vérifier que les formations obligatoires selon le poste sont complétées
    }

    /**
     * Vérifie la conformité fiscale.
     */
    private void verifierConformiteFiscale(EmployeModel employe, List<String> nonConformites,
                                           List<String> alertes, List<String> conformites) {
        if (employe.getRegimeFiscal() == null || employe.getRegimeFiscal().isEmpty()) {
            alertes.add("Régime fiscal non défini");
        } else {
            conformites.add("Régime fiscal défini");
        }

        if (employe.getPaysFiscal() == null || employe.getPaysFiscal().isEmpty()) {
            alertes.add("Pays de résidence fiscale non défini");
        } else {
            conformites.add("Pays de résidence fiscale défini");
        }
    }

    /**
     * Calcule le score de conformité.
     */
    private double calculerScoreConformite(int conformites, int nonConformites) {
        int total = conformites + nonConformites;
        if (total == 0) return 0.0;
        return (double) conformites / total * 100.0;
    }

    /**
     * Génère un plan d'action pour la conformité.
     */
    public Map<String, Object> genererPlanActionConformite(String employeUuid, Long organisationId) {
        Map<String, Object> rapport = verifierConformite(employeUuid, organisationId);
        
        @SuppressWarnings("unchecked")
        List<String> nonConformites = (List<String>) rapport.get("nonConformites");
        @SuppressWarnings("unchecked")
        List<String> alertes = (List<String>) rapport.get("alertes");

        Map<String, Object> planAction = new HashMap<>();
        planAction.put("prioriteHaute", nonConformites);
        planAction.put("prioriteMoyenne", alertes);
        planAction.put("dateGeneration", LocalDate.now());
        planAction.put("echeance", LocalDate.now().plusDays(30));

        return planAction;
    }

    private EmployeModel trouverEmploye(String uuid, Long organisationId) {
        return employeRepository.findByUuid(uuid)
            .filter(e -> e.getOrganisationId().equals(organisationId))
            .filter(e -> !Boolean.TRUE.equals(e.getSupprime()))
            .orElseThrow(() -> new ResourceNotFoundException("Employe", uuid));
    }
}
