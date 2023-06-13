package com.adrainty.im.consumer;

import com.adrainty.im.constants.RocketMQConstant;
import com.adrainty.im.service.IMemImMsgService;
import com.adrainty.module.im.MemImMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/14 0:01
 */

@Component
@RocketMQMessageListener(consumerGroup = RocketMQConstant.GROUP, topic = RocketMQConstant.TOPIC)
@RequiredArgsConstructor
@Slf4j
public class ListenConsumer implements RocketMQListener<MemImMessage> {

    private final IMemImMsgService iMemImMsgService;

    @Override
    public void onMessage(MemImMessage memImMessage) {
        log.info("接收到消息：{}", memImMessage);
        iMemImMsgService.save(memImMessage);
    }

}
