package com.adrainty.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常信息枚举
 * @author AdRainty
 * @version V1.0.0
 * @since 2022/11/26 20:31
 */

@AllArgsConstructor
@Getter
public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(500, BizErrorConstant.UNKNOWN_ERROR),
    UN_AUTHORITY(403, BizErrorConstant.UN_AUTHORITY_ERROR);

    private final Integer errCode;
    private final String msg;

}
