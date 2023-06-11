package com.adrainty.im.service;

import com.adrainty.module.im.MemTalkVo;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/11 17:45
 */
public interface IMemImService {

    /**
     * 获取用户所有会话
     * @param userId 用户ID
     * @return 会话Vo
     */
    List<MemTalkVo> getTalkVo(Long userId);
}
