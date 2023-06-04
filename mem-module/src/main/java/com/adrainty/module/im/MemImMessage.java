package com.adrainty.module.im;

import com.adrainty.module.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 18:35
 */

@EqualsAndHashCode(callSuper = true)
@TableName("mem_im_msg")
@ApiModel(value = "聊天对象")
@Data
public class MemImMessage extends BaseEntity implements Serializable {

    @ApiModelProperty("接收者")
    private Integer sender;

    @ApiModelProperty("接收者")
    private Integer receiver;

    @ApiModelProperty("聊天类型")
    private Integer type;

    @ApiModelProperty("聊天消息")
    private String message;


}
