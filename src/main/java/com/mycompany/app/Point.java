package com.mycompany.app;

public class Point {
  public int row, col;

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
    var point = (Point) obj;
    return row == point.row && col == point.col;
  }
}
