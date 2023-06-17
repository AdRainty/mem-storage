package com.adrainty.im.service.impl;

import com.adrainty.im.constants.ChatTypeEnum;
import com.adrainty.im.constants.ReadTypeEnum;
import com.adrainty.im.mapper.MemImMsgMapper;
import com.adrainty.im.service.IMemImMsgService;
import com.adrainty.module.im.MemImMessage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:56
 */

@Service
public class IMemImMsgServiceImpl extends ServiceImpl<MemImMsgMapper, MemImMessage> implements IMemImMsgService {

    @Override
    public Long getSingleMsgCount(Long userId, Long id) {
        LambdaQueryWrapper<MemImMessage> wrapper = new LambdaQueryWrapper<>();
        // 找单聊未读消息 1. 接收人为id, 2. 消息为未读, 3. 聊天类型为单聊
        wrapper.eq(MemImMessage::getSender, id)
                .eq(MemImMessage::getReceiver, userId)
                .eq(MemImMessage::getChatType, ChatTypeEnum.SINGLE_CHAT.getCode())
                .eq(MemImMessage::getIsRead, ReadTypeEnum.WAITING.getCode());
        return this.baseMapper.selectCount(wrapper);
    }

    @Override
    public Long getGroupMsgCount(Long id) {
        return 0L;
    }

    @Override
    public List<MemImMessage> getHistoryMessage(Long sender, Long receiver, Integer chatType) {
        LambdaQueryWrapper<MemImMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemImMessage::getSender, sender).eq(MemImMessage::getReceiver, receiver).eq(MemImMessage::getChatType, chatType)
                .or()
                .eq(MemImMessage::getSender, receiver).eq(MemImMessage::getReceiver, sender).eq(MemImMessage::getChatType, chatType);
        List<MemImMessage> memImMessages = this.baseMapper.selectList(wrapper);
        memImMessages.sort(Comparator.comparing(MemImMessage::getCreateTime));
        return memImMessages;
    }

    @Override
    public void readMessage(Long userId, Long to, Integer chatType) {
        LambdaQueryWrapper<MemImMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemImMessage::getSender, to).eq(MemImMessage::getReceiver, userId).eq(MemImMessage::getChatType, chatType);
        MemImMessage imMessage = new MemImMessage();
        imMessage.setIsRead(ReadTypeEnum.READ.getCode());
        this.baseMapper.update(imMessage, wrapper);
    }
}
