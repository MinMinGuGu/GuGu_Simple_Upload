package com.gugu.upload.common.bo;

import lombok.Data;

/**
 * The type App key bo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Data
public class AppKeyBo {
    private String userName;
    private String description;
    private Integer currPage;
    private Integer pageSize;
}
