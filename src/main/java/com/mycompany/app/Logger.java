package com.mycompany.app;

/**
 * Logger
 */
public class Logger {
  static final boolean DEBUG = false;

  static void log(Object... args) {
    // if (DEBUG)
      System.out.println(args);
  }

  static void lognr(Object... args) {
    // if (DEBUG)
      System.out.println(args);
  }

}
