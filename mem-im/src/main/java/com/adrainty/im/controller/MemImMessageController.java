package com.adrainty.im.controller;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.im.service.IMemImMsgService;
import com.adrainty.module.im.MemImMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/17 11:05
 */

@RestController
@RequestMapping("/im/message")
@Api(tags = "IM Message")
@Slf4j
@RequiredArgsConstructor
public class MemImMessageController {

    private final IMemImMsgService iMemImMsgService;

    @ApiOperation(value = "查询历史消息")
    @GetMapping("/history")
    public R history(@RequestHeader("token") String token, @RequestParam("id") String id, @RequestParam("chatType") Integer chatType) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        List<MemImMessage> memImMessages = iMemImMsgService.getHistoryMessage(userId, Long.valueOf(id), chatType);
        return R.ok().put("talk", memImMessages);
    }

    @ApiOperation(value = "阅读消息")
    @GetMapping("/readMessage")
    public R readMessage(@RequestHeader("token") String token, @RequestParam("id") String id, @RequestParam("chatType") String chatType) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        iMemImMsgService.readMessage(userId, Long.valueOf(id), Integer.parseInt(chatType));
        return R.ok();
    }

}
