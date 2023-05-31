package com.adrainty.file.feign;

import com.adrainty.common.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/24 22:40
 */

@FeignClient("mem-user")
public interface UserClient {

    @GetMapping("/user/getUsernameByUserId")
    R getUsernameByUserId(@RequestParam("id") Long id);

}
