package com.adrainty.file.service.impl;

import com.adrainty.common.constants.BizErrorConstant;
import com.adrainty.common.exception.MemException;
import com.adrainty.file.constant.FileTypeEnum;
import com.adrainty.file.mapper.MemFileMapper;
import com.adrainty.file.service.IMemFileService;
import com.adrainty.file.utils.MinioUtils;
import com.adrainty.module.file.MemFile;
import com.adrainty.module.file.MemFileVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
@Service
public class MemFileServiceImpl extends ServiceImpl<MemFileMapper, MemFile> implements IMemFileService {

    private final MinioUtils minioUtils;

    @Autowired
    public MemFileServiceImpl(MinioUtils minioUtils) {
        this.minioUtils = minioUtils;
    }

    @Override
    public List<MemFile> listFiles(Long userId, String path) {
        LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
        return this.baseMapper.selectList(wrapper.eq(MemFile::getBelongUser, userId).eq(MemFile::getPath, path));
    }

    @Override
    public boolean uploadFile(Long userId, String path, MultipartFile files) {
        List<String> names = minioUtils.upload(new MultipartFile[] {files});
        if (CollectionUtils.isEmpty(names)) return false;
        MemFile memFile = new MemFile();
        memFile.setName(files.getOriginalFilename());
        memFile.setRealName(names.get(0));
        memFile.setSize(files.getSize());
        memFile.setPath(path);
        memFile.setType(getType(files.getOriginalFilename()));
        memFile.setBelongUser(userId);
        this.save(memFile);
        return true;
    }

    @Override
    public MemFileVo downloadFile(Long fileId) {
        MemFile memFile = this.baseMapper.selectById(fileId);
        byte[] stream = minioUtils.download(memFile.getRealName());
        MemFileVo memFileVo = new MemFileVo();
        memFileVo.setFileName(memFile.getName());
        memFileVo.setStream(stream);
        return memFileVo;
    }

    @Override
    public void createNewFolder(Long userId, String folderName, String path) {
        MemFile memFile = new MemFile();
        memFile.setBelongUser(userId);
        memFile.setPath(path);
        memFile.setType(FileTypeEnum.FOLDER.getCode());
        memFile.setName(folderName);
        this.baseMapper.insert(memFile);
    }

    private Integer getType(String fileName) {
        if (!StringUtils.hasText(fileName)) return FileTypeEnum.OTHER.getCode();
        String[] split = fileName.split("\\.");
        if (split.length < 2) return FileTypeEnum.OTHER.getCode();
        String type = split[split.length - 1].toLowerCase();

        String[] document = new String[] {"txt", "pdf", "rtf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "wpd"};
        String[] video = new String[] {"avi", "wmv", "mpg", "mpeg", "mov", "rm", "ram", "swf", "flv", "mp4"};
        String[] music = new String[] {"wav", "aiff", "pcm", "flac", "alac", "wma", "mp3", "ogg", "acc"};
        String[] zipped = new String[] {"zip", "7z", "rar", "tar", "cab", "bzip2", "tar.gz"};
        String[] picture = new String[] {"jpeg", "psd", "gif", "tif", "png", "svg", "ico", "raw", "eps", "bmp"};

        if (Arrays.asList(document).contains(type)) {
            return FileTypeEnum.DOCUMENT.getCode();
        } else if (Arrays.asList(video).contains(type)) {
            return FileTypeEnum.VIDEO.getCode();
        } else if (Arrays.asList(music).contains(type)) {
            return FileTypeEnum.MUSIC.getCode();
        } else if (Arrays.asList(zipped).contains(type)) {
            return FileTypeEnum.ZIPPED.getCode();
        } else if (Arrays.asList(picture).contains(type)) {
            return FileTypeEnum.PICTURE.getCode();
        } else return FileTypeEnum.OTHER.getCode();
    }

    @Override
    public void deleteFile(Long userId, Long fileId) {
        MemFile memFile = this.baseMapper.selectById(fileId);

        if (!userId.equals(memFile.getBelongUser())) {
            throw new MemException(BizErrorConstant.DELETE_FAILED);
        }

        LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
        List<MemFile> memFiles = this.baseMapper.selectList(wrapper.eq(MemFile::getBelongUser, userId));

        if (memFile.getType().equals(FileTypeEnum.FOLDER.getCode())) {
            List<MemFile> ids = new ArrayList<>();
            ids.add(memFile);
            getChildren(ids, 0, memFiles);
            this.baseMapper.deleteBatchIds(ids.stream().map(MemFile::getId).toList());
        } else {
            this.baseMapper.deleteById(fileId);
        }

    }

    public void getChildren(List<MemFile> ids, int ind, List<MemFile> all) {
        int size = ids.size();
        if (size == ind) return;
        for (int i = ind; i < size; i++) {
            MemFile memFile = ids.get(i);
            if (memFile.getType().equals(FileTypeEnum.FOLDER.getCode())) {
                String fullPath = memFile.getPath() + "/" + memFile.getName();
                List<MemFile> collect = all.stream().filter(item -> item.getPath().equals(fullPath)).toList();
                ids.addAll(collect);
            } else {
                ids.add(memFile);
            }
        }
        getChildren(ids, size, all);
    }
}
