package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.request.HttpRequest;
import com.github.diegonighty.http.serialization.ResponseDeserializer;

public interface HttpGetRequest<T> extends HttpRequest<T> {

  /**
   * Set the custom deserializer, this replace the GSON default deserializer
   *
   * @param deserializer The custom deserializer for the JSON Response
   * @return HttpGetRequest the same request with the changes
   */
  HttpGetRequest<T> setResponseDeserializer(ResponseDeserializer<T> deserializer);

}
