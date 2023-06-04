package com.adrainty.module.im;

import com.adrainty.module.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/4 23:50
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "组对象")
@Data
public class MemTalkVo extends BaseEntity {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("未读消息数")
    private Integer count;

}
