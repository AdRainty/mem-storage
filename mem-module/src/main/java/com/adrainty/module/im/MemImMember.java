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
 * @since 2023/6/3 18:34
 */

@EqualsAndHashCode(callSuper = true)
@TableName("mem_im_member")
@ApiModel(value = "组成员对象")
@Data
public class MemImMember extends BaseEntity implements Serializable {

    @ApiModelProperty("所属组")
    private Long crowd;

    @ApiModelProperty("组员")
    private Long member;

}
