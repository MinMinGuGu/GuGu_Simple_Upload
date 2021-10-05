package com.gugu.upload.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * The type Md 5 util test.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
class MD5UtilTest {

    /**
     * Encrypt.
     */
    @Test
    void encrypt() {
        String encrypt = MD5Util.encrypt("admin");
        Assert.notNull(encrypt, "error");
        System.out.println("encrypt = " + encrypt);
    }

}