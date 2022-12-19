package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.entity.OperationLog;

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
     * @param operationType 操作类型
     * @param context       内容
     */
    void recordLog(OperationLog.OperationType operationType, String context);
}
