package com.github.diegonighty.http;

public interface HttpResponse<T> {

  /**
   * Result of the HTTP Request
   * @return http response serialized
   */
  T result();

  /**
   * The status code of the HTTP Request
   * @return http status code
   */
  int code();

}
