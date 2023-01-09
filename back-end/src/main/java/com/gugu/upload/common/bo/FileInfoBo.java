package com.gugu.upload.common.bo;

import com.gugu.upload.utils.StatusUtil;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * The type File info bo.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
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
    private Integer accountId;
    private String searchType;
    private String value;

    /**
     * Set status.
     *
     * @param status the status
     */
    public void setStatus(StatusUtil.Status status) {
        this.status = status.getCode();
    }

    /**
     * Set status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public SearchType findSearchType() {
        for (SearchType searchType : SearchType.values()) {
            if (searchType.getValue().equals(this.searchType)) {
                return searchType;
            }
        }
        return null;
    }

    @Getter
    public enum SearchType {
        /**
         * 上传者
         */
        UPLOADER("uploader"),
        /**
         * 文件名
         */
        FILENAME("fileName");

        private final String value;

        SearchType(String value) {
            this.value = value;
        }
    }
}
