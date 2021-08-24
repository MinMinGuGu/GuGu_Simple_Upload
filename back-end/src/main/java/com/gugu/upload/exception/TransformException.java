package com.gugu.upload.exception;

/**
 * The type Transform exception.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class TransformException extends RuntimeException{
    /**
     * Instantiates a new Transform exception.
     *
     * @param message the message
     */
    public TransformException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Transform exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TransformException(String message, Throwable cause) {
        super(message, cause);
    }
}
