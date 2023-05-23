package com.adrainty.module.form;

import lombok.Data;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/3 18:09
 */
@Data
public class LoginForm {

    private String uuid;

    private String username;

    private String password;

    private String captcha;
}
