package com.gugu.upload.common.exception;

/**
 * The type Function exception.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class FunctionException extends RuntimeException {
    private static final long serialVersionUID = -7110031378463837328L;

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
