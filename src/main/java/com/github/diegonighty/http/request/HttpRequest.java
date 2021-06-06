package com.github.diegonighty.http.request;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;

public interface HttpRequest<T> {

  /**
   * Execute the HTTP request
   *
   * @return the http responses if the request is a PUT/POST will be return status code
   * @throws FailedConnectionException if server does not respond correctly
   */
  HttpResponse<T> execute() throws FailedConnectionException;

}
