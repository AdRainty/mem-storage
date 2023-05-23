package com.adrainty.module.sys;

import com.adrainty.module.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author AdRainty
 * @since 2023-05-03
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
@ApiModel(value = "SysLoginLog对象")
@ToString
@Data
public class SysLoginLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    @NotNull
    private String username;

    @ApiModelProperty("ip地址")
    private String ipaddr;

    @ApiModelProperty("所属地区")
    private String state;

}
