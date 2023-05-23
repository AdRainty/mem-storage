package com.adrainty.gateway.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.adrainty.gateway.service.ISysLoginLogService;
import com.adrainty.gateway.service.ISysUserService;
import com.adrainty.common.utils.ValidatorUtils;
import com.adrainty.common.exception.MemException;
import com.adrainty.common.response.R;
import com.adrainty.gateway.utils.SpringSecurityUtil;
import com.adrainty.module.form.LoginForm;
import com.adrainty.module.group.AddGroup;
import com.adrainty.module.sys.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
@RestController
@RequestMapping("/api/auth")
@Api(tags = "注册登录模块")
public class SysLoginController {

    private final ISysUserService iSysUserService;

    private final ISysLoginLogService iSysLoginLogService;

    @Autowired
    public SysLoginController(ISysUserService iSysUserService, ISysLoginLogService iSysLoginLogService) {
        this.iSysUserService = iSysUserService;
        this.iSysLoginLogService = iSysLoginLogService;
    }

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        SysUser sysUser = iSysUserService.confirmForm(loginForm);
        if (sysUser == null) {
            return R.error("用户名或密码错误");
        }
        List<String> authorities = iSysUserService.searchPermission(sysUser.getId());

        try {
            iSysLoginLogService.saveLoginLog(username);
        } catch (IOException e) {
            return R.error("用户名或密码错误");
        }
        SpringSecurityUtil.login(username, password, authorities);
        return R.ok();
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("logout")
    public R logout() {
        SpringSecurityUtil.logout();
        return R.ok();
    }

    @ApiOperation(value = "注册")
    @PostMapping("registry")
    public R registry(@RequestBody SysUser sysUser) {
        ValidatorUtils.validateEntity(sysUser, AddGroup.class);
        sysUser.setSalt(RandomStringUtils.randomAlphanumeric(20));
        sysUser.setPassword(DigestUtil.sha256Hex(sysUser.getPassword(), sysUser.getSalt()));
        iSysUserService.save(sysUser);
        try {
            iSysLoginLogService.saveLoginLog(sysUser.getUsername());
        } catch (IOException e) {
            throw new MemException("获取所属地区失败");
        }
        SpringSecurityUtil.login(sysUser.getUsername(), sysUser.getPassword(), new ArrayList<>());
        return R.ok();
    }

}
