package com.gugu.upload.utils;

import java.io.InputStream;
import java.util.UUID;

/**
 * The type File util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
public class FileUtil {
    private FileUtil(){}

    /**
     * Get unique file name string.
     *
     * @return the string
     */
    public static String getUniqueFileName(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Get file hash code string.
     *
     * @param inputStream the input stream
     * @return the string
     */
    public static String getFileHashCode(InputStream inputStream){
        return MD5Util.getInputStreamHashCode(inputStream);
    }

    /**
     * Get file suffix string.
     *
     * @param fileName the file name
     * @return the string
     */
    public static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
