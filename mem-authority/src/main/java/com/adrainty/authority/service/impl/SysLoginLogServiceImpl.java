package com.adrainty.authority.service.impl;

import com.adrainty.authority.service.ISysLoginLogService;
import com.adrainty.authority.mapper.SysLoginLogMapper;
import com.adrainty.authority.utils.IPUtils;
import com.adrainty.common.exception.MemException;
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
    public void saveLoginLog(String username) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setIpaddr(IPUtils.getIpAddr());
        try {
            sysLoginLog.setState(IPUtils.getIpRealRegion(sysLoginLog.getIpaddr()));
        } catch (IOException e) {
            throw new MemException("Get state error.");
        }

        this.save(sysLoginLog);
    }
}
