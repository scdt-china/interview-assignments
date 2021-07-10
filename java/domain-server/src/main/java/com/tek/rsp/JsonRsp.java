package com.tek.rsp;

public class JsonRsp {
	
	private String code;
	
	private String message;
	
	private Object data;
	
	private JsonRsp() {}
	
	private JsonRsp(String code,String message) {
		this.code = code;
		this.message = message;
	}
	
	
	public static JsonRsp success() {
		return new JsonRsp("100000","sucess");
	}
	
	public static JsonRsp Fail() {
		return new JsonRsp("999999","fail"); 
	}
	
	public static JsonRsp Fail(String code,String message) {
		return new JsonRsp(code,message);
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

}
