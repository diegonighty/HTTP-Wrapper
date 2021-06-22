package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.response.WrappedNotSerializedResponse;
import com.github.diegonighty.http.util.StatusCode;

import java.io.IOException;
import java.net.HttpURLConnection;

public class WrappedHttpDeleteRequest implements HttpDeleteRequest {

  private final HttpURLConnection connection;

  public WrappedHttpDeleteRequest(HttpURLConnection connection) {
    this.connection = connection;
  }

  @Override
  public HttpResponse<Integer> execute() throws FailedConnectionException {

    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    try {
      connection.connect();

      if (!StatusCode.isSuccessful(connection.getResponseCode())) {
        throw new FailedConnectionException("Server is not responding", connection.getResponseCode());
      }

      return new WrappedNotSerializedResponse(connection.getResponseCode());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

}
