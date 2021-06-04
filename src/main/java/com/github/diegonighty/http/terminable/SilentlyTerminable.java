package com.github.diegonighty.http.terminable;

public interface SilentlyTerminable extends AutoCloseable {

  /**
   * Close a resource without catch exception
   */
  @Override
  void close();

}
