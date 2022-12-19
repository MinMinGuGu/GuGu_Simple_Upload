package com.gugu.upload.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * The type File info vo.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Data
@Accessors(chain = true)
public class FileInfoVo {
    private Integer id;
    private String fileOriginal;
    private String fileSize;
    private String uploader;
    private String fileDesc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
