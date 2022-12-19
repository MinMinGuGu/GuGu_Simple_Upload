package com.gugu.upload.common.exception;

/**
 * 业务操作异常 如横纵向越权
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
public class OperationException extends RuntimeException {
    private static final long serialVersionUID = 2335399114574306981L;

    /**
     * Instantiates a new Operation exception.
     */
    public OperationException() {
    }

    /**
     * Instantiates a new Operation exception.
     *
     * @param message the message
     */
    public OperationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Operation exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Operation exception.
     *
     * @param cause the cause
     */
    public OperationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Operation exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public OperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
