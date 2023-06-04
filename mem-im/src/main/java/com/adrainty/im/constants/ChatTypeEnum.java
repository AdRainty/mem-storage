package com.adrainty.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 18:29
 */

@AllArgsConstructor
@Getter
public enum ChatTypeEnum {

    /**
     * 单聊
     */
    SINGLE_CHAT(0),

    /**
     * 群聊
     */
    GROUP_CHAT(1);

    private final Integer code;

}
