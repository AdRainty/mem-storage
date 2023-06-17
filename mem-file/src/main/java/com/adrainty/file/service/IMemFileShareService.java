package com.adrainty.file.service;

import com.adrainty.module.file.MemFileShare;
import com.adrainty.module.form.FileShareForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/17 15:21
 */
public interface IMemFileShareService extends IService<MemFileShare> {

    /**
     * 判断用户是否拥有文件权限
     * @param id 文件ID
     * @param userId 用户ID
     * @return 是否拥有
     */
    boolean isShare(Long id, Long userId, String code);

    /**
     * 分享文件
     * @param form 表单
     */
    void shareFile(FileShareForm form);
}
