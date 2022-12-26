package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.bo.OperationLogBo;
import com.gugu.upload.common.entity.OperationLog;

import javax.servlet.http.HttpServletResponse;

/**
 * The interface Visit service.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/24
 * @since 1.8
 */
public interface IOperationLogService extends IService<OperationLog> {
    /**
     * 记录操作日志
     *
     * @param operationName 操作类型
     * @param context       内容
     */
    void recordLog(OperationLog.OperationName operationName, String context);

    /**
     * Select by page page.
     *
     * @param operationLogBo the operation log bo
     * @return the page
     */
    Page<OperationLog> selectByPage(OperationLogBo operationLogBo);

    /**
     * Clear log.
     */
    void clearLog();

    /**
     * Export 2 http response.
     *
     * @param httpServletResponse the http servlet response
     */
    void export2HttpResponse(HttpServletResponse httpServletResponse);
}
