package com.adrainty.im.feign;

import com.adrainty.module.form.FileShareForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/17 15:29
 */

@FeignClient("mem-file")
public interface FileClient {

    @PostMapping("/file/internal/share")
    void share(@RequestBody FileShareForm form);

}
