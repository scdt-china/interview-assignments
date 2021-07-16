package com.creolophus.liuyi.common.exception;

/**
 * @author magicnana
 * @date 2019/1/7 上午10:26
 */
public class BrokenException extends CreolophusException {

  public BrokenException(String message) {
    super(message);
  }

  public BrokenException(String message, Throwable cause) {
    super(message, cause);
  }

}
