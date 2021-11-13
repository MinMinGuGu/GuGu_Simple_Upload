package com.gugu.upload.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gugu.upload.common.constant.EntityConstant;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.utils.TransformUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * The type Query request.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /11/08
 * @since 1.8
 */
@Data
public class FileInfoQueryRequest implements ISupportQuery<FileInfo> {

    @JsonProperty("fileName")
    private String fileOriginal;
    private String uploader;
    private Date startDate;
    private Date endDate;
    private Order order = Order.DESC;

    @Override
    public QueryWrapper<FileInfo> toQueryWrapper() {
        FileInfo fileInfo = TransformUtil.transform(this, FileInfo.class);
        QueryWrapper<FileInfo> queryWrapper = getQueryWrapper(fileInfo);
        if (StringUtils.hasText(fileInfo.getFileOriginal())) {
            queryWrapper.like("file_original", fileInfo.getFileOriginal());
        }
        if (StringUtils.hasText(fileInfo.getUploader())) {
            queryWrapper.eq("uploader", fileInfo.getUploader());
        }
        generateDateCondition(queryWrapper);
        generateOrderCondition(order, queryWrapper);
        return queryWrapper;
    }

    private void generateOrderCondition(Order order, QueryWrapper<FileInfo> queryWrapper) {
        if (order == Order.ASC) {
            queryWrapper.orderByAsc(EntityConstant.FILE_INFO_ORDER);
        } else {
            queryWrapper.orderByDesc(EntityConstant.FILE_INFO_ORDER);
        }
    }

    private void generateDateCondition(QueryWrapper<FileInfo> queryWrapper) {
        if (startDate != null && endDate != null) {
            queryWrapper.between(EntityConstant.FILE_INFO_ORDER, startDate, endDate);
            return;
        }
        if (startDate != null) {
            queryWrapper.ge(EntityConstant.FILE_INFO_ORDER, startDate);
            return;
        }
        if (endDate != null) {
            queryWrapper.le(EntityConstant.FILE_INFO_ORDER, endDate);
        }
    }

    private enum Order {
        /**
         * Asc order.
         */
        ASC,
        /**
         * Desc order.
         */
        DESC;

        /**
         * Get index int.
         *
         * @return the int
         */
        @JsonValue
        public int getIndex() {
            return ordinal();
        }
    }
}
