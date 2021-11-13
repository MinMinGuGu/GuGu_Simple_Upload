package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.common.query.ISupportQuery;
import com.gugu.upload.common.vo.FileInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * Gets file info list.
     *
     * @param iSupportQuery the support query
     * @return the file info list
     */
    List<FileInfoVo> getFileInfoList(ISupportQuery<FileInfo> iSupportQuery);

    /**
     * Upload save file info vo.
     *
     * @param fileInfoBo the file info bo
     * @return the file info vo
     */
    FileInfoVo uploadSave(FileInfoBo fileInfoBo);
}
