package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.bo.FileInfoBo;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.common.exception.UnknownException;
import com.gugu.upload.common.vo.FileInfoVo;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.controller.helper.HttpHelper;
import com.gugu.upload.mapper.IFileInfoMapper;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.service.ILoginService;
import com.gugu.upload.utils.DateUtil;
import com.gugu.upload.utils.FileSizeUtil;
import com.gugu.upload.utils.FileUtil;
import com.gugu.upload.utils.IpUtil;
import com.gugu.upload.utils.StreamHelper;
import com.gugu.upload.utils.TransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type File service.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<IFileInfoMapper, FileInfo> implements IFileService {

    @Resource
    private IAccountService accountService;

    @Resource
    private ApplicationConfig applicationConfig;

    @Resource
    private ILoginService loginService;

    @Override
    public void downloadFileById(Integer id, HttpServletResponse response) {
        FileInfo fileInfo = this.getById(id);
        if (fileInfo == null) {
            log.error("The file to be downloaded cannot be found in the database according to the ID");
            return;
        }
        HttpHelper.settingFileStreamHeader(response, fileInfo.getFileOriginal());
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = getFileStream(fileInfo);
        } catch (FileNotFoundException e) {
            log.error("The file data was successfully obtained in the database, but the conversion of the local file to stream failed", e);
            return;
        }
        try {
            processStream(bufferedInputStream, response);
        } catch (IOException e) {
            log.error("Failed to write file stream to response stream", e);
            return;
        }
        StreamHelper.closerStream(bufferedInputStream);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            log.error("Brush in response result failed", e);
        }
    }

    @Override
    public List<FileInfoVo> getFileInfoList(FileInfoBo fileInfoBo) {
        FileInfo fileInfo = TransformUtil.transform(fileInfoBo, FileInfo.class);
        QueryWrapper<FileInfo> query = Wrappers.query(fileInfo);
        List<FileInfo> fileInfos = baseMapper.selectList(query);
        List<FileInfoVo> fileInfoVoList = new LinkedList<>();
        fileInfos.forEach(item -> {
            FileInfoVo fileInfoVo = TransformUtil.transform(item, FileInfoVo.class);
            Integer accountId = item.getAccountId();
            Account account = accountService.getById(accountId);
            fileInfoVo.setUploader(String.format("%s(%s)", account.getUserName(), item.getUploader()));
            fileInfoVoList.add(fileInfoVo);
        });
        fileInfoVoList.forEach(item -> item.setFileSize(FileSizeUtil.convertFileSize(item.getFileSize())));
        return fileInfoVoList;
    }

    @Override
    public FileInfoVo uploadSave(FileInfoBo fileInfoBo) {
        FileInfo fileInfo = TransformUtil.transform(fileInfoBo, FileInfo.class);
        this.save(fileInfo);
        log.info("Complete the database insert. file info : {}", fileInfo);
        return TransformUtil.transform(fileInfoBo, FileInfoVo.class);
    }

    @Override
    public Integer getAllFileCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public Integer getFileCountByAccountId(Integer accountId) {
        return query().eq("account_id", accountId).count();
    }

    @Override
    public List<Map<String, Object>> getWeekFileUploadData() {
        Calendar weekStart = Calendar.getInstance();
        weekStart.add(Calendar.DAY_OF_YEAR, -7);
        Date weekStartTime = weekStart.getTime();
        String dateField = "create_time";
        String dateFormat = "yyyy-MM-dd";
        QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();
        wrapper.select("count(*) as fileUploadCount, create_time as createTime")
                .groupBy(dateField)
                .between(dateField, DateUtil.getStringByFormat(weekStartTime, dateFormat), DateUtil.getStringByFormat(new Date(), dateFormat))
                .orderByAsc("createTime");
        return this.listMaps(wrapper);
    }

    @Override
    public FileInfo deleteFileReturnEntity(Integer id) {
        FileInfo fileInfo = this.getById(id);
        if (Objects.isNull(fileInfo)) {
            return null;
        }
        this.removeById(id);
        return fileInfo;
    }

    @Override
    public FileInfoBo initBo(MultipartFile multipartFile, HttpServletRequest request) {
        FileInfoBo fileInfoBo = new FileInfoBo();
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new UnknownException("Received file name exception");
            }
            String fileSuffix = FileUtil.getFileSuffix(originalFilename);
            Account currentAccount = loginService.getCurrentAccount(request);
            fileInfoBo
                    .setFileHash(FileUtil.getFileHashCode(multipartFile.getInputStream()))
                    .setFilePath(getSavePath(FileUtil.getUniqueFileName() + fileSuffix, applicationConfig).toString())
                    .setFileOriginal(originalFilename)
                    .setUploader(getUploader(request))
                    .setFileSize(String.valueOf(multipartFile.getSize()))
                    .setAccountId(currentAccount.getId());
        } catch (IOException e) {
            log.error("Failed to initialize file Bo object", e);
            throw new UnknownException(e.getMessage(), e);
        }
        return fileInfoBo;
    }

    private void processStream(BufferedInputStream bufferedInputStream, HttpServletResponse response) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
        byte[] bytes = new byte[2048];
        while (bufferedInputStream.read(bytes) != -1) {
            bufferedOutputStream.write(bytes);
        }
        bufferedOutputStream.flush();
    }

    private BufferedInputStream getFileStream(FileInfo fileInfo) throws FileNotFoundException {
        File file = Paths.get(fileInfo.getFilePath()).toFile();
        return new BufferedInputStream(new FileInputStream(file));
    }

    private String getUploader(HttpServletRequest request) {
        return IpUtil.getIpAddress(request);
    }

    private Path getSavePath(String filePath, ApplicationConfig applicationConfig) {
        return getTmpDir(applicationConfig).resolve(filePath);
    }

    private Path getTmpDir(ApplicationConfig applicationConfig) {
        Path path = Paths.get(applicationConfig.getTmpDir());
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("Failed to create folder", e);
                throw new UnknownException("Failed to create folder", e);
            }
        }
        return path;
    }
}
