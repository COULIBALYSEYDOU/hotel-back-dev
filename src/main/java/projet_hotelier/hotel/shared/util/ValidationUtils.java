package projet_hotelier.hotel.shared.util;

import projet_hotelier.hotel.shared.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utilitaires de validation des donnees.
 */
public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{8,15}$");

    private static final Pattern IBAN_PATTERN = Pattern.compile(
            "^[A-Z]{2}[0-9]{2}[A-Z0-9]{4,30}$");

    private static final Pattern SIRET_PATTERN = Pattern.compile(
            "^[0-9]{14}$");

    private static final Pattern NIF_PATTERN = Pattern.compile(
            "^[A-Z0-9]{8,20}$");

    private ValidationUtils() {
        // Utility class
    }

    // Validation null/empty
    public static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName, fieldName + " est requis");
        }
    }

    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldName, fieldName + " est requis et ne peut pas etre vide");
        }
    }

    public static void requireNonEmpty(Collection<?> collection, String fieldName) {
        if (collection == null || collection.isEmpty()) {
            throw new ValidationException(fieldName, fieldName + " ne peut pas etre vide");
        }
    }

    // Validation numerique
    public static void requirePositive(BigDecimal value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(fieldName, fieldName + " doit etre positif");
        }
    }

    public static void requireNonNegative(BigDecimal value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(fieldName, fieldName + " ne peut pas etre negatif");
        }
    }

    public static void requirePositive(Long value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value <= 0) {
            throw new ValidationException(fieldName, fieldName + " doit etre positif");
        }
    }

    public static void requireInRange(BigDecimal value, BigDecimal min, BigDecimal max, String fieldName) {
        requireNonNull(value, fieldName);
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new ValidationException(fieldName,
                    String.format("%s doit etre compris entre %s et %s", fieldName, min, max));
        }
    }

    public static void requireInRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new ValidationException(fieldName,
                    String.format("%s doit etre compris entre %d et %d", fieldName, min, max));
        }
    }

    // Validation de longueur
    public static void requireMaxLength(String value, int maxLength, String fieldName) {
        if (value != null && value.length() > maxLength) {
            throw new ValidationException(fieldName,
                    String.format("%s ne peut pas depasser %d caracteres", fieldName, maxLength));
        }
    }

    public static void requireMinLength(String value, int minLength, String fieldName) {
        if (value == null || value.length() < minLength) {
            throw new ValidationException(fieldName,
                    String.format("%s doit contenir au moins %d caracteres", fieldName, minLength));
        }
    }

    public static void requireLength(String value, int minLength, int maxLength, String fieldName) {
        requireMinLength(value, minLength, fieldName);
        requireMaxLength(value, maxLength, fieldName);
    }

    // Validation de format
    public static void requireValidEmail(String email, String fieldName) {
        requireNonBlank(email, fieldName);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(fieldName, "Format d'email invalide");
        }
    }

    public static void requireValidPhone(String phone, String fieldName) {
        if (phone != null && !phone.isBlank()) {
            String cleaned = phone.replaceAll("[\\s\\-\\.]", "");
            if (!PHONE_PATTERN.matcher(cleaned).matches()) {
                throw new ValidationException(fieldName, "Format de telephone invalide");
            }
        }
    }

    public static void requireValidIBAN(String iban, String fieldName) {
        if (iban != null && !iban.isBlank()) {
            String cleaned = iban.replaceAll("\\s", "").toUpperCase();
            if (!IBAN_PATTERN.matcher(cleaned).matches()) {
                throw new ValidationException(fieldName, "Format IBAN invalide");
            }
        }
    }

    public static void requireValidSIRET(String siret, String fieldName) {
        if (siret != null && !siret.isBlank()) {
            String cleaned = siret.replaceAll("\\s", "");
            if (!SIRET_PATTERN.matcher(cleaned).matches()) {
                throw new ValidationException(fieldName, "Format SIRET invalide (14 chiffres requis)");
            }
        }
    }

    public static void requireValidNIF(String nif, String fieldName) {
        if (nif != null && !nif.isBlank()) {
            String cleaned = nif.replaceAll("\\s", "").toUpperCase();
            if (!NIF_PATTERN.matcher(cleaned).matches()) {
                throw new ValidationException(fieldName, "Format NIF invalide");
            }
        }
    }

    // Validation de dates
    public static void requireNotInFuture(LocalDate date, String fieldName) {
        requireNonNull(date, fieldName);
        if (date.isAfter(LocalDate.now())) {
            throw new ValidationException(fieldName, fieldName + " ne peut pas etre dans le futur");
        }
    }

    public static void requireNotInPast(LocalDate date, String fieldName) {
        requireNonNull(date, fieldName);
        if (date.isBefore(LocalDate.now())) {
            throw new ValidationException(fieldName, fieldName + " ne peut pas etre dans le passe");
        }
    }

    public static void requireDateInRange(LocalDate date, LocalDate start, LocalDate end, String fieldName) {
        requireNonNull(date, fieldName);
        if (date.isBefore(start) || date.isAfter(end)) {
            throw new ValidationException(fieldName,
                    String.format("%s doit etre entre %s et %s", fieldName, start, end));
        }
    }

    public static void requireEndDateAfterStartDate(LocalDate startDate, LocalDate endDate,
                                                     String startFieldName, String endFieldName) {
        requireNonNull(startDate, startFieldName);
        requireNonNull(endDate, endFieldName);
        if (!endDate.isAfter(startDate)) {
            throw new ValidationException(endFieldName,
                    String.format("%s doit etre apres %s", endFieldName, startFieldName));
        }
    }

    // Validation d'appartenance
    public static <T extends Enum<T>> void requireValidEnum(String value, Class<T> enumClass, String fieldName) {
        requireNonBlank(value, fieldName);
        try {
            Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(fieldName,
                    String.format("Valeur '%s' invalide pour %s", value, fieldName));
        }
    }

    public static <T> void requireIn(T value, Collection<T> allowedValues, String fieldName) {
        requireNonNull(value, fieldName);
        if (!allowedValues.contains(value)) {
            throw new ValidationException(fieldName,
                    String.format("Valeur '%s' non autorisee pour %s", value, fieldName));
        }
    }

    // Validation tenant/organisation
    public static void requireSameOrganisation(Long expected, Long actual) {
        if (!expected.equals(actual)) {
            throw new ValidationException("organisationId",
                    "Acces refuse: ressource appartenant a une autre organisation");
        }
    }

    public static void requireSameHotel(Long expected, Long actual) {
        if (expected != null && !expected.equals(actual)) {
            throw new ValidationException("hotelId",
                    "Acces refuse: ressource appartenant a un autre hotel");
        }
    }

    // Utilitaires
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isBlank()) return false;
        String cleaned = phone.replaceAll("[\\s\\-\\.]", "");
        return PHONE_PATTERN.matcher(cleaned).matches();
    }

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
