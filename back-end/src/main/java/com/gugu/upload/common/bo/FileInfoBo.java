package com.gugu.upload.common.bo;

import com.gugu.upload.utils.StatusUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The type File info bo.
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class FileInfoBo {
    private String fileHash;
    private String filePath;
    private String fileOriginal;
    private String fileSize;
    private Integer status;
    private String uploader;

    /**
     * Set status.
     *
     * @param status the status
     */
    public void setStatus(StatusUtil.Status status){
        this.status = status.getCode();
    }

    /**
     * Set status.
     *
     * @param status the status
     */
    public void setStatus(Integer status){
        this.status = status;
    }
}
