package com.adrainty.module.file;

import com.adrainty.module.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/17 15:16
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分享文件对象", description = "分享文件对象")
@Data
@ToString
public class MemFileShare extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件ID")
    private Long fileId;

    @ApiModelProperty("发送人")
    private Long sender;

    @ApiModelProperty("接收人")
    private Long receiver;

    @ApiModelProperty("分享码, 群发时使用")
    private String code;

}
