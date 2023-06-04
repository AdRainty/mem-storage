package com.adrainty.user.controller;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.constants.BizDataConstant;
import com.adrainty.common.constants.BizErrorConstant;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.module.form.LoginForm;
import com.adrainty.module.sys.SysUser;
import com.adrainty.module.sys.SysUserDto;
import com.adrainty.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/24 22:44
 */

@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class SysUserController {

    private final ISysUserService iSysUserService;

    @Autowired
    public SysUserController(ISysUserService iSysUserService){
        this.iSysUserService = iSysUserService;
    }

    @ApiOperation(value = "登录表单校验")
    @PostMapping("/confirmLogin")
    public R confirmLogin(@RequestBody LoginForm loginForm) {
        SysUser sysUser = iSysUserService.confirmForm(loginForm);
        if (sysUser != null) {
            SysUserDto sysUserDto = new SysUserDto();
            BeanUtils.copyProperties(sysUser, sysUserDto);
            return R.ok().put(BizDataConstant.USER, sysUserDto);
        }
        return R.error(BizErrorConstant.LOGIN_ERROR_MSG);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("/saveUser")
    public R saveUser(@RequestBody SysUser sysUser){
        iSysUserService.save(sysUser);
        return R.ok().put(BizDataConstant.USER_ID, sysUser.getId());
    }

    @ApiOperation(value = "根据用户id查找用户名")
    @GetMapping("/getUsernameByUserId")
    public R getUsernameByUserId(@RequestParam("id") Long id) {
        SysUser select = iSysUserService.getById(id);
        if (select == null) return R.error();
        return R.ok().put(BizDataConstant.USER_NAME, select.getUsername());
    }

    @ApiOperation(value = "查询当前用户详细信息")
    @PostMapping("/userInfoBatch")
    public List<SysUserDto> userInfoBatch(@RequestBody List<Long> ids) {
        List<SysUser> sysUsers = iSysUserService.listByIds(ids);
        List<SysUserDto> sysUserDtos = new ArrayList<>();
        sysUsers.forEach(sysUser -> {
            SysUserDto sysUserDto = new SysUserDto();
            BeanUtils.copyProperties(sysUser, sysUserDto);
            sysUserDtos.add(sysUserDto);
        });
        return sysUserDtos;
    }

    @ApiOperation(value = "查询当前用户详细信息")
    @GetMapping("/userInfo")
    public R userInfo(@RequestHeader("token") String token){
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);

        SysUser user = iSysUserService.getById(userId);
        SysUserDto sysUserDto = new SysUserDto();
        BeanUtils.copyProperties(user, sysUserDto);
        return R.ok().put("user", sysUserDto);
    }

}
