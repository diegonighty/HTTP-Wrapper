package com.github.diegonighty.http;

import java.io.IOException;

public interface CloseableConnection<T> extends AutoCloseable, HttpConnection<T> {

  CloseableConnection<T> open();

  CloseableConnection<T> openHandlingException() throws IOException;

}
