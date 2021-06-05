package com.github.diegonighty.http;

import com.github.diegonighty.http.serialization.ResponseDeserializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;

public final class WrappedHttpResponse<T> implements HttpResponse<T> {

  private static final Gson GSON = new Gson();

  private final String result;
  private final int code;

  private final TypeToken<T> token;
  private final ResponseDeserializer<T> deserializer;

  public WrappedHttpResponse(String result, int code, TypeToken<T> token, ResponseDeserializer<T> deserializer) {
    this.result = result;
    this.code = code;

    this.token = token;
    this.deserializer = deserializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T result() {
    T object;

    if (deserializer != null) {
      object = deserializer.deserialize(result);
    } else if (token != null) {
      object = GSON.fromJson(result, token.getType());
    } else {
      object = GSON.fromJson(result, new TypeToken<T>() {}.getType());
    }

    return object;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int code() {
    return code;
  }
}
