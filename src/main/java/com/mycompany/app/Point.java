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
}
