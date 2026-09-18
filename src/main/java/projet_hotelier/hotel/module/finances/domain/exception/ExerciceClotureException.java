package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

public class ExerciceClotureException extends BusinessException {

    private final Integer exercice;

    public ExerciceClotureException(Integer exercice) {
        super("EXERCICE_CLOTURE",
                String.format("L'exercice %d est cloture et ne peut plus recevoir d'ecritures", exercice));
        this.exercice = exercice;
    }

    public ExerciceClotureException(Integer exercice, String periode) {
        super("PERIODE_CLOTUREE",
                String.format("La periode %s de l'exercice %d est cloturee", periode, exercice));
        this.exercice = exercice;
    }

    public Integer getExercice() {
        return exercice;
    }
}
