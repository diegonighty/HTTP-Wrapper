package com.github.diegonighty.http.util;

import com.github.diegonighty.http.CloseableConnection;
import com.github.diegonighty.http.HttpCloseableConnection;

public class Connections {

  /**
   * Create a new CloseableConnection from URL
   *
   * @param url of the Request
   * @param <T> type of Connection
   * @return the new Connection
   */
  public static <T> CloseableConnection<T> of(String url) {
    return new HttpCloseableConnection<>(url);
  }

}
