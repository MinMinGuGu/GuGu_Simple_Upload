package com.gugu.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.mapper.IFileInfoMapper;
import com.gugu.upload.service.IFileService;
import org.springframework.stereotype.Service;

/**
 * The type File service.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Service
public class FileServiceImpl extends ServiceImpl<IFileInfoMapper, FileInfo> implements IFileService {
}
