package com.gugu.upload.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * The interface Support query.
 *
 * @param <T> the type parameter
 * @author minmin
 * @version 1.0
 * @date 2021 /11/08
 * @since 1.8
 */
public interface ISupportQuery<T> {
    /**
     * To query wrapper query wrapper.
     *
     * @return the query wrapper
     */
    QueryWrapper<T> toQueryWrapper();

    /**
     * Get query wrapper query wrapper.
     *
     * @param t the t
     * @return the query wrapper
     */
    default QueryWrapper<T> getQueryWrapper(T t){
        QueryWrapper<T> query = Wrappers.query(t);
        query.clear();
        return query;
    }
}
