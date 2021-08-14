package com.gugu.upload.utils;

import com.gugu.upload.exception.TransformException;
import org.springframework.beans.BeanUtils;

/**
 * The type Transform util.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
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
            T targetObj = target.newInstance();
            BeanUtils.copyProperties(source, targetObj);
            return targetObj;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new TransformException(e.getMessage());
        }
    }
}
