package com.gugu.upload.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * The type File helper.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /11/13
 * @since 1.8
 */
@Slf4j
public class FileSizeUtil {

    private static final int FILE_SIZE_BASIC_UNIT = 1024;

    private FileSizeUtil() {
    }

    /**
     * Convert file size string.
     *
     * @param fileSizeStr the file size str
     * @return the string
     */
    public static String convertFileSize(String fileSizeStr) {
        try {
            long fileSize = Long.parseLong(fileSizeStr);
            return byteFormat(fileSize, true);
        } catch (NumberFormatException e) {
            log.error("Error converting file size", e);
            return fileSizeStr;
        }
    }

    /**
     * Byte format string.
     *
     * @param bytes   the bytes
     * @param company the company
     * @return the string
     */
    public static String byteFormat(long bytes, boolean company) {
        String[] units = new String[]{" B", " KB", " MB", " GB", " TB", " PB", " EB", " ZB", " YB"};
        int unit = FILE_SIZE_BASIC_UNIT;
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        double pre;
        if (bytes > unit) {
            pre = bytes / Math.pow(unit, exp);
        } else {
            pre = (double) bytes / (double) unit;
        }
        if (company) {
            return String.format(Locale.ROOT, "%.2f%s", pre, units[exp]);
        }
        return String.format(Locale.ROOT, "%.2f", pre);
    }



}
