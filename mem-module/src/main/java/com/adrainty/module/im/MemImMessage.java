package com.adrainty.module.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 18:35
 */

@TableName("mem_im_msg")
@ApiModel(value = "聊天对象")
@Data
public class MemImMessage implements Serializable {

    @ApiModelProperty(value = "ID")
    @TableId
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

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
