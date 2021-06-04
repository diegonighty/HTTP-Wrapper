package com.github.diegonighty.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public final class WrappedHttpResponse<T> implements HttpResponse<T> {

  private static final Gson GSON = new Gson();

  private final String result;
  private final int code;

  private final TypeToken<T> token;

  public WrappedHttpResponse(String result, int code, TypeToken<T> token) {
    this.result = result;
    this.code = code;

    this.token = token;
  }

  @Override
  public T result() {
    return GSON.fromJson(result, token.getType());
  }

  @Override
  public int code() {
    return code;
  }
}
