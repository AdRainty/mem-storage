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

    @ApiModelProperty("发送人")
    private Long sender;

    @ApiModelProperty("接收人")
    private Long receiver;

    @ApiModelProperty("聊天类型")
    private Integer chatType;

    @ApiModelProperty("消息类型")
    private Integer msgType;

    @ApiModelProperty("聊天消息")
    private String message;

    @ApiModelProperty("是否已读")
    private Integer isRead;


}
