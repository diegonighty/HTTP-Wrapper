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
    Accept,
    Accept_Charset,
    Accept_Datatime,
    Accept_Encoding,
    Accept_Language,
    Access_Control_Request_Method,
    Access_Control_Request_Headers,
    Authorization,
    Cache_Control,
    Connection,
    Content_Encoding,
    Content_Length,
    Content_MD5,
    Content_Type,
    Cookie,
    Date,
    Expect,
    Forwarded,
    From,
    Host,
    If_Match,
    If_Modified_Since,
    If_None_Match,
    If_Range,
    If_Unmodified_Since,
    Max_Forwards,
    Origin,
    Pragma,
    Prefer,
    Proxy_Authorization,
    Range,
    Referer,
    TE,
    Trailer,
    Transfer_Encoding,
    User_Agent,
    Upgrade,
    Via,
    Warning;

    public String parse() {
      return toString().replace("_", "-");
    }
  }
}
