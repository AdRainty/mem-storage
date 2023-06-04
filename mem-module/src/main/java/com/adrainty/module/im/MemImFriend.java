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
 * @since 2023/6/4 15:24
 */

@EqualsAndHashCode(callSuper = true)
@TableName("mem_im_group")
@ApiModel(value = "好友对象")
@Data
public class MemImFriend extends BaseEntity implements Serializable {

    @ApiModelProperty("用户1")
    private Long user1;

    @ApiModelProperty("用户2")
    private Long user2;

}
