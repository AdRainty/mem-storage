package com.adrainty.file.service.impl;

import com.adrainty.common.constants.BizDataConstant;
import com.adrainty.common.constants.BizErrorConstant;
import com.adrainty.common.exception.MemException;
import com.adrainty.common.response.R;
import com.adrainty.file.constant.FileTypeEnum;
import com.adrainty.file.feign.UserClient;
import com.adrainty.file.mapper.MemFileMapper;
import com.adrainty.file.service.IMemFileService;
import com.adrainty.file.service.IMemFileShareService;
import com.adrainty.file.utils.MinioUtils;
import com.adrainty.module.base.BaseEntity;
import com.adrainty.module.file.MemFile;
import com.adrainty.module.file.MemFileVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
@RequiredArgsConstructor
public class MemFileServiceImpl extends ServiceImpl<MemFileMapper, MemFile> implements IMemFileService {

    private final MinioUtils minioUtils;

    private final UserClient userClient;

    private final IMemFileShareService iMemFileShareService;

    @Override
    public List<MemFile> listFiles(Long userId, String path, String name, String updateTime, String size) {
        path = replacePath(userId, path);
        LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
        List<MemFile> memFiles = this.baseMapper.selectList(wrapper.eq(MemFile::getBelongUser, userId).eq(MemFile::getPath, path));
        if (BizDataConstant.ASCENDING.equals(name)) {
            memFiles = memFiles.stream().sorted(Comparator.comparing(MemFile::getName)).toList();
        } else if (BizDataConstant.DESCENDING.equals(name)) {
            memFiles = memFiles.stream().sorted((o1, o2) ->  o2.getName().compareTo(o1.getName())).toList();
        }

        if (BizDataConstant.ASCENDING.equals(size)) {
            memFiles = memFiles.stream().sorted((o1, o2) ->  {
                if (FileTypeEnum.FOLDER.getCode().equals(o1.getType())) return 1;
                if (FileTypeEnum.FOLDER.getCode().equals(o2.getType())) return -1;
                return o1.getSize().compareTo(o2.getSize());
            }).toList();
        } else if (BizDataConstant.DESCENDING.equals(size)) {
            memFiles = memFiles.stream().sorted((o1, o2) ->  {
                if (FileTypeEnum.FOLDER.getCode().equals(o1.getType())) return -1;
                if (FileTypeEnum.FOLDER.getCode().equals(o2.getType())) return 1;
                return o2.getSize().compareTo(o1.getSize());
            }).toList();
        }

        if (BizDataConstant.ASCENDING.equals(updateTime)) {
            memFiles = memFiles.stream().sorted(Comparator.comparing(BaseEntity::getUpdateTime)).toList();
        } else if (BizDataConstant.DESCENDING.equals(updateTime)) {
            memFiles = memFiles.stream().sorted((o1, o2) ->  o2.getUpdateTime().compareTo(o1.getUpdateTime())).toList();
        }
        return memFiles;
    }

    @Override
    public boolean uploadFile(Long userId, String path, MultipartFile file) {
        path = replacePath(userId, path);

        String names = minioUtils.upload(path, file);
        if (!StringUtils.hasLength(names)) return false;
        MemFile memFile = new MemFile();
        memFile.setName(file.getOriginalFilename());
        memFile.setRealName(names);
        memFile.setSize(file.getSize());
        memFile.setPath(path);
        memFile.setType(getType(file.getOriginalFilename()));
        memFile.setBelongUser(userId);

        this.save(memFile);
        return true;
    }

    @Override
    public MemFileVo downloadFile(Long fileId) {
        MemFile memFile = this.baseMapper.selectById(fileId);
        byte[] stream = minioUtils.download(memFile.getPath() + "/" + memFile.getRealName());
        MemFileVo memFileVo = new MemFileVo();
        memFileVo.setFileName(memFile.getName());
        memFileVo.setStream(stream);
        return memFileVo;
    }

    @Override
    public void createNewFolder(Long userId, String folderName, String path) {
        path = replacePath(userId, path);
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

    private String replacePath(Long userId, String path) {
        R r = userClient.getUsernameByUserId(userId);
        if (r.getCode() != 0) throw new MemException("系统异常");
        String name = (String) r.get(BizDataConstant.USER_NAME);
        return path.replace(BizDataConstant.HOME_PATH, name);
    }

    @Override
    public void deleteFile(Long userId, Long fileId) {
        MemFile memFile = this.baseMapper.selectById(fileId);

        if (!userId.equals(memFile.getBelongUser())) {
            throw new MemException(BizErrorConstant.DELETE_FAILED);
        }

        LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
        List<MemFile> memFiles = this.baseMapper.selectList(wrapper.eq(MemFile::getBelongUser, userId));
        List<String> deleted = new ArrayList<>();

        if (memFile.getType().equals(FileTypeEnum.FOLDER.getCode())) {
            List<MemFile> ids = new ArrayList<>();
            ids.add(memFile);
            getChildren(ids, 0, memFiles);
            ids.stream().filter(item -> !FileTypeEnum.FOLDER.getCode().equals(item.getType()))
                    .forEach(item -> deleted.add(item.getPath() + "/" + item.getRealName()));
            minioUtils.removeObjects(deleted);
            this.baseMapper.deleteBatchIds(ids.stream().map(MemFile::getId).toList());
        } else {
            deleted.add(memFile.getPath() + "/" + memFile.getRealName());
            this.baseMapper.deleteById(fileId);
        }

    }

    @Override
    public List<MemFile> searchKeyWord(Long userId, String keyword) {
        LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
        List<MemFile> memFiles = this.baseMapper.selectList(wrapper.eq(MemFile::getBelongUser, userId));
        return memFiles.stream().filter(item -> item.getName().contains(keyword)).toList();
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

    @Override
    public MemFile getFile(Long userId, String id) {
        LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
        MemFile memFile = this.baseMapper.selectOne(wrapper.eq(MemFile::getId, Long.parseLong(id)));
        if (userId.equals(memFile.getBelongUser())) return memFile;
        return iMemFileShareService.isShare(Long.parseLong(id), userId, "")? memFile: null;
    }

    @Override
    public boolean saveFile(Long userId, Long fileId, String path, String code) {
        if (iMemFileShareService.isShare(fileId, userId, code)) {
            LambdaQueryWrapper<MemFile> wrapper = new LambdaQueryWrapper<>();
            MemFile memFile = this.baseMapper.selectOne(wrapper.eq(MemFile::getId, fileId));
            memFile.setBelongUser(userId);
            memFile.setPath(replacePath(userId, path));
            memFile.setId(null);
            memFile.setCreateTime(null);
            memFile.setUpdateTime(null);
            this.baseMapper.insert(memFile);
            return true;
        }
        return false;

    }
}
