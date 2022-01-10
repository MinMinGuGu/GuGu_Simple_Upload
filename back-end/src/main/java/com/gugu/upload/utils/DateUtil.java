package com.gugu.upload.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * The type Date util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class DateUtil {

    /**
     * The constant FORMAT_DATE_STR.
     */
    public static final String FORMAT_DATE_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * The constant DEFAULT_ZONE_ID.
     */
    public static final String DEFAULT_ZONE_ID = "Asia/Shanghai";

    private DateUtil() {
    }

    /**
     * Get string by format string.
     *
     * @param date   the date
     * @param format the format
     * @return the string
     */
    public static String getStringByFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Get str by date now string.
     *
     * @return the string
     */
    public static String getDefaultFormat() {
        return getDefaultFormat(new Date());
    }

    /**
     * Get default format string.
     *
     * @param date the date
     * @return the string
     */
    public static String getDefaultFormat(Date date) {
        return new SimpleDateFormat(FORMAT_DATE_STR).format(date);
    }

    /**
     * Get default format string.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String getDefaultFormat(LocalDateTime localDateTime) {
        return getDefaultFormat(localDateTime2Date(localDateTime));
    }

    /**
     * Date 2 local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date2LocalDateTime(date, false);
    }

    /**
     * Date 2 local date time local date time.
     *
     * @param date      the date
     * @param isDefault the is default
     * @return the local date time
     */
    public static LocalDateTime date2LocalDateTime(Date date, boolean isDefault) {
        Instant instant = date.toInstant();
        ZoneId zoneId = isDefault ? ZoneId.systemDefault() : ZoneId.of(DEFAULT_ZONE_ID);
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Local date time 2 date date.
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return localDateTime2Date(localDateTime, false);
    }

    /**
     * Local date time 2 date date.
     *
     * @param localDateTime the local date time
     * @param isDefault     the is default
     * @return the date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime, boolean isDefault) {
        ZoneId zoneId = isDefault ? ZoneId.systemDefault() : ZoneId.of(DEFAULT_ZONE_ID);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
