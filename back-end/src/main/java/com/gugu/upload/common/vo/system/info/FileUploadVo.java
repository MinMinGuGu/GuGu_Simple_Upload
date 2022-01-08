package com.gugu.upload.common.vo.system.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type File upload vo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/09
 * @since 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadVo {
    private Integer systemFileNum;
    private Integer userFileUploadNum;
}
