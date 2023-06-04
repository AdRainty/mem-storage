package com.adrainty.im.feign;

import com.adrainty.module.sys.SysUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/5 0:18
 */
@FeignClient("mem-user")
public interface SysUserClient {

    @PostMapping("/user/userInfoBatch")
    List<SysUserDto> userInfoBatch(@RequestBody List<Long> ids);

}
