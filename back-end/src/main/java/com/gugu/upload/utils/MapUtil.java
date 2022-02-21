package com.gugu.upload.utils;

import com.gugu.upload.exception.ParamsException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Map util.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/09
 * @since 1.8
 */
public class MapUtil {
    private MapUtil() {
    }

    /**
     * To map map.
     *
     * @param keyAndValues the key and values
     * @return the map
     */
    public static Map<?, ?> toMap(Object... keyAndValues) {
        if (keyAndValues.length % 2 != 0) {
            throw new ParamsException("The passed in parameter does not constitute a key value pair.");
        }
        Map<Object, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < keyAndValues.length - 1; i++) {
            Object key = keyAndValues[i];
            Object value = keyAndValues[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
