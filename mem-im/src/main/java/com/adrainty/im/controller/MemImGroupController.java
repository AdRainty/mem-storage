package com.adrainty.im.controller;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.im.service.IMemImGroupService;
import com.adrainty.module.im.MemImGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:46
 */

@RestController
@RequestMapping("/im/group")
@Api(tags = "IM Group模块")
@RequiredArgsConstructor
public class MemImGroupController {

    private final IMemImGroupService iMemImGroupService;

    @ApiOperation(value = "查询讨论组")
    @GetMapping("/searchGroupTalk")
    public R searchGroupTalk(@RequestHeader("token") String token) {

        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);

        List<MemImGroup> memImGroups = iMemImGroupService.searchGroupTalk(userId);
        return R.ok().put("groups", memImGroups);
    }

    @ApiOperation(value = "新增讨论组")
    @PostMapping("/addGroup")
    public R addGroup(@RequestHeader("token") String token, @RequestBody MemImGroup memImGroup) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        iMemImGroupService.addGroup(userId, memImGroup);
        return R.ok();
    }

}
