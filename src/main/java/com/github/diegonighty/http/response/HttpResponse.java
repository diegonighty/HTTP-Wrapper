package com.github.diegonighty.http.response;

import java.io.IOException;

public interface HttpResponse<T> {

  /**
   * Result of the HTTP Request
   * @return http response serialized
   *
   * @throws IOException if an I/O exception occurs.
   */
  T result() throws IOException;

  /**
   * The status code of the HTTP Request
   * @return http status code
   */
  int code();

}
