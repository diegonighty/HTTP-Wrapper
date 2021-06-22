package com.github.diegonighty.http.response;

public class WrappedNotSerializedResponse implements HttpResponse<Integer> {

  private final int code;

  public WrappedNotSerializedResponse(int code) {
    this.code = code;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer result() {
    return code;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int code() {
    return code;
  }
}
