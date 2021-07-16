package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.exception.ApiException;
import com.creolophus.liuyi.common.exception.BrokenException;
import com.creolophus.liuyi.common.exception.ErrorCodeException;
import com.creolophus.liuyi.common.exception.HttpStatusException;
import com.creolophus.liuyi.common.json.JSON;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.WebUtils;

/**
 * @author magicnana
 * @date 2018/5/25.
 */
@Component
public class ErrorInfoBuilder {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private ErrorProperties errorProperties;
  @Value("${spring.cloud.http-always-ok:false}")
  private boolean httpAlwaysOK;


  public ErrorInfoBuilder(ServerProperties serverProperties) {
    this.errorProperties = serverProperties.getError();
  }

  public ResponseEntity e0(HttpStatus httpStatus) {
    String message = httpStatus.getReasonPhrase();
    logger.error("error [{}:{}] {}", httpStatus.value(), httpStatus.getReasonPhrase(), message);
    ApiResult apiResult = new ApiResult(httpStatus.value(), message);
    return getResponseEntity(apiResult, httpStatus);
  }

  public ResponseEntity e0(HttpStatus httpStatus, String message) {
    logger.error("error [{}:{}] {}", httpStatus.value(), httpStatus.getReasonPhrase(), message);
    ApiResult apiResult = new ApiResult(httpStatus.value(), message);
    return getResponseEntity(apiResult, httpStatus);
  }

  public ResponseEntity e0(HttpStatus httpStatus, ApiError apiError) {
    logger.error("error [{}:{}] {}", httpStatus.value(), httpStatus.getReasonPhrase(),
        (JSON.toJSONString(apiError)));
    ApiResult apiResult = new ApiResult(apiError.getCode(), apiError.getMessage());
    return getResponseEntity(apiResult, httpStatus);
  }

  public ResponseEntity e0(HttpStatus httpStatus, ApiResult apiResult) {
    logger.error("error [{}:{}] {}", httpStatus.value(), httpStatus.getReasonPhrase(),
        apiResult.getMessage());
    return getResponseEntity(apiResult, httpStatus);
  }

  public Throwable getCloudError(HttpServletRequest request) {
    Throwable e = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
    return e;
  }

  /**
   * 获取错误.(Error/Exception) javax.servlet.error.exception
   * <p>
   * 获取方式：通过Request对象获取(Key="javax.servlet.error.exception").
   *
   * @see DefaultErrorAttributes #addErrorDetails
   */
  public Throwable getError(HttpServletRequest request) {
    //根据Request对象获取错误.
    Throwable error = (Throwable) request
        .getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
    //当获取错误非空,取出RootCause.
    if (error != null) {
      while (error instanceof ServletException && error.getCause() != null) {
        error = error.getCause();
      }
    }//当获取错误为null,此时我们设置错误信息即可.
    return error;
  }

  /**
   * 构建错误信息.
   */
  public ResponseEntity getErrorInfo(HttpServletRequest request, HttpServletResponse response) {

    Throwable e = this.getError(request);
    if (e == null) {
      e = getCloudError(request);
    }

//		if(e==null){
//			// TODO .. 无论何时，不能出现无法取得异常的情况
//			return e0(request,HttpStatus.BAD_REQUEST,ApiCode.E_ERROR.getMessage());
//		}

    //参数类型错误
    if (e instanceof MethodArgumentTypeMismatchException) {
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //此方法不支持
    else if (e instanceof HttpRequestMethodNotSupportedException) {
      return e0(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    //缺少参数
    else if (e instanceof MissingServletRequestParameterException) {
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //需要配置spring:
    //    mvc:
    //      throw-exception-if-no-handler-found: true
    // 这里走else，取得404的错误码返回
    //404
    else if (e instanceof NoHandlerFoundException) {
      return e0(HttpStatus.NOT_FOUND);
    }

//    //参数校验错误
//    else if (e instanceof ConstraintViolationException) {
//      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
//    }

    //Security相关的401和403异常，走这里。但是，必须有额外的message
    else if (e instanceof HttpStatusException) {
      HttpStatusException ee = (HttpStatusException) e;
      ee.setUri(request.getRequestURI());
      return e0(ee.getHttpStatus(), e.getMessage());
    }

    //抛一个ErrorCodeException，直接返回此ErrorCode
    else if (e instanceof ErrorCodeException) {
      ErrorCodeException ee = (ErrorCodeException) e;
      ee.setUri(request.getRequestURI());
      return e0(HttpStatus.BAD_REQUEST, ee.getApiError());
    }

    //Feign异常，不熔断，message是provider返回的ApiResult，直接返回provider的ApiResult
    else if (e instanceof HystrixBadRequestException) {
      try {
        ApiResult apiResult = JSON.parseObject(e.getMessage(), ApiResult.class);
        if (apiResult == null) {
          return e0(HttpStatus.BAD_REQUEST, e.getMessage());
        } else {
          return e0(HttpStatus.BAD_REQUEST, apiResult);
        }
      } catch (Throwable eee) {
        return e0(HttpStatus.BAD_REQUEST, e.getMessage());
      }

    }

    //在程序中抛出的ApiException，message直接给客户端返回，必须throw new ApiException("验证码不正确");
    else if (e instanceof ApiException) {
      ApiException ee = (ApiException) e;
      ee.setUri(request.getRequestURI());
      return e0(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //内部服务错误，熔断，返回给客户端500，"暂时无法提供服务"
    else if (e instanceof BrokenException) {
      return e0(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.E_INTERNAL_ERROR);

    }

    //有SqlException之类的，不方便给客户端提供的走这里，返回"服务器内部错误"
    else if (e instanceof Throwable) {
      return e0(HttpStatus.BAD_REQUEST, ApiError.E_ERROR);
    } else {
      int code = (int) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
      HttpStatus httpStatus = HttpStatus.valueOf(code);
      httpStatus = httpStatus == null ? HttpStatus.BAD_REQUEST : httpStatus;
      return e0(httpStatus);
    }
  }

  public String getErrorPath() {
    return this.errorProperties.getPath();
  }

  private ResponseEntity getResponseEntity(ApiResult apiResult, HttpStatus httpStatus) {
    if (httpAlwaysOK) {
      return new ResponseEntity(apiResult, HttpStatus.OK);
    } else {
      return new ResponseEntity(apiResult, httpStatus);
    }
  }
}
