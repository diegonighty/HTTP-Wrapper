package com.github.diegonighty.http.serialization;

public interface ResponseDeserializer<T> {

  /**
   * Custom deserializer for the HTTP Response
   *
   * @param json the response of the http request
   * @return the serialized response
   */
  T deserialize(String json);

}
