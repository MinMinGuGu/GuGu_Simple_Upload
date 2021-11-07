package com.gugu.upload.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * The type Stream helper.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /11/07
 * @since 1.8
 */
@Slf4j
public class StreamHelper {
    private StreamHelper(){}

    /**
     * Closer stream.
     *
     * @param closeables the closeables
     */
    public static void closerStream(Closeable... closeables){
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null){
                    closeable.close();
                }
            }
        } catch (IOException e) {
            log.error("Failed to close stream", e);
        }
    }
}
