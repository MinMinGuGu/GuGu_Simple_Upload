package com.gugu.upload.utils;

import com.gugu.upload.exception.ParamsException;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The type Md5 util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class MD5Util {

    private MD5Util(){}

    /**
     * Encrypt string.
     *
     * @param plaintext the plaintext
     * @return the string
     */
    public static String encrypt(String plaintext){
        return DigestUtils.md5DigestAsHex(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Get file hash code string.
     *
     * @param inputStream the input stream
     * @return the string
     */
    public static String getInputStreamHashCode(InputStream inputStream){
        try {
            return DigestUtils.md5DigestAsHex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParamsException("InputStream have error");
        }
    }
}
