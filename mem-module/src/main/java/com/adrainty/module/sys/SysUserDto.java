package com.adrainty.module.sys;

import com.adrainty.module.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/24 23:00
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUserDto对象")
@Data
public class SysUserDto extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("盐")
    private String salt;

}
