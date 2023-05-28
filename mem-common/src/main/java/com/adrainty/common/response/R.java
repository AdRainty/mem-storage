package com.adrainty.common.response;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.constants.BizErrorConstant;
import org.apache.http.HttpStatus;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, BizErrorConstant.UNKNOWN_ERROR);
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R error(BizCodeEnum bizCodeEnum) {
		R r = new R();
		r.put("code", bizCodeEnum.getErrCode());
		r.put("msg", bizCodeEnum.getErrCode());
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public  Integer getCode() {
		return (Integer) this.get("code");
	}
}
