package com.adrainty.user.service.impl;

import com.adrainty.user.mapper.SysUserMapper;
import com.adrainty.user.service.ISysUserService;
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
        if (user == null ||
                !user.getPassword().equals(DigestUtil.sha256Hex(loginForm.getPassword() + user.getSalt()))) return null;
        return user;
    }

    @Override
    public List<String> searchPermission(Long id) {
        return new ArrayList<>();
    }
}
