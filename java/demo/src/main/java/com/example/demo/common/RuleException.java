package com.example.demo.common;

import com.example.demo.common.Constants.ErrorCode;

public class RuleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RuleException(ErrorCode errCode) {
		super(errCode.getMsg());
	}

}
