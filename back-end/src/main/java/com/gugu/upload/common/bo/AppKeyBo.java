package com.gugu.upload.common.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type App key bo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppKeyBo extends BasePage {
    private String userName;
    private String description;
}