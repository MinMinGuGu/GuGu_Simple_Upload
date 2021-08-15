package com.gugu.upload.common.vo;

import com.gugu.upload.utils.StatusUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The type Upload file vo.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class FileInfoVo {
    private String filePath;
    private String fileOriginal;
    private String fileSize;
    private String status;
    private String uploader;

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        if (StatusUtil.Status.FAIL.getCode().equals(status)) {
            this.status = "fail";
        } else {
            this.status = "success";
        }
    }
}
