package com.github.vvserdiuk.util;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by vvserdiuk on 28.03.2016.
 */
public class EventUtil {
    public static final LocalDate MIN_DATE = LocalDate.of(0, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static boolean isBetweenTimes(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}
