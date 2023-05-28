package com.adrainty.user.controller;

import com.adrainty.common.constants.BizDataConstant;
import com.adrainty.common.constants.BizErrorConstant;
import com.adrainty.common.response.R;
import com.adrainty.module.form.LoginForm;
import com.adrainty.module.sys.SysUser;
import com.adrainty.module.sys.SysUserDto;
import com.adrainty.user.service.ISysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/saveUser")
    public R saveUser(@RequestBody SysUser sysUser){
        iSysUserService.save(sysUser);
        return R.ok().put(BizDataConstant.USER_ID, sysUser.getId());
    }

}
