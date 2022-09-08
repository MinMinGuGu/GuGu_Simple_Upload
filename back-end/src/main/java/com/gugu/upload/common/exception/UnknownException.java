package com.gugu.upload.common.exception;

/**
 * The type Unknown exception.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class UnknownException extends RuntimeException {
    private static final long serialVersionUID = 296587398142588689L;

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
