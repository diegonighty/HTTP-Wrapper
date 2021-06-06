package com.github.diegonighty.http.serialization;

public interface RequestSerializer<T> {

  /**
   * Serialize request to json
   *
   * @param object to be serialized
   * @return json
   */
  String serialize(T object);

}
