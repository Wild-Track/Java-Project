package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static DateTimeFormatter usualDateFormat = DateTimeFormatter.ofPattern("d MMMM uuuu");

    public static String toUsualDate(LocalDate date) {
        return date.format(usualDateFormat);
    }
}