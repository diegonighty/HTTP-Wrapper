package com.github.diegonighty.http;

import com.github.diegonighty.http.request.types.HttpDeleteRequest;
import com.github.diegonighty.http.request.types.HttpGetRequest;
import com.github.diegonighty.http.request.types.HttpPostRequest;
import com.github.diegonighty.http.request.types.WrappedHttpDeleteRequest;
import com.github.diegonighty.http.request.types.WrappedHttpGetRequest;
import com.github.diegonighty.http.request.types.WrappedHttpPostRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public final class HttpCloseableConnection<T> implements CloseableConnection<T> {

  private HttpURLConnection connection;

  private final String url;

  public HttpCloseableConnection(String url) {
    this.url = url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CloseableConnection<T> open() throws IOException {
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
  public <V> HttpConnection<T> addRequestField(String field, V value) {
    connection.setRequestProperty(field, value.toString());
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HttpConnection<T> addRequestFields(Map<String, Object> map) {
    for (Entry<String, Object> entry : map.entrySet()) {
      addRequestField(entry.getKey(), entry.getValue());
    }

    return this;
  }

  @Override
  public HttpGetRequest<T> createGetRequest() {
    setMethod(HttpMethod.GET);

    return new WrappedHttpGetRequest<>(connection);
  }

  @Override
  public HttpPostRequest<T> createPostRequest() {
    setMethod(HttpMethod.POST);

    return new WrappedHttpPostRequest<>(connection);
  }

  @Override
  public HttpDeleteRequest createDeleteRequest() {
    setMethod(HttpMethod.DELETE);

    return new WrappedHttpDeleteRequest(connection);
  }

  private void setMethod(HttpMethod method) {
    try {
      connection.setRequestMethod(method.name());
    } catch (ProtocolException e) {
      e.printStackTrace();
    }
  }

}
