package com.adrainty.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 18:38
 */

@AllArgsConstructor
@Getter
public enum MsgTypeEnum {

    /**
     * 纯文本
     */
    TEXT(0),

    /**
     * 图片
     */
    PICTURE(1),

    /**
     * 文件
     */
    FILE(2);

    private final Integer code;

}
