package com.gugu.upload.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * The type Jasypt util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
public class JasyptUtil {
    private static final BasicTextEncryptor BASIC_TEXT_ENCRYPTOR = new BasicTextEncryptor();

    private JasyptUtil(){}

    /**
     * Encrypt string.
     *
     * @param plaintext the plaintext
     * @param key       the key
     * @return the string
     */
    public static String encrypt(String plaintext, String key){
        BASIC_TEXT_ENCRYPTOR.setPassword(key);
        return BASIC_TEXT_ENCRYPTOR.encrypt(plaintext);
    }
}
