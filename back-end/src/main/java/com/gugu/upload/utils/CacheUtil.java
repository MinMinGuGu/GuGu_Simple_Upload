package com.gugu.upload.utils;

import com.gugu.upload.exception.ParamsException;
import com.gugu.upload.task.CacheTask;
import lombok.Data;

import java.lang.ref.SoftReference;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Cache util.
 * <p>If there is a large amount of data that is no longer read in the cache and {@link CacheUtil#clearUp()} is not called, it may cause memory overflow.</p>
 * <p>In order to deal with the possibility of memory overflow, in the application and used timing tasks to periodically clean up the cache.see {@link CacheTask#cleanCache()}</p>
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class CacheUtil {

    private static final Map<String, SoftReference<CacheObject>> CACHE_OBJECT_MAP = new ConcurrentHashMap<>(100);

    private CacheUtil() {
    }

    /**
     * Is there boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean isThere(String key) {
        return get(key) != null;
    }

    /**
     * Clear up.
     */
    public static void clearUp(){
        CACHE_OBJECT_MAP.forEach((key, soft) -> isThere(key));
    }

    /**
     * Remove.
     *
     * @param key the key
     */
    public static void remove(String key) {
        if (key != null) {
            CACHE_OBJECT_MAP.remove(key);
        }
    }

    /**
     * Remove.
     *
     * @param <T>    the type parameter
     * @param source the source
     */
    public static <T> void remove(Class<T> source) {
        CACHE_OBJECT_MAP.forEach((key, object) -> {
            CacheObject cacheObject = get(key);
            if (checkIsSource(cacheObject, source)) {
                remove(key);
            }
        });
    }

    /**
     * Get list.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @return if found return list otherwise null
     */
    public static <T> List<T> get(Class<T> source) {
        List<T> cacheObjects = new LinkedList<>();
        CACHE_OBJECT_MAP.forEach((key, object) -> {
            CacheObject cacheObject = get(key);
            if (checkIsSource(cacheObject, source)) {
                Object cacheObjectObject = cacheObject.getObject();
                cacheObjects.add(source.cast(cacheObjectObject));
            }
        });
        return cacheObjects;
    }

    private static <T> boolean checkIsSource(CacheObject cacheObject, Class<T> source) {
        return cacheObject != null && source != null && source.isAssignableFrom(cacheObject.getObject().getClass());
    }

    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     */
    public static CacheObject get(String key) {
        SoftReference<CacheObject> cacheObjectSoftReference = CACHE_OBJECT_MAP.get(key);
        if (checkCacheObject(cacheObjectSoftReference)) {
            return null;
        }
        CacheObject cacheObject = cacheObjectSoftReference.get();
        if (isUnavailable(cacheObject)) {
            remove(key);
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

    private static boolean checkCacheObject(SoftReference<CacheObject> softReference) {
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
     * @author minmin
     * @version 1.0
     * @since 1.8
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
        public CacheObject(Object object) {
            this(object, 30, TimeUnit.MINUTE);
        }

        /**
         * Instantiates a new Cache object.
         *
         * @param object   the object
         * @param time     the time
         * @param timeUnit the time unit
         */
        public CacheObject(Object object, long time, TimeUnit timeUnit) {
            this.object = object;
            this.expireDate = handler(time, timeUnit);
        }

        /**
         * Set time.
         *
         * @param time     the time
         * @param timeUnit the time unit
         */
        public void setTime(long time, TimeUnit timeUnit) {
            this.expireDate = handler(time, timeUnit);
        }

        private LocalTime handler(long time, TimeUnit timeUnit) {
            switch (timeUnit) {
                case SECOND: {
                    return LocalTime.now().plusSeconds(time);
                }
                case MINUTE: {
                    return LocalTime.now().plusMinutes(time);
                }
                case HOUR: {
                    return LocalTime.now().plusHours(time);
                }
                default: {
                    throw new ParamsException("com.gugu.upload.utils.CacheUtil.CacheObject.TimeUnit 参数异常");
                }
            }
        }

        /**
         * The enum Time unit.
         *
         * @author minmin
         * @version 1.0
         * @since 1.8
         */
        public enum TimeUnit {
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
