package com.gugu.upload.common.exception;

/**
 * 表示Controller层异常
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /03/12
 * @since 1.8
 */
public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = -6894202851319627867L;

    /**
     * Instantiates a new Controller exception.
     */
    public ControllerException() {
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param message the message
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param cause the cause
     */
    public ControllerException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
