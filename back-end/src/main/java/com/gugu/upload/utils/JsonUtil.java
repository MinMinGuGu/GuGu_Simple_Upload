package com.gugu.upload.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Json util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    /**
     * Obj 2 json str string.
     *
     * @param obj the obj
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String obj2JsonStr(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * Json str 2 obj t.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param target  the target
     * @return the t
     */
    public static <T> T jsonStr2Obj(String jsonStr, Class<T> target) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, target);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert JSON string to {} object", target);
            return null;
        }
    }

    /**
     * Json str 2 obj list list.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param target  the target
     * @return the list
     */
    public static <T> List<T> jsonStr2ObjList(String jsonStr, Class<T> target) {
        try {
            List<T> readResult = OBJECT_MAPPER.readValue(jsonStr, new TypeReference<List<T>>() {
            });
            List<T> result = new LinkedList<>();
            for (T obj : readResult) {
                if (obj instanceof LinkedHashMap) {
                    result.add(jsonStr2Obj(obj2JsonStr(obj), target));
                } else if (obj.getClass().isAssignableFrom(target)) {
                    result.add(obj);
                } else {
                    log.error("I don't know how to convert {} to a list collection", obj);
                    return null;
                }
            }
            return result;
        } catch (JsonProcessingException e) {
            log.error("Failed to convert JSON string to {} object collection", target);
            return null;
        }
    }

}
