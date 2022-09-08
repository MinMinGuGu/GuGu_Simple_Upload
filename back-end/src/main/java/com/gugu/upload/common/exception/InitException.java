package com.gugu.upload.common.exception;

/**
 * The type Init exception.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class InitException extends RuntimeException{
    private static final long serialVersionUID = -5925907222526038857L;

    /**
     * Instantiates a new Init exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InitException(String message, Throwable cause) {
        super(message, cause);
    }

}
