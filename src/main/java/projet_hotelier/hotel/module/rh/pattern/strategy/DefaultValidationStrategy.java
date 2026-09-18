package projet_hotelier.hotel.module.rh.pattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import projet_hotelier.hotel.module.rh.dto.request.employe.CreateEmployeRequest;
import projet_hotelier.hotel.shared.exception.ValidationException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation par defaut de la strategy de validation.
 * Valide les regles metier specifiques au domaine RH.
 */
@Slf4j
@Component
public class DefaultValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(CreateEmployeRequest request) {
        log.debug("Validation de la requete de creation d'employe");
        Map<String, String> errors = new HashMap<>();

        // Validation de l'email
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                errors.put("email", "Format d'email invalide");
            }
        }

        // Validation de la date de naissance
        if (request.getDateNaissance() != null) {
            if (request.getDateNaissance().isAfter(LocalDate.now().minusYears(14))) {
                errors.put("dateNaissance", "L'employe doit avoir au moins 14 ans");
            }
            if (request.getDateNaissance().isBefore(LocalDate.now().minusYears(100))) {
                errors.put("dateNaissance", "Date de naissance invalide");
            }
        }

        // Validation de la date d'embauche
        if (request.getDateEmbauche() != null && request.getDateNaissance() != null) {
            if (request.getDateEmbauche().isBefore(request.getDateNaissance())) {
                errors.put("dateEmbauche", "La date d'embauche ne peut pas etre anterieure a la date de naissance");
            }
        }

        // Validation du salaire
        if (request.getSalaireBase() != null && request.getSalaireBase().signum() < 0) {
            errors.put("salaireBase", "Le salaire de base ne peut pas etre negatif");
        }

        // Validation du telephone
        if (request.getTelephone() != null && !request.getTelephone().isEmpty()) {
            String phone = request.getTelephone().replaceAll("[^0-9]", "");
            if (phone.length() < 8 || phone.length() > 15) {
                errors.put("telephone", "Format de telephone invalide");
            }
        }

        if (!errors.isEmpty()) {
            log.warn("Erreurs de validation detectees: {}", errors);
            throw new ValidationException("Erreurs de validation", errors);
        }

        log.debug("Validation reussie");
    }
}
