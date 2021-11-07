package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.entity.FileInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * The interface File service.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public interface IFileService extends IService<FileInfo> {
    /**
     * Download file by id boolean.
     *
     * @param id       the id
     * @param response the response
     */
    void downloadFileById(Integer id, HttpServletResponse response);
}
