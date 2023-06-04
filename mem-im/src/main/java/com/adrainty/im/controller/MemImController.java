package com.adrainty.im.controller;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.im.service.IMemImFriendService;
import com.adrainty.im.service.IMemImGroupService;
import com.adrainty.module.im.MemImGroup;
import com.adrainty.module.sys.SysUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemImController {

    private final IMemImGroupService iMemImGroupService;

    private final IMemImFriendService iMemImFriendService;

    @Autowired
    public MemImController(IMemImGroupService iMemImGroupService, IMemImFriendService iMemImFriendService) {
        this.iMemImGroupService = iMemImGroupService;
        this.iMemImFriendService = iMemImFriendService;
    }

    @ApiOperation(value = "查询对话")
    @GetMapping("/getAllTalk")
    public R getAllTalk(@RequestHeader("token") String token) {

        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        List<MemImGroup> memImGroups = iMemImGroupService.searchGroupTalk(userId);
        List<SysUserDto> sysUserDtos = iMemImFriendService.searchFriend(userId);
        log.debug(String.valueOf(memImGroups));
        log.debug(String.valueOf(sysUserDtos));
        return R.ok();
    }

}
