package com.gugu.upload.utils;

import com.gugu.upload.common.constant.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Date util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class DateUtil {

    private DateUtil(){}

    /**
     * Get str by date now string.
     *
     * @return the string
     */
    public static String getStrByDateNow(){
        return new SimpleDateFormat(Constant.FORMAT_DATE_STR).format(new Date());
    }
}
