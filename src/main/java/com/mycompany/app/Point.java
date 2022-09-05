package com.mycompany.app;

public class Point implements Cloneable {
  public int row, col;
  public char action;

  Point(int x, int y) {
    this.row = x;
    this.col = y;
  }

  Point() {
  }

  @Override
  public String toString() {
    return "Point [x=" + row + ", y=" + col + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Point))
      return false;
    var point = (Point) obj;
    return row == point.row && col == point.col;
  }

  public Object clone() throws CloneNotSupportedException {
    return (Point) super.clone();
  }
}
