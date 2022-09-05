package com.mycompany.app;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Snake implements Actions, ICollider, Cloneable {
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
    head.action = RIGHT;
    spawn();
    body.add(head);
    isAlive = true;
    astar = new AStar(drawer.drawerState, this);

    for (int i = 0; i < initSize; i++)
      body.add(new Body(head.action, new Point(head.location.row, head.location.col - 1)));
  }

  private void spawn() {
    spawn(Drawer.COLS_N / 2, Drawer.ROWS_N / 2);
  }

  private void spawn(int row, int col) {
    head.location.row = row;
    head.location.col = col;
  }

  public void isEating() {
    if (head.location.equals(food.point)) {
      Body tail = body.get(body.size() - 1);
      Body b = new Body(tail.action, new Point(tail.location.row, tail.location.col));
      body.add(b);
      food.generateNewFoodCoord(drawer);
      findPath(food.point);
    }
  }

  public void movePiece(Body current, Body next, boolean firstIteration) {
    if (firstIteration)
      drawer.drawerState[current.location.row][current.location.col] = 'E';
    movePiece(next);
    current.location.row = next.location.row;
    current.location.col = next.location.col;
  }

  public void movePiece(Body current) {
    drawer.drawerState[head.location.row][head.location.col] = 'S';
  }

  public boolean moveHead() {
    moveCoords(head);
    itsOutsideMap();
    if (isEatingBody()) {
      die();
      return false;
    }
    isEating();
    movePiece(head);
    return true;
  }

  public void changeAction(char action) {
    if (!isInvalidMove(action))
      head.action = action;
    else
      System.out.println("-------------------INVALID MOVE--------------");
  }

  public boolean isInvalidMove(int action) {
    if (action == head.action)
      return false;
    return head.action == UP && action == DOWN || head.action == LEFT && action == RIGHT
        || head.action == RIGHT && action == LEFT || head.action == DOWN && action == UP;
  }

  public boolean move() {
    if (head.action == 'Z')
      return false;

    boolean firstIteration = true;
    for (int i = body.size(); i-- > 1;) {
      Body next = body.get(i - 1);
      movePiece(body.get(i), next, firstIteration);
      firstIteration = false;
    }

    boolean validMove = moveHead();
    drawer.printDrawer();
    return validMove;
  }

  public void print(Graphics g) {
    g.setColor(Color.BLUE);
    body.stream().parallel().forEach(b -> g.fillRect(b.location.col * 10, b.location.row * 10, 10, 10));
  }

  public boolean isEatingBody() {
    return body.stream().parallel().filter(b -> b.equals(head)).count() == 2;
  }

  public void die() {
    System.out.println("-----------------SNAKE DIE--------------------");
    // App.RUNNING = false;
  }

  public void findPath(Point food) {
    if (food.col == -1 && food.row == -1)
      return;

    System.out.println("FOOT COORDS " + food);
    System.out.println("FIND FOOD");
    astar.findPath(head.location, food, true);
    if (!astar.finded) {
      System.out.println("FIND TAIL");
      astar.findPath(head.location, body.get(body.size() - 1).location, false);
    }
    if (!astar.findShortPath)
      System.out.println("NOT FINDED");
  }

  public void printPath(Graphics g) {
    g.setColor(Color.YELLOW);
    astar.path.stream().parallel().forEach(b -> g.fillRect(b.p.col * 10, b.p.row * 10, 10, 10));
  }

  private void moveCoords(Body current) {
    if (current.action == DOWN)
      current.location.row -= 1;
    else if (current.action == UP)
      current.location.row += 1;
    else if (current.action == LEFT)
      current.location.col -= 1;
    else if (current.action == RIGHT)
      current.location.col += 1;
  }

  private void itsOutsideMap() {
    drawer.checkIfNotInMap(head.location.row, head.location.col);
  }

  public Object clone() throws CloneNotSupportedException {
    Snake s = (Snake) super.clone();
    s.food = food;
    s.head = (Body) head.clone();
    s.drawer = (Drawer) drawer.clone();
    s.body = body.stream().map((Body b) -> {
      try {
        return (Body) b.clone();
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
        return null;
      }
    }).collect(Collectors.toCollection(ArrayList::new));
    return s;
  }

}
