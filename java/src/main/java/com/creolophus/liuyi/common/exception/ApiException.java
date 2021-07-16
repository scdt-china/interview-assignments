package com.creolophus.liuyi.common.exception;

public class ApiException extends CreolophusException {

  private String uri;

  public ApiException(String message) {
    super(message);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
