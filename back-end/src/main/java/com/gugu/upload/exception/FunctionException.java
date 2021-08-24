package com.gugu.upload.exception;

/**
 * The type Function exception.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class FunctionException extends RuntimeException{
    /**
     * Instantiates a new Function exception.
     *
     * @param message the message
     */
    public FunctionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Function exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FunctionException(String message, Throwable cause) {
        super(message, cause);
    }
}
