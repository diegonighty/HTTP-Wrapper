package com.github.diegonighty.http;

import com.github.diegonighty.http.serialization.ResponseDeserializer;

public final class WrappedHttpResponse<T> implements HttpResponse<T> {

  private final String result;
  private final int code;

  private final ResponseDeserializer<T> deserializer;

  public WrappedHttpResponse(String result, int code, ResponseDeserializer<T> deserializer) {
    this.result = result;
    this.code = code;

    this.deserializer = deserializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T result() {
    return deserializer.deserialize(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int code() {
    return code;
  }
}
