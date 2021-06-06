package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.response.WrappedNotSerializedResponse;
import com.github.diegonighty.http.serialization.RequestSerializer;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class WrappedHttpPostRequest<T> implements HttpPostRequest<T> {

  private final HttpURLConnection connection;
  private RequestSerializer<T> serializer;

  private T object;

  public WrappedHttpPostRequest(HttpURLConnection connection) {
    this.connection = connection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpPostRequest<T> setSerializer(RequestSerializer<T> serializer) {
    this.serializer = serializer;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpPostRequest<T> setObject(T object) {
    this.object = object;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpResponse<Integer> execute() throws FailedConnectionException {
    final String json = serializer.serialize(object);
    final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

    connection.setDoOutput(true);
    connection.setFixedLengthStreamingMode(bytes.length);
    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

    try {
      connection.connect();

      try (BufferedOutputStream stream = new BufferedOutputStream(connection.getOutputStream())) {
        stream.write(bytes);
      }

      if (connection.getResponseCode() != 201 && connection.getResponseCode() != 200) {
        throw new FailedConnectionException("Data is not posted correctly", connection.getResponseCode());
      }

      return new WrappedNotSerializedResponse(connection.getResponseCode());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

}
