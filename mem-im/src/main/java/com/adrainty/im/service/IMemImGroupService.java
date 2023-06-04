package com.adrainty.im.service;

import com.adrainty.module.im.MemImGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:55
 */
public interface IMemImGroupService extends IService<MemImGroup> {

    /**
     * 查询用户的讨论组
     * @param userId 用户ID
     */
    List<MemImGroup> searchGroupTalk(Long userId);

    /**
     * 创建讨论组
     * @param userId 用户ID
     * @param memImGroup 用户组对象
     */
    void addGroup(Long userId, MemImGroup memImGroup);
}
