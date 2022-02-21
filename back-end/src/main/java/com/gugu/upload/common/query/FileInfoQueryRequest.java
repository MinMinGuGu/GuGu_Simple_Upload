package com.gugu.upload.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gugu.upload.common.entity.FileInfo;
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

    private static final String CREATE_TIME = "create_time";

    private static final String UPDATE_TIME = "update_time";

    @JsonProperty("fileName")
    private String fileOriginal;
    private String fileDesc;
    private String uploader;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Order order = Order.DESC;

    @Override
    public QueryWrapper<FileInfo> toQueryWrapper() {
        QueryWrapper<FileInfo> queryWrapper = getQueryWrapper();
        if (StringUtils.hasText(fileOriginal)) {
            queryWrapper.like("file_original", fileOriginal);
        }
        if (StringUtils.hasText(uploader)) {
            queryWrapper.eq("uploader", uploader);
        }
        if (StringUtils.hasText(fileDesc)){
            queryWrapper.like("file_desc", fileDesc);
        }
        generateDateCondition(queryWrapper);
        generateOrderCondition(order, queryWrapper);
        return queryWrapper;
    }

    private void generateOrderCondition(Order order, QueryWrapper<FileInfo> queryWrapper) {
        if (order == Order.ASC) {
            queryWrapper.orderByAsc(UPDATE_TIME);
        } else {
            queryWrapper.orderByDesc(UPDATE_TIME);
        }
    }

    private void generateDateCondition(QueryWrapper<FileInfo> queryWrapper) {
        if (startDate != null && endDate != null) {
            queryWrapper.between(CREATE_TIME, startDate, endDate);
            return;
        }
        if (startDate != null) {
            queryWrapper.ge(CREATE_TIME, startDate);
            return;
        }
        if (endDate != null) {
            queryWrapper.le(CREATE_TIME, endDate);
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
