package com.adrainty.gateway.service.impl;

import com.adrainty.gateway.mapper.SysLoginLogMapper;
import com.adrainty.gateway.service.ISysLoginLogService;
import com.adrainty.gateway.utils.IPUtils;
import com.adrainty.module.sys.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

    @Override
    public void saveLoginLog(String username) throws IOException {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setIpaddr(IPUtils.getIpAddr());
        sysLoginLog.setState(IPUtils.getIpRealRegion(sysLoginLog.getIpaddr()));
        this.save(sysLoginLog);
    }
}
