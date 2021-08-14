package com.gugu.upload.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Date util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
public class DateUtil {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateUtil(){}

    /**
     * Get str by date now string.
     *
     * @return the string
     */
    public static synchronized String getStrByDateNow(){
        return SIMPLE_DATE_FORMAT.format(new Date());
    }
}
