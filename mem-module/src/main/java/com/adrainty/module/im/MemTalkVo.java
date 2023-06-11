package com.adrainty.module.im;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/4 23:50
 */

@ApiModel(value = "组对象")
@Data
public class MemTalkVo {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("聊天类型")
    private Integer chatType;

    @ApiModelProperty("聊天ID")
    private Long id;

    @ApiModelProperty("未读消息数")
    private Long count;

}
