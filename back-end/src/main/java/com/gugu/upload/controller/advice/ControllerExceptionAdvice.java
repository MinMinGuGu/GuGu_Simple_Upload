package com.gugu.upload.controller.advice;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller层抛出的异常
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /03/12
 * @since 1.8
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {
    /**
     * Handler service exception result.
     *
     * @param controllerException the controller exception
     * @return the result
     */
    @ExceptionHandler(ControllerException.class)
    public Result<?> handlerServiceException(ControllerException controllerException) {
        log.error("Controller层抛出异常", controllerException);
        return Result.fastFail(controllerException.getMessage());
    }
}
