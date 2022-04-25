package com.mycompany.app;

public class Body {
  Point location;
  char action;

  Body() {
    location = new Point();
  }

  Body(char action, Point point) {
    this.action = action;
    this.location = point;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    return ((Body) obj).location.equals(location);
  }
}
