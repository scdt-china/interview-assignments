package com.sequoiacapital.assignment.common.resp;


import com.sequoiacapital.assignment.common.conts.ResultStatus;
import lombok.Data;
import lombok.ToString;

/**
 * 通用返回结果接口
 * @author xin.wu
 * @date 2021/6/24
 */
@ToString
@Data
public class Result<T>  {
	private String msg;
	private Integer code;
	private T data;

	private Result(){
		super();
	}

	private Result(ResultStatus resultStatus, T data){
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
		this.data = data;
	}

	/**
	 * 业务成功返回业务代码和描述信息
	 * @author xin.wu
	 * @date 2021/6/1
	 * @param
	 * @return com.aat.dizena.common.resp.Result<java.lang.Void>
	 */
	public static Result<Void> success() {
		return new Result<Void>(ResultStatus.SUCCESS, null);
	}

	/**
	 * 业务成功返回业务代码,描述和返回的参数
	 * @author xin.wu
	 * @date 2021/6/1
	 * @param data
	 * @return com.aat.dizena.common.resp.CommResult<T>
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(ResultStatus.SUCCESS, data);
	}

	/**
	 * 业务成功返回业务代码,描述和返回的参数
	 * @author xin.wu
	 * @date 2021/6/1
	 * @param resultStatus
	 * @param data
	 * @return com.aat.dizena.common.resp.Result<T>
	 */
	public static <T> Result<T> success(ResultStatus resultStatus, T data) {
		if (resultStatus == null) {
			return success(data);
		}
		return new Result<T>(resultStatus, data);
	}

	/**
	 * 业务异常返回业务代码,描述和返回的参数
	 * @author xin.wu
	 * @date 2021/6/1
	 * @param resultStatus
	 * @param msg
	 * @return com.aat.dizena.common.resp.Result<T>
	 */
	public static <T> Result<T> failure(ResultStatus resultStatus, String msg) {
		Result result = new Result();
		result.setCode(resultStatus.getCode());
		result.setMsg(msg);
		return result;
	}

	/**
	 * 失败消息
	 * @author xin.wu
	 * @date 2021/6/2
	 * @param msg
	 * @return com.aat.dizena.common.resp.Result<T>
	 */
	public static <T> Result<T> failure(String msg){
		Result result = new Result();
		result.setCode(ResultStatus.INTERNAL_SERVER_ERROR.getCode());
		result.setMsg(msg);
		return result;
	}

	/**
	 * 失败消息
	 * @author xin.wu
	 * @date 2021/6/2
	 * @param msg
	 * @return com.aat.dizena.common.resp.Result<T>
	 */
	public static <T> Result<T> error(Integer code,String msg){
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 失败消息
	 * @author xin.wu
	 * @date 2021/6/2
	 * @param msg
	 * @return com.aat.dizena.common.resp.Result<T>
	 */
	public static <T> Result<T> error(ResultStatus resultStatus,String msg){
		Result result = new Result();
		result.setCode(resultStatus.getCode());
		result.setMsg(msg);
		return result;
	}


}
