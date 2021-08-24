package com.gugu.upload.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gugu.upload.utils.StatusUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * The type File info.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Data
@Accessors(chain = true)
@TableName("file_info")
public class FileInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String fileHash;
    private String filePath;
    private String fileOriginal;
    private String fileSize;
    private Integer status;
    private String uploader;
    @TableLogic
    private Integer deleted;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * Is exist boolean.
     *
     * @return the boolean
     */
    public boolean isExist(){
        return StatusUtil.Status.SUCCESS.getCode().equals(status);
    }

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

    /**
     * Get status description status util . status.
     *
     * @return the status util . status
     */
    public StatusUtil.Status getStatusDescription(){
        for (StatusUtil.Status statusEnum : StatusUtil.Status.values()) {
            if (statusEnum.getCode().equals(this.status)){
                return statusEnum;
            }
        }
        return StatusUtil.Status.FAIL;
    }
}
