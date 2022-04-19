package com.mycompany.app;

public class Node {
  public Point p;
  public int g;
  public int h;
  public int f;
  public char action;
  public Node parent;

  Node(Point p, Node parent) {
    this.p = p;
    this.g = 0;
    this.f = 0;
    this.h = 0;
    this.parent = parent;
  }

  Node(Point p, Node parent, char action) {
    this(p, parent);
    this.action = action;
  }

  @Override
  public String toString() {
    return p.toString() + "\nG: " + g + "\nH: " + h + "\nF: " + f + "\nACTION: " + action;
  }
}
