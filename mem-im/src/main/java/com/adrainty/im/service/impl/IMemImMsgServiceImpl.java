package com.adrainty.im.service.impl;

import com.adrainty.im.mapper.MemImMsgMapper;
import com.adrainty.im.service.IMemImMsgService;
import com.adrainty.module.im.MemImMessage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:56
 */

@Service
public class IMemImMsgServiceImpl extends ServiceImpl<MemImMsgMapper, MemImMessage> implements IMemImMsgService {
}
