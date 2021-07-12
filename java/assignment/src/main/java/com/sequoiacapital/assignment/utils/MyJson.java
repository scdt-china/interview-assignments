package com.sequoiacapital.assignment.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * JSON格式转化类
 * @author xin.wu
 * @date 2021/6/24
 */
public class MyJson {

	/**
	 * 对象转json字符串
	 * @author xin.wu
	 * @date 2021/6/24
	 * @param obj
	 * @return java.lang.String
	 */
	public static String obj2JsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(obj);
			return json;
		} catch (JsonProcessingException e) {
		}
		return null;
	}

	/**
	 * 字符串转JSON
	 * @author xin.wu
	 * @date 2021/6/24
	 * @param str
	 * @param valueType
	 * @return T
	 */
	public static <T> T  string2Object(String str, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(str, valueType);
		} catch (JsonProcessingException e) {
		} catch (IOException e) {
		}
		return null;
	}
	
	
}
