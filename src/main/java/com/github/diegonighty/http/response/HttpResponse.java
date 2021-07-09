package com.github.diegonighty.http.response;

import java.io.IOException;

public interface HttpResponse<T> {

  /**
   * Result of the HTTP Request
   * @return http response serialized
   */
  T result() throws IOException;

  /**
   * The status code of the HTTP Request
   * @return http status code
   */
  int code();

}
