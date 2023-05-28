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
 * @since 2023/5/27 22:31
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FileItem对象", description = "文件对象")
@Data
@ToString
public class MemFile extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("Bucket内文件名称")
    private String realName;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件路径")
    private String path;

    @ApiModelProperty("文件类型")
    private Integer type;

    @ApiModelProperty("属于用户")
    private Long belongUser;

}
