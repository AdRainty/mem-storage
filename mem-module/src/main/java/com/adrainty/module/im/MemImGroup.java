package com.adrainty.module.im;

import com.adrainty.module.base.BaseEntity;
import com.adrainty.module.group.AddGroup;
import com.adrainty.module.sys.SysUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 18:26
 */

@EqualsAndHashCode(callSuper = true)
@TableName("mem_im_group")
@ApiModel(value = "组对象")
@Data
public class MemImGroup extends BaseEntity implements Serializable {

    @NotBlank(message = "组名不能为空", groups = AddGroup.class)
    @ApiModelProperty("组名")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    @TableField(exist = false)
    @ApiModelProperty("聊天成员")
    private List<SysUser> members;

}
