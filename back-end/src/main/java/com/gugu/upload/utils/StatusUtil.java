package com.gugu.upload.utils;

import lombok.Getter;

/**
 * The type Status util.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class StatusUtil {
    private StatusUtil(){}

    /**
     * Get status status.
     *
     * @param integer the integer
     * @return the status
     */
    public static Status getStatus(Integer integer){
        return integer == 1 ? Status.SUCCESS : Status.FAIL;
    }

    /**
     * Get status code integer.
     *
     * @param status the status
     * @return the integer
     */
    public static Integer getStatusCode(Status status){
        return status.getCode();
    }

    /**
     * The enum Status.
     *
     * @author minmin
     * @version 1.0
     * @since 1.8
     */
    @Getter
    public enum Status{
        /**
         * Success status.
         */
        SUCCESS(1),
        /**
         * Fail status.
         */
        FAIL(0);

        private final Integer code;

        Status(Integer code) {
            this.code = code;
        }
    }
}
