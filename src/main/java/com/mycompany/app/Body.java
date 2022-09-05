package com.mycompany.app;

public class Body implements Cloneable {
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

  public Object clone() throws CloneNotSupportedException {
    var b = (Body) super.clone();
    b.location = (Point) location.clone();
    return b;
  }
}
