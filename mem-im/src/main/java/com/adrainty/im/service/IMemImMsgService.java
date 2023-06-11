package com.adrainty.im.service;

import com.adrainty.module.im.MemImMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:55
 */
public interface IMemImMsgService extends IService<MemImMessage> {
    /**
     * 获取单聊未读消息数
     * @param id 发送人
     * @return 消息数量
     */
    Long getSingleMsgCount(Long id);

    /**
     * 获取群聊未读消息数
     * @param id 群ID
     * @return 消息数量
     */
    Long getGroupMsgCount(Long id);
}
