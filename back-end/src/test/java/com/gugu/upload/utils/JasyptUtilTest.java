package com.gugu.upload.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * The type Jasypt util test.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
class JasyptUtilTest {

    private static String plainText;

    private static String key;

    @BeforeAll
    static void beforeClass(){
        key = System.getenv("key");
        plainText = System.getenv("plainText");
    }

    /**
     * Encrypt username.
     */
    @Test
    void encrypt() {
        Assert.hasText(plainText, "please set the encrypted environment variable");
        String encrypt = JasyptUtil.encrypt(plainText, key);
        Assert.notNull(encrypt, "error");
        System.out.println("encrypt result = " + encrypt);
    }
}