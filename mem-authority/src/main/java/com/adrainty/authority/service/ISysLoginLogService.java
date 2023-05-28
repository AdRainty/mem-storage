package com.adrainty.authority.service;

import com.adrainty.module.sys.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
public interface ISysLoginLogService extends IService<SysLoginLog> {

    /**
     * 保存登录日志
     * @param username 用户名
     */
    void saveLoginLog(String username);
}
