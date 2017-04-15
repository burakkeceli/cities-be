package com.cities.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class TimeUtil {
    private static final String DEFAULT_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static String convertDefaultTimeFormatter(DateTime dateTime) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DEFAULT_TIME_FORMATTER);
        return fmt.print(dateTime);
    }
}
