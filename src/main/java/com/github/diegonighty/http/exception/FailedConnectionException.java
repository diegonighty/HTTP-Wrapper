package com.github.diegonighty.http.exception;

public class FailedConnectionException extends Exception {

  private final int code;

  public FailedConnectionException(String header, int code) {
    super(header);

    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
