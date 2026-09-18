package projet_hotelier.hotel.shared.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * Utilitaires pour la manipulation des dates.
 * Supporte les fuseaux horaires pour un systeme international.
 */
public final class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private DateUtils() {
        // Utility class
    }

    // Obtenir maintenant
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime now(ZoneId zoneId) {
        return LocalDateTime.now(zoneId);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDate today(ZoneId zoneId) {
        return LocalDate.now(zoneId);
    }

    // Formatage
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }

    public static String formatIsoDate(LocalDate date) {
        return date != null ? date.format(ISO_DATE_FORMATTER) : null;
    }

    public static String formatIsoDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(ISO_DATE_TIME_FORMATTER) : null;
    }

    // Parsing
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isBlank()) return null;
        return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }

    public static LocalDate parseIsoDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        return LocalDate.parse(dateStr, ISO_DATE_FORMATTER);
    }

    // Periodes
    public static LocalDate startOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate endOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate startOfYear(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

    public static LocalDate endOfYear(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfYear());
    }

    public static LocalDate startOfQuarter(LocalDate date) {
        int month = date.getMonthValue();
        int quarterStartMonth = ((month - 1) / 3) * 3 + 1;
        return LocalDate.of(date.getYear(), quarterStartMonth, 1);
    }

    public static LocalDate endOfQuarter(LocalDate date) {
        LocalDate startOfQuarter = startOfQuarter(date);
        return startOfQuarter.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static int getQuarter(LocalDate date) {
        return (date.getMonthValue() - 1) / 3 + 1;
    }

    // Calculs
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public static long monthsBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.MONTHS.between(start, end);
    }

    public static long yearsBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.YEARS.between(start, end);
    }

    // Verification
    public static boolean isInPeriod(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null || start == null || end == null) return false;
        return !date.isBefore(start) && !date.isAfter(end);
    }

    public static boolean isBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public static boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public static boolean isFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    // Conversion de fuseaux horaires
    public static LocalDateTime convertTimezone(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
        if (dateTime == null) return null;
        ZonedDateTime zonedDateTime = dateTime.atZone(fromZone);
        return zonedDateTime.withZoneSameInstant(toZone).toLocalDateTime();
    }

    public static LocalDateTime toUTC(LocalDateTime dateTime, ZoneId fromZone) {
        return convertTimezone(dateTime, fromZone, ZoneOffset.UTC);
    }

    public static LocalDateTime fromUTC(LocalDateTime utcDateTime, ZoneId toZone) {
        return convertTimezone(utcDateTime, ZoneOffset.UTC, toZone);
    }

    // Exercice fiscal (variable selon le pays)
    public static LocalDate startOfFiscalYear(LocalDate date, int fiscalYearStartMonth) {
        int year = date.getMonthValue() >= fiscalYearStartMonth ? date.getYear() : date.getYear() - 1;
        return LocalDate.of(year, fiscalYearStartMonth, 1);
    }

    public static LocalDate endOfFiscalYear(LocalDate date, int fiscalYearStartMonth) {
        LocalDate start = startOfFiscalYear(date, fiscalYearStartMonth);
        return start.plusYears(1).minusDays(1);
    }

    // Null-safe comparison
    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        return Objects.compare(date1, date2, LocalDate::compareTo) < 0;
    }

    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        return Objects.compare(date1, date2, LocalDate::compareTo) > 0;
    }

    public static boolean isEqual(LocalDate date1, LocalDate date2) {
        return Objects.equals(date1, date2);
    }
}
