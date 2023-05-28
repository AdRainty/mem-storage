package com.adrainty.authority.feign;

import com.adrainty.common.response.R;
import com.adrainty.module.form.LoginForm;
import com.adrainty.module.sys.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/24 22:40
 */

@FeignClient("mem-user")
public interface UserClient {

    @PostMapping("/user/confirmLogin")
    R confirmLogin(@RequestBody LoginForm loginForm);

    @PostMapping("/user/saveUser")
    R saveUser(@RequestBody SysUser sysUser);

}
