package com.adrainty.im.controller;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.im.service.IMemImService;
import com.adrainty.module.im.MemTalkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/4 23:46
 */

@RestController
@RequestMapping("/im")
@Api(tags = "IM")
@Slf4j
@RequiredArgsConstructor
public class MemImController {

    private final IMemImService iMemImService;

    @ApiOperation(value = "查询对话")
    @GetMapping("/getAllTalk")
    public R getAllTalk(@RequestHeader("token") String token) {

        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        List<MemTalkVo> memTalkVos = iMemImService.getTalkVo(userId);
        return R.ok().put("talk", memTalkVos);
    }

}
