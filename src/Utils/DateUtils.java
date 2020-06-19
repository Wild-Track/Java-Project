package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static DateTimeFormatter usualDateFormat = DateTimeFormatter.ofPattern("d MMMM uuuu");
    public static DateTimeFormatter usualDateNumericFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String toUsualDate(LocalDate date) {
        return date.format(usualDateFormat);
    }

    public static String toUsualDateNumeric(LocalDate date) {
        return date.format(usualDateNumericFormat);
    }

    public static LocalDate numericToLocalDate(String date) {
        return LocalDate.parse(date, usualDateNumericFormat);
    }
}