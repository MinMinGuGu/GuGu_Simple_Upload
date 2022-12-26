package com.gugu.upload.controller.system;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.annotation.PermissionCheck;
import com.gugu.upload.common.bo.OperationLogBo;
import com.gugu.upload.service.IOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Operation log controller.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/23
 * @since 1.8
 */
@Api("操作日志相关")
@RestController
@RequestMapping("/system/operationLog")
@PermissionCheck
public class OperationLogController {

    @Resource
    private IOperationLogService operationLogService;

    /**
     * Get operation log result.
     *
     * @param operationLogBo the operation log bo
     * @return the result
     */
    @GetMapping
    @ApiOperation("分页查询操作日志")
    public Result<?> getOperationLog(OperationLogBo operationLogBo) {
        return Result.fastSuccess(operationLogService.selectByPage(operationLogBo));
    }

    /**
     * Export log.
     *
     * @param httpServletResponse the http servlet response
     */
    @GetMapping("/export")
    @ApiOperation("导出日志")
    public void exportLog(HttpServletResponse httpServletResponse) {
        operationLogService.export2HttpResponse(httpServletResponse);
    }

    /**
     * Clear operation log result.
     *
     * @return the result
     */
    @DeleteMapping
    @ApiOperation("清空操作日志")
    public Result<?> clearOperationLog() {
        operationLogService.clearLog();
        return Result.fastSuccess();
    }
}
