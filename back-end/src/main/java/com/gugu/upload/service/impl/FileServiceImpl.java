package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.mapper.IFileInfoMapper;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.utils.HttpHelper;
import com.gugu.upload.utils.StreamHelper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * The type File service.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Service
public class FileServiceImpl extends ServiceImpl<IFileInfoMapper, FileInfo> implements IFileService {
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

    private void processStream(BufferedInputStream bufferedInputStream, HttpServletResponse response) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
        byte[] bytes = new byte[10240];
        while (bufferedInputStream.read(bytes) != -1) {
            bufferedOutputStream.write(bytes);
        }
        bufferedOutputStream.flush();
    }

    private BufferedInputStream getFileStream(FileInfo fileInfo) throws FileNotFoundException {
        File file = Paths.get(fileInfo.getFilePath()).toFile();
        return new BufferedInputStream(new FileInputStream(file));
    }
}
