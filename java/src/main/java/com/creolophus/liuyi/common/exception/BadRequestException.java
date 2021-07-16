package com.creolophus.liuyi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2019/6/6 上午12:23
 */
public class BadRequestException extends HttpStatusException {


  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
