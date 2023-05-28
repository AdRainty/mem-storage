package com.adrainty.user.service;

import com.adrainty.module.form.LoginForm;
import com.adrainty.module.sys.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 校验表单信息是否正确
     * @param loginForm 登录表单
     */
    SysUser confirmForm(LoginForm loginForm);

    /**
     * 查询用户权限列表
     * @param id 用户id
     */
    List<String> searchPermission(Long id);
}
