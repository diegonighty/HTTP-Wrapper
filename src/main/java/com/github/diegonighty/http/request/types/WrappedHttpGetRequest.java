package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.response.WrappedHttpResponse;
import com.github.diegonighty.http.serialization.ResponseDeserializer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public final class WrappedHttpGetRequest<T> implements HttpGetRequest<T> {

  private final HttpURLConnection connection;
  private ResponseDeserializer<T> deserializer;

  public WrappedHttpGetRequest(HttpURLConnection connection) {
    this.connection = connection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpGetRequest<T> setResponseDeserializer(ResponseDeserializer<T> deserializer) {
    this.deserializer = deserializer;

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpResponse<T> execute() throws FailedConnectionException {
    try {
      connection.connect();

      if (connection.getResponseCode() != 200) {
        throw new FailedConnectionException("Server is not responding", connection.getResponseCode());
      }

      return new WrappedHttpResponse<>(getResult(connection.getInputStream()), connection.getResponseCode(), deserializer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  private String getResult(InputStream stream) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

      final StringBuilder resultBuilder = new StringBuilder();
      reader.lines().forEachOrdered(resultBuilder::append);

      return resultBuilder.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
