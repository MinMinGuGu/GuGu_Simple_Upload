package com.gugu.upload.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The type Json util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class JsonUtil {

    private JsonUtil(){}

    /**
     * Obj 2 json str string.
     *
     * @param obj the obj
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String obj2JsonStr(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonTool.INSTANCE.objectMapper;
        return objectMapper.writeValueAsString(obj);
    }

    private enum JsonTool{
        /**
         * Instance json tool.
         */
        INSTANCE;

        private final ObjectMapper objectMapper = new ObjectMapper();
    }
}
