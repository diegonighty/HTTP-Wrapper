package com.github.diegonighty.http.serialization.common;

import com.github.diegonighty.http.serialization.ResponseDeserializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * DefaultResponseDeserializer is used when you do not declare a custom ResponseDeserializer this
 * deserializer uses generic gson TypeToken to deserialize, so may be will have problems
 *
 * @param <T> Object to deserialize
 */
public class DefaultResponseDeserializer<T> implements ResponseDeserializer<T> {

  private static final Gson GSON = new Gson();
  private final TypeToken<T> token;

  public DefaultResponseDeserializer(TypeToken<T> token) {
    this.token = token;
  }

  public DefaultResponseDeserializer(Class<T> token) {
    this.token = TypeToken.get(token);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T deserialize(String json) {
    return GSON.fromJson(json, token.getType());
  }

}
