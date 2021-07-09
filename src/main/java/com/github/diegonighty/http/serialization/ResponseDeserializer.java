package com.github.diegonighty.http.serialization;

import java.io.IOException;
import java.io.InputStream;

public interface ResponseDeserializer<T> {

  /**
   * Custom deserializer for the HTTP Response
   *
   * @param stream the response of the http request
   *
   * @return the serialized response
   *
   * @throws IOException if an I/O exception occurs.
   */
  T deserialize(InputStream stream) throws IOException;

}
