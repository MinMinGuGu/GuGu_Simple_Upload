package com.gugu.upload.controller.advice;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.exception.OperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 出乎意料的异常
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /03/12
 * @since 1.8
 */
@Slf4j
@RestControllerAdvice
public class SurprisedExceptionAdvice {
    private static final String DEFAULT_MESSAGE = "要获取详细错误,请查看系统日志";

    /**
     * Handler exception result.
     *
     * @param exception the exception
     * @return the result
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handlerException(Exception exception) {
        log.error("出现意料之外的异常", exception);
        return Result.fastFail(DEFAULT_MESSAGE);
    }

    /**
     * Handler operation exception result.
     *
     * @param operationException the operation exception
     * @return the result
     */
    @ExceptionHandler(OperationException.class)
    public Result<?> handlerOperationException(OperationException operationException) {
        log.error("出现操作异常", operationException);
        return Result.fastFail("出现操作异常: " + operationException.getMessage());
    }
}
