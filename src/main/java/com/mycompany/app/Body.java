package com.mycompany.app;

/**
 * Body
 */
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
    if (this == null || !(obj instanceof Body))
      return false;
    if (this == obj)
      return true;
    var body = (Body) obj;
    return body.location.row == location.row && body.location.col == location.col;
  }

  public int szudzikPairingFunction(int x, int y) {
    return (x >= y) ? (x * x + x + y) : (y * y + x);
  }

  @Override
  public int hashCode() {
    return szudzikPairingFunction(location.row, location.col);
  }
}
