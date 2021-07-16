package com.creolophus.liuyi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2019/6/6 上午12:23
 */
public class ForbiddenException extends HttpStatusException {


  public ForbiddenException(String message) {
    super(HttpStatus.FORBIDDEN, message);
  }
}
