package com.gugu.upload.common.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type App key vo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Data
public class AppKeyVo {
    private String userName;
    private String value;
    private String description;
    private LocalDateTime createTime;
}
