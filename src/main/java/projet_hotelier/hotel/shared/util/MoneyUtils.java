package projet_hotelier.hotel.shared.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

/**
 * Utilitaires pour les calculs monetaires.
 * Conforme aux normes OHADA pour les calculs financiers.
 * Precision de 2 decimales par defaut, configurable pour certaines devises.
 */
public final class MoneyUtils {

    private static final int DEFAULT_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
    private static final MathContext PRECISION = new MathContext(15, RoundingMode.HALF_UP);

    // Formats de devise courants
    private static final DecimalFormat XOF_FORMAT; // Franc CFA
    private static final DecimalFormat EUR_FORMAT;
    private static final DecimalFormat USD_FORMAT;

    static {
        DecimalFormatSymbols xofSymbols = new DecimalFormatSymbols(Locale.FRANCE);
        xofSymbols.setGroupingSeparator(' ');
        xofSymbols.setDecimalSeparator(',');
        XOF_FORMAT = new DecimalFormat("#,##0", xofSymbols);

        DecimalFormatSymbols eurSymbols = new DecimalFormatSymbols(Locale.FRANCE);
        eurSymbols.setGroupingSeparator(' ');
        eurSymbols.setDecimalSeparator(',');
        EUR_FORMAT = new DecimalFormat("#,##0.00", eurSymbols);

        DecimalFormatSymbols usdSymbols = new DecimalFormatSymbols(Locale.US);
        USD_FORMAT = new DecimalFormat("#,##0.00", usdSymbols);
    }

    private MoneyUtils() {
        // Utility class
    }

    // Creation
    public static BigDecimal of(double value) {
        return BigDecimal.valueOf(value).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal of(String value) {
        if (value == null || value.isBlank()) return BigDecimal.ZERO;
        return new BigDecimal(value.replace(",", ".").replace(" ", ""))
                .setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal of(long value) {
        return BigDecimal.valueOf(value).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal zero() {
        return BigDecimal.ZERO.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    // Operations arithmetiques
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return nullSafe(a).add(nullSafe(b)).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return nullSafe(a).subtract(nullSafe(b)).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return nullSafe(a).multiply(nullSafe(b), PRECISION).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (isZero(b)) {
            throw new ArithmeticException("Division par zero");
        }
        return nullSafe(a).divide(b, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        if (isZero(b)) {
            throw new ArithmeticException("Division par zero");
        }
        return nullSafe(a).divide(b, scale, DEFAULT_ROUNDING);
    }

    // Pourcentages
    public static BigDecimal percentage(BigDecimal amount, BigDecimal percent) {
        return multiply(amount, divide(percent, of(100)));
    }

    public static BigDecimal addPercentage(BigDecimal amount, BigDecimal percent) {
        return add(amount, percentage(amount, percent));
    }

    public static BigDecimal subtractPercentage(BigDecimal amount, BigDecimal percent) {
        return subtract(amount, percentage(amount, percent));
    }

    public static BigDecimal calculatePercentage(BigDecimal part, BigDecimal total) {
        if (isZero(total)) return zero();
        return multiply(divide(part, total), of(100));
    }

    // Calculs TVA (conforme OHADA)
    public static BigDecimal calculateTVA(BigDecimal montantHT, BigDecimal tauxTVA) {
        return percentage(montantHT, tauxTVA);
    }

    public static BigDecimal calculateTTC(BigDecimal montantHT, BigDecimal tauxTVA) {
        return addPercentage(montantHT, tauxTVA);
    }

    public static BigDecimal calculateHT(BigDecimal montantTTC, BigDecimal tauxTVA) {
        BigDecimal diviseur = add(of(100), tauxTVA);
        return divide(multiply(montantTTC, of(100)), diviseur);
    }

    public static BigDecimal extractTVA(BigDecimal montantTTC, BigDecimal tauxTVA) {
        BigDecimal montantHT = calculateHT(montantTTC, tauxTVA);
        return subtract(montantTTC, montantHT);
    }

    // Comparaisons
    public static boolean isZero(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNegative(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isGreaterThan(BigDecimal a, BigDecimal b) {
        return nullSafe(a).compareTo(nullSafe(b)) > 0;
    }

    public static boolean isLessThan(BigDecimal a, BigDecimal b) {
        return nullSafe(a).compareTo(nullSafe(b)) < 0;
    }

    public static boolean isGreaterOrEqual(BigDecimal a, BigDecimal b) {
        return nullSafe(a).compareTo(nullSafe(b)) >= 0;
    }

    public static boolean isLessOrEqual(BigDecimal a, BigDecimal b) {
        return nullSafe(a).compareTo(nullSafe(b)) <= 0;
    }

    public static boolean equals(BigDecimal a, BigDecimal b) {
        return nullSafe(a).compareTo(nullSafe(b)) == 0;
    }

    // Arrondi
    public static BigDecimal round(BigDecimal value, int scale) {
        return nullSafe(value).setScale(scale, DEFAULT_ROUNDING);
    }

    public static BigDecimal roundUp(BigDecimal value, int scale) {
        return nullSafe(value).setScale(scale, RoundingMode.CEILING);
    }

    public static BigDecimal roundDown(BigDecimal value, int scale) {
        return nullSafe(value).setScale(scale, RoundingMode.FLOOR);
    }

    // Conversion de devises
    public static BigDecimal convertCurrency(BigDecimal amount, BigDecimal exchangeRate) {
        return multiply(amount, exchangeRate);
    }

    public static BigDecimal convertCurrency(BigDecimal amount, BigDecimal exchangeRate, int targetScale) {
        return multiply(amount, exchangeRate).setScale(targetScale, DEFAULT_ROUNDING);
    }

    // Formatage selon la devise
    public static String formatXOF(BigDecimal amount) {
        return XOF_FORMAT.format(nullSafe(amount)) + " FCFA";
    }

    public static String formatEUR(BigDecimal amount) {
        return EUR_FORMAT.format(nullSafe(amount)) + " EUR";
    }

    public static String formatUSD(BigDecimal amount) {
        return "$" + USD_FORMAT.format(nullSafe(amount));
    }

    public static String format(BigDecimal amount, String currencyCode) {
        return switch (currencyCode.toUpperCase()) {
            case "XOF", "XAF" -> formatXOF(amount);
            case "EUR" -> formatEUR(amount);
            case "USD" -> formatUSD(amount);
            default -> nullSafe(amount).toPlainString() + " " + currencyCode;
        };
    }

    // Min/Max
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return nullSafe(a).min(nullSafe(b));
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return nullSafe(a).max(nullSafe(b));
    }

    public static BigDecimal abs(BigDecimal value) {
        return nullSafe(value).abs();
    }

    public static BigDecimal negate(BigDecimal value) {
        return nullSafe(value).negate();
    }

    // Utilitaires
    private static BigDecimal nullSafe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    public static BigDecimal sum(BigDecimal... values) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            total = total.add(nullSafe(value));
        }
        return total.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    // Verification solde suffisant
    public static boolean hasSufficientFunds(BigDecimal balance, BigDecimal amount) {
        return isGreaterOrEqual(balance, amount);
    }

    // Calcul ecart budgetaire
    public static BigDecimal calculateVariance(BigDecimal actual, BigDecimal budget) {
        return subtract(actual, budget);
    }

    public static BigDecimal calculateVariancePercentage(BigDecimal actual, BigDecimal budget) {
        if (isZero(budget)) return zero();
        return calculatePercentage(calculateVariance(actual, budget), budget);
    }
}
