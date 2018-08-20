package com.tmoncorp.wettyapi.utils;

import com.tmoncorp.wettyapi.exception.ObjectCreationWarningException;

import java.time.format.DateTimeFormatter;

public final class LocalDateTimeConstants {

    /**
     * 객체 생성 방지
     */
    private LocalDateTimeConstants() {
        throw new ObjectCreationWarningException();
    }

    public static final String DATE_TIME_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_HOUR_STRING = "yyyy-MM-dd HH:00:00";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_STRING);
    public static final DateTimeFormatter DATE_TIME_HOUR_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_HOUR_STRING);

}
