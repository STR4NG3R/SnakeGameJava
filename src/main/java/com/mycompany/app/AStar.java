package com.mycompany.app;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

class DtoConsumer {
  HashMap<String, Node> closedSet;
  Node endNode, parent;
  PriorityQueue<Node> openSet;
  int rx, ry;
  Point p;
  char action;

  DtoConsumer(HashMap<String, Node> closedSet, Node endNode, PriorityQueue<Node> openSet, Point p, int rx,
      int ry, char action, Node parent) {
    this.endNode = endNode;
    this.closedSet = closedSet;
    this.openSet = openSet;
    this.p = p;
    this.rx = rx;
    this.ry = ry;
    this.action = action;
    this.parent = parent;
  }

  DtoConsumer(int row, int col, int rx, int ry) {
    this.p = new Point(row, col);
    this.rx = rx;
    this.ry = ry;
  }
}

public class AStar implements Actions {
  boolean finded;
  int depth = 0;
  boolean findShortPath;

  final int MAX_NUMBER_ITERATIONS = Drawer.COLS_N * Drawer.ROWS_N;
  Character[][] drawerState;
  Snake snake;
  Stack<Node> path;

  AStar(Character[][] _drawerState, Snake snake) {
    drawerState = _drawerState;
    this.snake = snake;
    path = new Stack<Node>();
  }

  void lookAtDirection(Point p, int rx, int ry, char action, Node parent,
      PriorityQueue<Node> openSet, HashMap<String, Node> closedSet, Node endNode, Consumer<DtoConsumer> callback) {
    callback.accept(new DtoConsumer(closedSet, endNode, openSet, p, rx, ry, action, parent));
  }

  void seeNearNodes(Point p, Node parent, HashMap<String, Node> closedSet,
      PriorityQueue<Node> openSet, Node endNode, Snake snake, Consumer<DtoConsumer> callback) {
    lookAtDirection(p, 0, 1, RIGHT, parent, openSet, closedSet, endNode, callback);
    lookAtDirection(p, 0, -1, LEFT, parent, openSet, closedSet, endNode, callback);
    lookAtDirection(p, 1, 0, UP, parent, openSet, closedSet, endNode, callback);
    lookAtDirection(p, -1, 0, DOWN, parent, openSet, closedSet, endNode, callback);
  }

  int calculateDistance(Point startNode, Point endNode) {
    int a = Math.abs(startNode.col - endNode.col);
    int b = Math.abs(startNode.row - endNode.row);
    return a * a + b * b;
  }

  Stack<Node> findPath(Point start, Point end, boolean findShortPath) {
    path.clear();
    this.findShortPath = findShortPath;
    // if (findShortPath)
    // this.findShortPath = this.snake.body.size() < 10 ? true : findShortPath;

    var closedSet = new HashMap<String, Node>();
    var openSet = new PriorityQueue<Node>((Node n1, Node n2) -> this.findShortPath ? n1.f - n2.f : n2.f - n1.f);
    var startNode = new Node(start, null, snake);
    var endNode = new Node(end, null);

    depth = 0;
    finded = false;
    openSet.add(startNode);

    while (!openSet.isEmpty() && depth++ < 1000) {
      Node currentNode = openSet.poll();
      closedSet.put(currentNode.p.row + "-" + currentNode.p.col, currentNode);

      if (currentNode.p.equals(end)) {
        finded = true;
        Node current = currentNode;
        while (current != null) {
          path.push(current);
          current = current.parent;
        }
        break;
      } else
        seeNearNodes(currentNode.p, currentNode, closedSet, openSet, endNode, snake,
            (context) -> {
              int col = context.p.row + context.rx, row = context.p.col + context.ry;

              if (snake.drawer.itsOutsideMap(row, col))
                return;

              if (drawerState[row][col] == 'S')
                return;

              if (depth == 1 && snake.isInvalidMove(context.action))
                return;

              Node child = new Node(new Point(col, row), context.parent, context.p.action);

              if (checkIfExistOnClosedSet(context.closedSet, child))
                return;

              // child.snake = (Snake) context.parent.snake.clone();
              // child.snake.changeAction(context.action);
              // child.snake.move();

              child.g = context.parent.g + 1;
              child.h = calculateDistance(child.p, context.endNode.p);
              child.f = child.g + child.h;
              child.action = context.action;

              if (checkIfExistOnOpenSet(context.openSet, child))
                return;

              context.openSet.add(child);
            });
    }
    return path;

  }

  boolean checkIfExistOnClosedSet(HashMap<String, Node> closedSet, Node element) {
    return closedSet.containsKey(element.p.row + "-" + element.p.col);
  }

  boolean checkIfExistOnOpenSet(PriorityQueue<Node> list, Node element) {
    for (Node current : list)
      if (current.p.equals(element.p))
        return this.findShortPath ? element.f <= current.f : element.f >= current.f;
    return false;
  }

  boolean getReachableDrawer() {
    int maxIterations = ((this.snake.body.size() - MAX_NUMBER_ITERATIONS) * 80) / 100;
    AtomicInteger counter = new AtomicInteger(0);
    this.countNodes(snake.head.location.row, snake.head.location.col, counter, maxIterations);
    return counter.get() >= maxIterations;
  }

  void countNodes(int x, int y, AtomicInteger counter, final int maxIterations) {
    if (counter.get() >= maxIterations)
      return;

    seeNearNodes(x, y,
        (context) -> {
          int col = context.p.row + context.rx, row = context.p.col + context.ry;

          if (snake.drawer.itsOutsideMap(row, col))
            return;

          if (drawerState[row][col] == 'S')
            return;

          if (drawerState[row][col] == 'E')
            counter.incrementAndGet();

          if (counter.get() >= maxIterations)
            return;

          countNodes(row, col, counter, maxIterations);
        });
  }

  void seeNearNodes(int row, int col, Consumer<DtoConsumer> callback) {
    lookAtDirection(row, col, 1, 0, callback);
    lookAtDirection(row, col, 1, 0, callback);
    lookAtDirection(row, col, -1, 0, callback);
    lookAtDirection(row, col, 0, 1, callback);
    lookAtDirection(row, col, 0, -1, callback);
  }

  private void lookAtDirection(int row, int col, int rx, int ry, Consumer<DtoConsumer> callback) {
    callback.accept(new DtoConsumer(row, col, rx, ry));
  }
}
