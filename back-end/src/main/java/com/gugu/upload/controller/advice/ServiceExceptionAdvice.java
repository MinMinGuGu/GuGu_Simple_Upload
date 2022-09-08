package com.gugu.upload.controller.advice;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.exception.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Service层抛出的异常
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /03/12
 * @since 1.8
 */
@RestControllerAdvice
public class ServiceExceptionAdvice {
    /**
     * Handler service exception result.
     *
     * @param serviceException the service exception
     * @return the result
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> handlerServiceException(ServiceException serviceException) {
        return Result.fastFail(serviceException.getMessage());
    }
}
