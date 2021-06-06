package com.github.diegonighty.http.util;

import com.github.diegonighty.http.HttpConnection.RequestField;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HeaderMap {

  private final Map<String, Object> headerMap;

  protected HeaderMap(Map<String, Object> map) {
   this.headerMap = map;
  }

  private static Map<String, Object> map(Map<RequestField, Object> map) {
    Map<String, Object> mapped = new LinkedHashMap<>();

    for(Entry<RequestField, ?> entry : map.entrySet()) {
      mapped.put(entry.getKey().parse(), entry.getValue());
    }

    return mapped;
  }

  public static HeaderMap ofString(Map<String, Object> map) {
    return new HeaderMap(map);
  }

  public static HeaderMap of(Map<RequestField, Object> map) {
    return new HeaderMap(map(map));
  }

  public <V> void put(String header, V value) {
    headerMap.put(header, value);
  }

  public <V> void put(RequestField field, V value) {
    put(field.parse(), value);
  }

  public String get(RequestField field) {
    return String.valueOf(headerMap.get(field.parse()));
  }

  public void remove(RequestField field) {
    headerMap.remove(field.parse());
  }

  public Map<String, Object> getHeaderMap() {
    return headerMap;
  }

}
