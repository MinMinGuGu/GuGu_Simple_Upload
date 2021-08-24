package com.gugu.upload.common;

/**
 * The type Constant.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
public class Constant {

    private Constant(){}

    /**
     * The constant RECORD_CACHE_KEY.
     */
    public static final String RECORD_CACHE_PREFIX = "record_cache_key_";

    /**
     * The constant CPU_INTENSIVE.
     */
    public static final int CPU_INTENSIVE = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * The constant IO_INTENSIVE.
     */
    public static final int IO_INTENSIVE = Runtime.getRuntime().availableProcessors() * 2;
}
