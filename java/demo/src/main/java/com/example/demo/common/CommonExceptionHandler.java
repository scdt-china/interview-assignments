package com.example.demo.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = RuleException.class)
	public String errorRuleHandler(RuleException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public String errorHandler(Exception ex) {
		return Constants.ErrorCode.SYSTEM_ERROR.getMsg();
	}
}
