package com.example.demo.common;

public class Constants {

	public static final String BASE_DOMAIN = "https://www.t.tt/";

	public static final String URL_ROLE =
			"(https://|http://|ftp://|file://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;\\s]+[-A-Za-z0-9+&@#/%=~_|]";

	public static final String DATE_FORMAT = "yyyy-MM-dd HH24:mm:ss";
	
	public enum ErrorCode {
		SYSTEM_ERROR("系统异常, 请稍后再试"), 
		URL_MAPPING_NOT_EXIST("链接不存在"), 
		URL_EMPTY("链接参数不能为空"), 
		URL_NOT_FORMAT("链接格式不正确"), 
		URL_TRANSFER_ERROR("链接转换错误");
		
		ErrorCode(String msg) {
			this.msg = msg;
		}

		private String msg;

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}
}
