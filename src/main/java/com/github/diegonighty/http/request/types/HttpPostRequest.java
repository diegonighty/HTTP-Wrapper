package com.github.diegonighty.http.request.types;

import com.github.diegonighty.http.request.HttpRequest;
import com.github.diegonighty.http.serialization.RequestSerializer;

public interface HttpPostRequest<T> extends HttpRequest<Integer> {

  /**
   * Set custom serializer from object to JSON
   *
   * @param serializer convert object to JSON
   * @return the same http request with the changes
   */
  HttpPostRequest<T> setSerializer(RequestSerializer<T> serializer);

  /**
   * Set the object to post
   *
   * @param object to post
   * @return the same http request with the changes
   */
  HttpPostRequest<T> setObject(T object);

}
