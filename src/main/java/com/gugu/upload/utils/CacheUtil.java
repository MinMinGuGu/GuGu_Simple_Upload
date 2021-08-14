package com.gugu.upload.utils;

import com.gugu.upload.exception.ParamsException;
import lombok.Data;

import java.lang.ref.SoftReference;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Cache util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
public class CacheUtil {

    private static final Map<String, SoftReference<CacheObject>> CACHE_OBJECT_MAP = new ConcurrentHashMap<>(15);

    private CacheUtil() {
    }

    /**
     * Is there boolean.
     *
     * @param key the key
     * @return true Exist and cache. false Is not in the cache and deleted.
     */
    public static boolean isThere(String key){
        return get(key) != null;
    }

    /**
     * Get t.
     *
     * @param key the key
     * @return the t
     */
    public static Object get(String key) {
        SoftReference<CacheObject> cacheObjectSoftReference = CACHE_OBJECT_MAP.get(key);
        if (checkCacheObject(cacheObjectSoftReference)){
            return null;
        }
        CacheObject cacheObject = cacheObjectSoftReference.get();
        if (isUnavailable(cacheObject)){
            CACHE_OBJECT_MAP.remove(key);
            return null;
        }
        return cacheObject;
    }

    /**
     * Get t.
     *
     * @param <T>    the type parameter
     * @param key    the key
     * @param tClass the t class
     * @return the t
     */
    public static <T> T get(String key, Class<T> tClass) {
        return tClass.cast(get(key));
    }

    private static boolean checkCacheObject(SoftReference<CacheObject> softReference){
        return softReference == null;
    }

    private static boolean isUnavailable(CacheObject cacheObject) {
        return cacheObject == null || checkTime(cacheObject);
    }


    private static boolean checkTime(CacheObject cacheObject) {
        return LocalTime.now().isAfter(cacheObject.getExpireDate());
    }

    /**
     * Pull.
     *
     * @param key         the key
     * @param cacheObject the cache object
     */
    public static void pull(String key, CacheObject cacheObject) {
        CACHE_OBJECT_MAP.put(key, new SoftReference<>(cacheObject));
    }

    /**
     * The type Cache object.
     *
     * @date 2021 /08/14
     * @since 1.0
     */
    @Data
    public static class CacheObject {
        private LocalTime expireDate;
        private Object object;

        /**
         * Instantiates a new Cache object.
         *
         * @param object the object
         */
        public CacheObject(Object object){
            this(object, 30, TimeUnit.MINUTE);
        }

        /**
         * Instantiates a new Cache object.
         *
         * @param object   the object
         * @param time     the time
         * @param timeUnit the time unit
         */
        public CacheObject(Object object, long time, TimeUnit timeUnit){
            this.object = object;
            this.expireDate = handler(time, timeUnit);
        }

        /**
         * Set time.
         *
         * @param time     the time
         * @param timeUnit the time unit
         */
        public void setTime(long time, TimeUnit timeUnit){
            this.expireDate = handler(time, timeUnit);
        }

        private LocalTime handler(long time, TimeUnit timeUnit) {
            switch (timeUnit) {
                case SECOND:{
                    return LocalTime.now().plusSeconds(time);
                }
                case MINUTE:{
                    return LocalTime.now().plusMinutes(time);
                }
                case HOUR:{
                    return LocalTime.now().plusHours(time);
                }
                default:{
                    throw new ParamsException("com.gugu.upload.utils.CacheUtil.CacheObject.TimeUnit 参数异常");
                }
            }
        }

        /**
         * The enum Time unit.
         *
         * @date 2021 /08/14
         * @since 1.0
         */
        public enum TimeUnit{
            /**
             * Second time unit.
             */
            SECOND,
            /**
             * Minute time unit.
             */
            MINUTE,
            /**
             * Hour time unit.
             */
            HOUR
        }
    }
}
