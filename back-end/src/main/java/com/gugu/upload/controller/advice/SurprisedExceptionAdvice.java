package com.gugu.upload.controller.advice;

import com.gugu.upload.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 出乎意料的异常
 *
 * @author minmin
 * @date 2022/03/12
 */
@Slf4j
@RestControllerAdvice
public class SurprisedExceptionAdvice {
    private static final String DEFAULT_MESSAGE = "要获取详细错误,请查看系统日志";

    /**
     * Handler exception result.
     *
     * @return the result
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handlerException(Exception exception) {
        log.error("出现意料之外的异常", exception);
        return Result.fastFail(DEFAULT_MESSAGE);
    }
}
