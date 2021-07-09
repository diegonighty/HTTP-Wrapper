package com.github.diegonighty.http.serialization;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestSerializer<T> {

  /**
   * Serialize request to json
   *
   * @param object to be serialized
   * @param stream OutputStream involved in the HTTP Request
   *
   * @throws IOException if an I/O exception occurs.
   */
  void serialize(T object, OutputStream stream) throws IOException;

}
