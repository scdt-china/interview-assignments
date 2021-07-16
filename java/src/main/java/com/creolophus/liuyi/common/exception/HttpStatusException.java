package com.creolophus.liuyi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2019/6/6 上午12:24
 */
public class HttpStatusException extends ApiException {

  private HttpStatus httpStatus;

  public HttpStatusException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatusException(HttpStatus httpStatus) {
    super(httpStatus.getReasonPhrase());
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
