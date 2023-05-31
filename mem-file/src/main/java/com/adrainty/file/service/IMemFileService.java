package com.adrainty.file.service;

import com.adrainty.module.file.MemFile;
import com.adrainty.module.file.MemFileVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
public interface IMemFileService extends IService<MemFile> {

    /**
     * 列出文件列表
     * @param userId 用户Id
     * @param path 当前路径
     * @return 文件列表
     */
    List<MemFile> listFiles(Long userId, String path, String name, String updateTime, String size);

    /**
     * 上传文件
     * @param userId 用户Id
     * @param path 当前路径
     * @param file 文件
     * @return 是否上传成功
     */
    boolean uploadFile(Long userId, String path, MultipartFile file);

    /**
     * 下载微模块
     * @param fileId 文件ID
     * @return 文件流
     */
    MemFileVo downloadFile(Long fileId);

    /**
     * 新建文件夹
     * @param userId 用户ID
     * @param folderName 文件夹名称
     * @param path 当前路径
     */
    void createNewFolder(Long userId, String folderName, String path);

    /**
     * 删除文件
     * @param userId 用户ID
     * @param fileId 文件ID
     */
    void deleteFile(Long userId, Long fileId);

    /**
     * 根据关键词查找文件
     * @param userId 用户Id
     * @param keyword 关键词
     * @return 文件列表
     */
    List<MemFile> searchKeyWord(Long userId, String keyword);
}
