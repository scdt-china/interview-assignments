package com.creolophus.liuyi.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2019/1/7 上午10:26
 */
public class InternalServerException extends HttpStatusException {


  public InternalServerException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
}
