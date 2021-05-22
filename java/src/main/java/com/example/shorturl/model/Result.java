package com.example.shorturl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 返回结果包装类，错误代码这里简单定义一下
 * @param <T> 返回的数据类型
 * @author hcq
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Result<T> {
	/**
	 * 返回成功的代码
	 */
	public static final int SUCCESS_CODE = 200;
	/**
	 * 查无结果返回的代码
	 */
	public static final int NO_FOUND_CODE = 404;
	/**
	 * 异常返回的代码
	 */
	public static final int EXCEPTION_CODE = 500;

	/**
	 * 返回的代码
	 */
	@Getter
	private int code;
	/**
	 * 返回的错误信息
	 */
	@Getter
	private String message;

	/**
	 * 返回的数据,错误时不返回
	 */
	@Getter
	private T data;

	private Result(T data) {
		this.code = SUCCESS_CODE;
		this.data = data;
	}

	private Result(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static <T> Result<T> wrapSuccess(T data) {
		return new Result<>(data);
	}

	public static <T> Result<T> wrapError(int code, String message) {
		return new Result<>(code, message);
	}
}
