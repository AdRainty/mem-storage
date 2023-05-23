package com.adrainty.module.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 系统验证码
 * </p>
 *
 * @author AdRainty
 * @since 2023-05-03
 */
@TableName("sys_captcha")
@ApiModel(value = "SysCaptcha对象", description = "系统验证码")
@Data
@ToString
public class SysCaptcha implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("过期时间")
    private Date expireTime;
}
