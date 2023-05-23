package com.adrainty.common.exception;

import com.adrainty.common.constants.BizCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/18 19:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemException extends RuntimeException {

    private final String msg;
    private final int code = BizCodeEnum.UNKNOWN_EXCEPTION.getErrCode();

    public MemException(String msg) {
        super(msg);
        this.msg = msg;
    }

}
