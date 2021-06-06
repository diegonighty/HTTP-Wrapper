package com.github.diegonighty.http.response;

import com.github.diegonighty.http.serialization.ResponseDeserializer;

public final class WrappedHttpResponse<R> implements HttpResponse<R> {

  private final String result;
  private final int code;

  private final ResponseDeserializer<R> deserializer;

  public WrappedHttpResponse(String result, int code, ResponseDeserializer<R> deserializer) {
    this.result = result;
    this.code = code;

    this.deserializer = deserializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public R result() {
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
