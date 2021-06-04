package com.github.diegonighty.http;

import com.github.diegonighty.http.exception.FailedConnectionException;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

public interface HttpConnection<T> {

  /**
   * Set the type of object that will be serialized
   */
  void setType(Class<T> clazz);

  void setType(TypeToken<T> token);

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

  HttpResponse<T> execute() throws FailedConnectionException;

  enum HttpMethod {

    GET,
    POST,
    UPDATE,
    DELETE

  }

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

    public String parse() {
      return toString().replace("_", "-");
    }
  }
}
