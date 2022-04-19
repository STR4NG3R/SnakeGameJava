package com.mycompany.app;

/**
 * Logger
 */
public class Logger {
  static final boolean DEBUG = false;

  static void log(String... args) {
    if (DEBUG)
      Logger.log(args);
  }

  static void log(Object... args) {
    if (DEBUG)
      Logger.log(args);
  }
}
