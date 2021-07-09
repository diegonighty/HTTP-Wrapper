package com.github.diegonighty.http.serialization.common;

import com.github.diegonighty.http.serialization.RequestSerializer;
import com.github.diegonighty.http.serialization.ResponseDeserializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * JsonSerializationWrapper is a default (de)serializer using JSON
 *
 * @param <T> Object to deserialize
 */
public class JsonSerializationWrapper<T> implements ResponseDeserializer<T>, RequestSerializer<T> {

  private static final Gson GSON = new Gson();
  private final TypeToken<T> token;

  public JsonSerializationWrapper(TypeToken<T> token) {
    this.token = token;
  }

  public JsonSerializationWrapper(Class<T> token) {
    this.token = TypeToken.get(token);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T deserialize(InputStream stream) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      StringBuilder resultBuilder = new StringBuilder();
      reader.lines().forEachOrdered(resultBuilder::append);

      return GSON.fromJson(resultBuilder.toString(), token.getType());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(T object, OutputStream stream) throws IOException {
    String json = GSON.toJson(object);
    byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

    try (BufferedOutputStream output = new BufferedOutputStream(stream)) {
      output.write(bytes);
    }
  }
}
