package com.assignments.url.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO<T> {

	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_FAILED  = -1;
	
	private int status = STATUS_SUCCESS;
	private String msg;//接口执行信息
	private T data;//需要传输的数据
	
	public void failed(String msg){
		this.status = STATUS_FAILED;
		this.msg = msg;
	}
	public void failed(String msg,int status){
		this.status = status;
		this.msg = msg;
	}
}
