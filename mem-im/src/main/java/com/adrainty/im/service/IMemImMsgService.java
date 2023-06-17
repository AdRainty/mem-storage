package com.adrainty.im.service;

import com.adrainty.module.im.MemImMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    Long getSingleMsgCount(Long userId, Long id);

    /**
     * 获取群聊未读消息数
     * @param id 群ID
     * @return 消息数量
     */
    Long getGroupMsgCount(Long id);

    /**
     * 获取历史消息
     * @param sender 发送人
     * @param receiver 接收人
     * @param chatType 聊天类型
     * @return 历史消息列表
     */
    List<MemImMessage> getHistoryMessage(Long sender, Long receiver, Integer chatType);

    /**
     * 阅读消息
     * @param userId 当前用户
     * @param to 对方用户
     * @param chatType 聊天类型
     */
    void readMessage(Long userId, Long to, Integer chatType);
}
