package com.gugu.upload.exception;

/**
 * The type Unknown exception.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
public class UnknownException extends RuntimeException{
    /**
     * Instantiates a new Unknown exception.
     *
     * @param message the message
     */
    public UnknownException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Unknown exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
