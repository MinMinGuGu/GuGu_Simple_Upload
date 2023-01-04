package com.gugu.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.common.vo.FileInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
     * @param fileInfoBo the file info bo
     * @return the file info list
     */
    List<FileInfoVo> getFileInfoList(FileInfoBo fileInfoBo);

    /**
     * Upload save file info vo.
     *
     * @param fileInfoBo the file info bo
     * @return the file info vo
     */
    FileInfoVo uploadSave(FileInfoBo fileInfoBo);

    /**
     * Gets all file number.
     *
     * @return the all file number
     */
    Integer getAllFileCount();

    /**
     * Gets file count by account id.
     *
     * @param accountId the account id
     * @return the file count by account id
     */
    Integer getFileCountByAccountId(Integer accountId);

    /**
     * Gets week file upload data.
     *
     * @return the week file upload data
     */
    List<Map<String, Object>> getWeekFileUploadData();

    /**
     * 删除文件的同时返回原数据
     *
     * @param id id
     * @return 已删除的数据 file info
     */
    FileInfo deleteFileReturnEntity(Integer id);

    /**
     * Init bo file info bo.
     *
     * @param multipartFile the multipart file
     * @param request       the request
     * @return the file info bo
     */
    FileInfoBo initBo(MultipartFile multipartFile, HttpServletRequest request);
}
