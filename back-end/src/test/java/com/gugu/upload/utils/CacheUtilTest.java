package com.gugu.upload.utils;

import com.gugu.upload.common.entity.OperationLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The type Cache util test.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
class CacheUtilTest {

    private static final String RECORD_CACHE_PREFIX = "record_cache_key_";

    /**
     * Init cache.
     */
    @BeforeEach
    void initCache() {
        for (int i = 0; i < 10; i++) {
            OperationLog operationLog = new OperationLog();
            operationLog.setId(i);
            CacheUtil.CacheObject cacheObject = new CacheUtil.CacheObject(operationLog, 5, CacheUtil.CacheObject.TimeUnit.MINUTE);
            CacheUtil.pull(getCacheKey(), cacheObject);
        }
    }

    private String getCacheKey(){
        return RECORD_CACHE_PREFIX + UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Get.
     */
    @Test
    void get() {
        List<Map<String, OperationLog>> maps = CacheUtil.get(OperationLog.class);
        Assertions.assertNotEquals(0, maps.size());
        System.out.println("OperationLogs = " + maps);
    }

    /**
     * Remove.
     */
    @Test
    void remove() {
        CacheUtil.remove(OperationLog.class);
        Assertions.assertEquals(0, CacheUtil.get(OperationLog.class).size());
    }
}