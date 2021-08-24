package com.gugu.upload.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * The type Jasypt util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class JasyptUtil {

    private JasyptUtil(){}

    /**
     * Encrypt string.
     *
     * @param plaintext the plaintext
     * @param key       the key
     * @return the string
     */
    public static String encrypt(String plaintext, String key){
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        return basicTextEncryptor.encrypt(plaintext);
    }
}
