package com.adrainty.im.service.impl;

import com.adrainty.im.constants.ChatTypeEnum;
import com.adrainty.im.constants.ReadTypeEnum;
import com.adrainty.im.mapper.MemImMsgMapper;
import com.adrainty.im.service.IMemImMsgService;
import com.adrainty.module.im.MemImMessage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:56
 */

@Service
public class IMemImMsgServiceImpl extends ServiceImpl<MemImMsgMapper, MemImMessage> implements IMemImMsgService {

    @Override
    public Long getSingleMsgCount(Long id) {
        LambdaQueryWrapper<MemImMessage> wrapper = new LambdaQueryWrapper<>();
        // 找单聊未读消息 1. 接收人为id, 2. 消息为未读, 3. 聊天类型为单聊
        wrapper.eq(MemImMessage::getReceiver, id)
                .eq(MemImMessage::getChatType, ChatTypeEnum.SINGLE_CHAT.getCode())
                .eq(MemImMessage::getIsRead, ReadTypeEnum.WAITING.getCode());
        return this.baseMapper.selectCount(wrapper);
    }

    @Override
    public Long getGroupMsgCount(Long id) {
        return 0L;
    }
}
