package com.gugu.upload.common.exception;

/**
 * 权限异常
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
public class PermissionException extends RuntimeException {
    private static final long serialVersionUID = 808391991416951958L;

    /**
     * Instantiates a new Permission exception.
     */
    public PermissionException() {
    }

    /**
     * Instantiates a new Permission exception.
     *
     * @param message the message
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Permission exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Permission exception.
     *
     * @param cause the cause
     */
    public PermissionException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Permission exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
