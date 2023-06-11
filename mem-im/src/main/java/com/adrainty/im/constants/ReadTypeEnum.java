package com.adrainty.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/11 18:22
 */

@AllArgsConstructor
@Getter
public enum ReadTypeEnum {

    /**
     * 未读
     */
    WAITING(0),

    /**
     * 已读
     */
    READ(1);

    private final Integer code;

}
