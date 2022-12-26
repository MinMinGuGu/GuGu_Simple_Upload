package com.gugu.upload.common.bo;

import lombok.Data;

/**
 * The type Base page.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/23
 * @since 1.8
 */
@Data
public class BasePage {
    /**
     * The Curr page.
     */
    protected Integer currPage = 1;
    /**
     * The Page size.
     */
    protected Integer pageSize = 10;
}
