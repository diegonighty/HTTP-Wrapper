package com.github.diegonighty.http;

import com.github.diegonighty.http.terminable.SilentlyTerminable;
import java.io.IOException;

public interface CloseableConnection<T> extends SilentlyTerminable, HttpConnection<T> {

  /**
   * Open a HTTP URL Connection
   * @return the same closeable connection, but with the HTTP URL opened
   */
  CloseableConnection<T> open();

  /**
   * Open a HTTP URL Connection
   * @return the same closeable connection, but with the HTTP URL opened
   * @throws IOException if an I/O exception occurs.
   */
  CloseableConnection<T> openHandlingException() throws IOException;

}
