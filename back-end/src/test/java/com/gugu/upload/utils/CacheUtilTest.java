package com.gugu.upload.utils;

import com.gugu.upload.common.constant.Constant;
import com.gugu.upload.common.entity.Visit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;


/**
 * The type Cache util test.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
class CacheUtilTest {

    /**
     * Init cache.
     */
    @BeforeEach
    void initCache(){
        for (int i = 0; i < 10; i++) {
            Visit visit = new Visit();
            visit.setId(i);
            CacheUtil.CacheObject cacheObject = new CacheUtil.CacheObject(visit, 5, CacheUtil.CacheObject.TimeUnit.MINUTE);
            CacheUtil.pull(getCacheKey(), cacheObject);
        }
    }

    private String getCacheKey(){
        return Constant.RECORD_CACHE_PREFIX + UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Get.
     */
    @Test
    void get() {
        List<Visit> visits = CacheUtil.get(Visit.class);
        Assertions.assertNotEquals(0, visits.size());
        System.out.println("visits = " + visits);
    }

    /**
     * Remove.
     */
    @Test
    void remove(){
        CacheUtil.remove(Visit.class);
        Assertions.assertEquals(0, CacheUtil.get(Visit.class).size());
    }
}