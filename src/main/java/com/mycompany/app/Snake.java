package com.mycompany.app;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Snake implements Actions, ICollider {
  Body head;
  ArrayList<Body> body = new ArrayList<>();
  Character[][] drawer;
  boolean isAlive;
  AStar astar;
  Stack<Node> path;

  public Snake(Character[][] _drawer) {
    drawer = _drawer;
    head = new Body();
    head.action = App.IA ? DOWN : 'Z';
    head.location.row = Drawer.WIDTH / 2;
    head.location.col = Drawer.HEIGHT / 2;
    body.add(head);
    isAlive = true;
    astar = new AStar(_drawer, this);
  }

  public void eat() {
    Body tail = body.get(body.size() - 1);
    Body b = new Body(tail.action, new Point(tail.location.row, tail.location.col));
    body.add(b);
  }

  public void movePiece(Body current, Body next) {
    drawer[next.location.col][next.location.row] = 'S';
    current.location.row = next.location.row;
    current.location.col = next.location.col;
  }

  void moveCoords(Body current, int a) {
    if (current.action == DOWN)
      current.location.col -= a;
    else if (current.action == UP)
      current.location.col += a;
    else if (current.action == LEFT)
      current.location.row -= a;
    else if (current.action == RIGHT)
      current.location.row += a;
  }

  public void movePiece(Body current) {
    moveCoords(current, 1);
    // Rollback
    if (itsOutsideMap())
      moveCoords(head, -1);
    drawer[current.location.col][current.location.row] = 'S';
  }

  public void changeAction(char action) {
    if (!isInvalidMove(action)) {
      System.out.println("VALID MOVE");
      head.action = action;
    } else
      System.out.println(
          "***********************************************INVALID MOVE**********************************************");
  }

  public boolean isInvalidMove(int action) {
    return head.action == UP && action == DOWN || head.action == LEFT && action == RIGHT
        || head.action == RIGHT && action == LEFT || head.action == DOWN && action == UP;
  }

  public void move() {
    if (head.action == 'Z')
      return;
    for (int i = body.size(); i-- > 1;) {
      Body next = body.get(i - 1);
      movePiece(body.get(i), next);
    }

    // isEatingBody();

    movePiece(head);

    System.out.println("---------------------Drawer----------------------");
    for (int i = 0; i < drawer.length; i++) {
      for (int j = 0; j < drawer[0].length; j++) {
        System.out.print(drawer[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println("END---------------------Drawer----------------------END");
  }

  private boolean itsOutsideMap() {
    int row = head.location.row, col = head.location.col;
    if (!(((col >= 0) && (col < Drawer.WIDTH)) && ((row >= 0) && (row < Drawer.HEIGHT))))
      return true;
    return false;
    // die();
  }

  public void print(Graphics g) {
    g.setColor(Color.WHITE);
    for (Body b : body)
      g.fillRect(b.location.row * 10, b.location.col * 10, 10, 10);
  }

  public void isEatingBody() {
    for (int i = 1; i < body.size(); i++) {
      Body pieceOfBody = body.get(i);
      int x = pieceOfBody.location.row;
      int y = pieceOfBody.location.col;
      if (head.location.row == x && head.location.col == y) {
        die();
      }
    }
  }

  public void die() {
    App.RUNNING = false;
    isAlive = false;
  }

  public void findPath(Point food) {
    if (food.col != -1)
      path = astar.findPath(head.location, food);
    System.out.println("Calculated path" + path.size());
  }

  public void printPath(Graphics g) {
    g.setColor(Color.YELLOW);
    for (Iterator<Node> it = path.iterator(); it.hasNext();) {
      Node b = it.next();
      System.out.println(b);
      g.fillRect(b.p.row * 10, b.p.col * 10, 10, 10);
    }
  }
}
