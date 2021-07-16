package com.creolophus.liuyi.common.exception;

import org.apache.commons.lang3.StringUtils;

public class CreolophusException extends RuntimeException {

  private static final long serialVersionUID = -8748008038972403572L;

  public CreolophusException(String message) {
    super(message);
  }

  public CreolophusException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public String getMessage() {
    return StringUtils.isBlank(super.getMessage()) ? "Error" : super.getMessage();
  }
}
