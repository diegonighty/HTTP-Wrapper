package com.github.diegonighty.http;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.serialization.ResponseDeserializer;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

public interface HttpConnection<T> {

  /**
   * Set the type of object that will be serialized
   *
   * @param clazz Class type of object for the response
   */
  void setType(Class<T> clazz);

  /**
   * Set the type of object that will be serialized
   *
   * @param token T
   */
  void setType(TypeToken<T> token);

  /**
   * Set the custom deserializer, this replace the GSON default deserializer
   *
   * @param deserializer The custom deserializer for the JSON Response
   */
  void setResponseDeserializer(ResponseDeserializer<T> deserializer);

  /**
   * Set method to http request
   * @see <a href="https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods"> List of request methods </a>
   *
   * @param method HttpMethod that will be used in the request
   */
  void setRequestMethod(HttpMethod method);

  /**
   * Add header to http request
   * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_header_fields"> List of header fields and usage </a>
   *
   * @param field Field that will be added in the headers of the request
   * @param value Value of the field, this will be serialized to string
   */
  <V> void addRequestField(RequestField field, V value);

  /**
   * Add headers to http request
   * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_header_fields"> List of header fields and usage </a>
   *
   * @param map Map containing all fields and values, the values will be serialized to string
   */
  void addRequestFields(Map<RequestField, ?> map);

  /**
   * Execute the HTTP request
   *
   * @return HttpResponse contains the response serialized and the status code
   * @throws FailedConnectionException if api is not responding
   */
  HttpResponse<T> execute() throws FailedConnectionException;


  /**
   * HTTP Methods for the request
   * @see <a href="https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods"> List of method request and usage </a>
   */
  enum HttpMethod {

    GET,
    POST,
    UPDATE,
    DELETE

  }

  /**
   * HTTP headers for the request
   * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_header_fields"> List of header fields and usage </a>
   */
  enum RequestField {

    A_IM,
    ACCEPT,
    ACCEPT_CHARSET,
    ACCEPT_DATATIME,
    ACCEPT_ENCODING,
    ACCEPT_LANGUAGE,
    ACCESS_CONTROL_REQUEST_METHOD,
    ACCESS_CONTROL_REQUEST_HEADERS,
    AUTHORIZATION,
    CACHE_CONTROL,
    CONNECTION,
    CONTENT_ENCODING,
    CONTENT_LENGTH,
    CONTENT_MD5,
    CONTENT_TYPE,
    COOKIE,
    DATE,
    EXPECT,
    FORWARDED,
    FROM,
    HOST,
    IF_MATCH,
    IF_MODIFIED_SINCE,
    IF_NONE_MATCH,
    IF_RANGE,
    IF_UNMODIFIED_SINCE,
    MAX_FORWARDS,
    ORIGIN,
    PRAGMA,
    PREFER,
    PROXY_AUTHORIZATION,
    RANGE,
    REFERER,
    TE,
    TRAILER,
    TRANSFER_ENCODING,
    USER_AGENT,
    UPGRADE,
    VIA,
    WARNING;

    /**
     * parse http header to string
     * @return Header parsed
     */
    public String parse() {
      return toString().replace("_", "-");
    }
  }
}
