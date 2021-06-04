package com.github.diegonighty.http;

public interface HttpResponse<T> {

  T result();

  int code();

}
