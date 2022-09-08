package com.gugu.upload.utils;

import com.gugu.upload.common.exception.TransformException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Transform util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Slf4j
public class TransformUtil {
    private TransformUtil(){}

    /**
     * Transform t.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param target the target
     * @return the t
     */
    public static <T> T transform(Object source, Class<T> target){
        try {
            Constructor<T> constructor = target.getConstructor();
            T targetObj = constructor.newInstance();
            BeanUtils.copyProperties(source, targetObj);
            return targetObj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error in class conversion", e);
            throw new TransformException(e.getMessage());
        }
    }

    /**
     * Transform list list.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param target the target
     * @return the list
     */
    public static <T> List<T> transformList(List<?> source, Class<T> target){
        List<T> resultList = new LinkedList<>();
        for (Object obj : source) {
            resultList.add(transform(obj, target));
        }
        return resultList;
    }
}
