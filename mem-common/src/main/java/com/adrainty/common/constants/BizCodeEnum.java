package com.adrainty.common.constants;

/**
 * 异常信息枚举
 * @author AdRainty
 * @version V1.0.0
 * @since 2022/11/26 20:31
 */
public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(500, "System Error"),
    VALID_EXCEPTION(400, "Bad Request");

    private final Integer errCode;
    private final String msg;

    BizCodeEnum(Integer errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getMsg() {
        return msg;
    }
}
