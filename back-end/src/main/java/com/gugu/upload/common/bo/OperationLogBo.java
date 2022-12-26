package com.gugu.upload.common.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Operation log bo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/23
 * @since 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogBo extends BasePage {
    private Integer id;
    private String ipAddress;
    private String operationName;
    private String userName;
    private String context;
}
