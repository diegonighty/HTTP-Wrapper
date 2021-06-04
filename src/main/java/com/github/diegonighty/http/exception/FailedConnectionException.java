package com.github.diegonighty.http.exception;

public class FailedConnectionException extends Exception {

  public FailedConnectionException(String header) {
    super(header);
  }

}
