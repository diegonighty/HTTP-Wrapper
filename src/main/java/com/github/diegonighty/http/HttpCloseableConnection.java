package com.github.diegonighty.http;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public final class HttpCloseableConnection<T> implements CloseableConnection<T> {

  private HttpURLConnection connection;
  private TypeToken<T> token;

  private final String url;

  public HttpCloseableConnection(String url) {
    this.url = url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CloseableConnection<T> open() {
    try {
      this.connection = (HttpURLConnection) new URL(url).openConnection();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CloseableConnection<T> openHandlingException() throws IOException {
    this.connection = (HttpURLConnection) new URL(url).openConnection();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    if (connection != null) {
      connection.disconnect();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setType(Class<T> clazz) {
    this.token = TypeToken.get(clazz);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setType(TypeToken<T> token) {
    this.token = token;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRequestMethod(HttpMethod method) {
    try {
      connection.setRequestMethod(method.name());
    } catch (ProtocolException e) {
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <V> void addRequestField(RequestField field, V value) {
    connection.setRequestProperty(field.parse(), value.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRequestFields(Map<RequestField, ?> map) {
    for (Entry<RequestField, ?> entry : map.entrySet()) {
      addRequestField(entry.getKey(), entry.getValue());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpResponse<T> execute() throws FailedConnectionException {
    try {
      connection.connect();

      if (connection.getResponseCode() != 200) {
        throw new FailedConnectionException("Server is not responding");
      }

      return new WrappedHttpResponse<>(getResult(connection.getInputStream()), connection.getResponseCode(), token);
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
