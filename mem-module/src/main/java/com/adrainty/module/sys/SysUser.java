package com.adrainty.module.sys;

import com.adrainty.common.constants.BizErrorConstant;
import com.adrainty.module.base.BaseEntity;
import com.adrainty.module.group.AddGroup;
import com.adrainty.module.group.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AdRainty
 * @since 2023-05-03
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@ApiModel(value = "SysUser对象")
@Data
public class SysUser extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    @NotBlank(message = BizErrorConstant.USERNAME_CANNOT_EMPTY_ERROR, groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = BizErrorConstant.PASSWORD_CANNOT_EMPTY_ERROR, groups = AddGroup.class)
    private String password;

    @ApiModelProperty("手机号")
    @NotBlank(message = BizErrorConstant.TELEPHONE_CANNOT_EMPTY_ERROR, groups = AddGroup.class)
    private String telephone;

    @ApiModelProperty("盐")
    private String salt;

}
