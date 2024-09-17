package knu.univ.cse.server.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    /**
     * 특정 LocalDateTime이 두 개의 LocalDateTime 사이에 포함되는지 확인
     * @param target 확인할 LocalDateTime
     * @param start 시작 LocalDateTime (경계 포함)
     * @param end 종료 LocalDateTime (경계 포함)
     * @return 포함되면 1, 포함되지 않으면 0
     */
    public static boolean isBetweenInclusive(LocalDateTime target, LocalDateTime start, LocalDateTime end) {
        if (target == null || start == null || end == null) return false;
        return target.isEqual(start) || target.isEqual(end) || (target.isAfter(start) && target.isBefore(end));
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
