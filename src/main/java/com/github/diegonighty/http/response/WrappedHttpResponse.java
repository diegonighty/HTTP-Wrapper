package com.github.diegonighty.http.response;

import com.github.diegonighty.http.serialization.ResponseDeserializer;

import java.io.IOException;
import java.io.InputStream;

public final class WrappedHttpResponse<R> implements HttpResponse<R> {

  private final InputStream result;
  private final int code;

  private final ResponseDeserializer<R> deserializer;

  public WrappedHttpResponse(InputStream result, int code, ResponseDeserializer<R> deserializer) {
    this.result = result;
    this.code = code;

    this.deserializer = deserializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public R result() throws IOException {
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
