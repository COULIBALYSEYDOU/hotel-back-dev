package projet_hotelier.hotel.module.rh.pattern.strategy;

import projet_hotelier.hotel.module.rh.dto.request.employe.CreateEmployeRequest;

/**
 * Strategy Pattern pour les validations metier.
 * Permet de definir differentes strategies de validation.
 */
public interface ValidationStrategy {

    /**
     * Valide une requete de creation d'employe.
     * @param request La requete a valider
     * @throws IllegalArgumentException si la validation echoue
     */
    void validate(CreateEmployeRequest request);
}
