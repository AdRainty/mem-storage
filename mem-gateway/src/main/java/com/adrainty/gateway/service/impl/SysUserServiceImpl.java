package com.adrainty.gateway.service.impl;

import com.adrainty.gateway.mapper.SysUserMapper;
import com.adrainty.gateway.service.ISysUserService;
import com.adrainty.module.form.LoginForm;
import com.adrainty.module.sys.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser confirmForm(LoginForm loginForm) {
        LambdaQueryWrapper<SysUser> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        SysUser user = this.baseMapper.selectOne(objectLambdaQueryWrapper.eq(SysUser::getUsername, loginForm.getUsername()));
        if (user == null) return null;
        if(!user.getPassword().equals(DigestUtil.sha256Hex(loginForm.getPassword()))){
            return null;
        }
        return user;
    }

    @Override
    public List<String> searchPermission(Long id) {
        // authorities.add("YourController:YourMethod");
        return new ArrayList<>();
    }
}
