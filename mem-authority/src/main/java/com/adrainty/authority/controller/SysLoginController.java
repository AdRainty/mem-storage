package com.adrainty.authority.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestUtil;
import com.adrainty.authority.feign.UserClient;
import com.adrainty.authority.service.ISysLoginLogService;
import com.adrainty.common.constants.BizDataConstant;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.common.utils.ValidatorUtils;
import com.adrainty.module.form.LoginForm;
import com.adrainty.module.group.AddGroup;
import com.adrainty.module.sys.SysUser;
import com.adrainty.module.sys.SysUserDto;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
@Api(tags = "注册登录模块")
public class SysLoginController {

    private final UserClient userClient;

    private final ISysLoginLogService iSysLoginLogService;

    @Value("${system.password.publicKey}")
    private String publicKey;

    @Value("${system.password.privateKey}")
    private String privateKey;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginForm loginForm) {
        RSA rsa = new RSA(privateKey, publicKey);
        byte[] decrypt = rsa.decrypt(loginForm.getPassword(), KeyType.PrivateKey);
        loginForm.setPassword(new String(decrypt, StandardCharsets.UTF_8));

        R r = userClient.confirmLogin(loginForm);
        if (r.get(BizDataConstant.USER) == null) {
            return r;
        }

        JSONObject object = new JSONObject(r);
        SysUserDto user = object.getObject(BizDataConstant.USER, SysUserDto.class);
        String token = JwtUtils.createJWT(user.getId(), user.getUsername());

        iSysLoginLogService.saveLoginLog(loginForm.getUsername());
        return R.ok().put(BizDataConstant.TOKEN, token);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/registry")
    public R registry(@RequestBody SysUser sysUser) {
        ValidatorUtils.validateEntity(sysUser, AddGroup.class);
        sysUser.setSalt(RandomStringUtils.randomAlphanumeric(20));

        RSA rsa = new RSA(privateKey, publicKey);
        byte[] decrypt = rsa.decrypt(sysUser.getPassword(), KeyType.PrivateKey);
        String password = new String(decrypt,StandardCharsets.UTF_8);
        String encrypt = DigestUtil.sha256Hex(password + sysUser.getSalt());
        sysUser.setPassword(encrypt);

        R r = userClient.saveUser(sysUser);
        if (r.get(BizDataConstant.USER_ID) != null) {
            String token = JwtUtils.createJWT((Long) r.get(BizDataConstant.USER_ID), sysUser.getUsername());
            iSysLoginLogService.saveLoginLog(sysUser.getUsername());
            return R.ok().put(BizDataConstant.TOKEN, token);
        }
        return r;
    }

    @ApiOperation(value = "获取公钥")
    @GetMapping("/publicKey")
    public R publicKey() {
        return R.ok().put("publicKey", publicKey);
    }

}
