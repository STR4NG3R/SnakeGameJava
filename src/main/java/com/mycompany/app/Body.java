package com.mycompany.app;

/**
 * Body
 */
public class Body {
  Point location = new Point();
  char action;

  Body() {
  }

  Body(char action, Point point) {
    this.action = action;
    this.location = point;
  }
}
