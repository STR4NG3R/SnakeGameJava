package com.mycompany.app;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snake implements Actions, ICollider {
  Body head;
  ArrayList<Body> body = new ArrayList<>();
  Drawer drawer;
  boolean isAlive;
  AStar astar;
  Food food;

  public Snake(Drawer _drawer, Food food, int initSize) {
    this.food = food;
    drawer = _drawer;
    head = new Body();
    head.action = App.IA ? DOWN : 'Z';
    head.location.row = Drawer.WIDTH / 2;
    head.location.col = Drawer.HEIGHT / 2;
    body.add(head);

    for (int i = 0; i < initSize; i++) {
      var piece = new Body(head.action, new Point(head.location.row,
          head.location.col - 1));
      body.add(piece);
    }

    isAlive = true;
    astar = new AStar(drawer.drawerState, this);
  }

  public void isEating() {
    if (head.location.equals(food.point)) {
      Body tail = body.get(body.size() - 1);
      Body b = new Body(tail.action, new Point(tail.location.row, tail.location.col));
      body.add(b);
      food.generateNewFoodCoord();
      findPath(food.point);
    }
  }

  public void movePiece(Body current, Body next) {
    drawer.drawerState[next.location.col][next.location.row] = 'S';
    current.location.row = next.location.row;
    current.location.col = next.location.col;
  }

  void moveCoords(Body current) {
    if (current.action == DOWN)
      current.location.col -= 1;
    else if (current.action == UP)
      current.location.col += 1;
    else if (current.action == LEFT)
      current.location.row -= 1;
    else if (current.action == RIGHT)
      current.location.row += 1;
  }

  public void moveHead() {
    moveCoords(head);
    if (itsOutsideMap() || isEatingBody()) {
      die();
      return;
    }
    isEating();
    drawer.drawerState[head.location.col][head.location.row] = 'S';
  }

  public void changeAction(char action) {
    if (!isInvalidMove(action))
      head.action = action;
  }

  public boolean isInvalidMove(int action) {
    boolean valid = head.action == UP && action == DOWN || head.action == LEFT && action == RIGHT
        || head.action == RIGHT && action == LEFT || head.action == DOWN && action == UP;
    if (!valid)
      System.out.println("-------------------INVALID MOVE--------------");
    return valid;
  }

  public void move() {
    if (head.action == 'Z')
      return;

    for (int i = body.size(); i-- > 1;) {
      Body next = body.get(i - 1);
      movePiece(body.get(i), next);
    }

    moveHead();
    drawer.printDrawer();
  }

  private boolean itsOutsideMap() {
    int row = head.location.row, col = head.location.col;
    return drawer.itsOutsideMap(row, col);
  }

  public void print(Graphics g) {
    g.setColor(Color.WHITE);
    for (Body b : body)
      g.fillRect(b.location.row * 10, b.location.col * 10, 10, 10);
  }

  public boolean isEatingBody() {
    return body.stream().filter(b -> b.equals(head)).count() == 2;
  }

  public void die() {
    System.out.println("-----------------SNAKE DIE--------------------");
    // App.RUNNING = false;
  }

  public void findPath(Point food) {
    if (food.col == -1 && food.row == -1)
      return;
    astar.findPath(head.location, food);
    for (Node node : astar.path)
      drawer.drawerState[node.p.col][node.p.row] = 'P';
    System.out.println("Calculated path" + astar.path.size());
  }

  public void printPath(Graphics g) {
    System.out.println("-----------------CURRENT ACTION SNAKE-----------------");
    g.setColor(Color.YELLOW);
    for (var it = astar.path.iterator(); it.hasNext();) {
      Node b = it.next();
      System.out.println(b);
      g.fillRect(b.p.row * 10, b.p.col * 10, 10, 10);
    }
  }
}
