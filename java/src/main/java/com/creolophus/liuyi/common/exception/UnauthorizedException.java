package com.creolophus.liuyi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2019/6/6 上午12:23
 */
public class UnauthorizedException extends HttpStatusException {


  public UnauthorizedException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}
