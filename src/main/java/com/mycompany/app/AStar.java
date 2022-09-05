package com.mycompany.app;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class AStar implements Actions {
  boolean finded;
  int depth = 0;
  boolean findShortPath;
  boolean findOurTail;

  final int MAX_NUMBER_ITERATIONS = Drawer.COLS_N * Drawer.ROWS_N;
  Character[][] drawerState;
  Stack<Node> path;

  AStar(Character[][] _drawerState, Snake snake) {
    drawerState = _drawerState;
    path = new Stack<Node>();
  }

  void lookAtDirection(int _row, int _col, int rRow, int rCol, char action, Node parent,
      PriorityQueue<Node> openSet, HashMap<String, Node> closedSet, Node endNode) {
    // try {
    int row = _row + rRow;
    int col = _col + rCol;

    if (parent.snake.drawer.itsOutsideMap(row, col))
      return;

    if (parent.snake.isInvalidMove(action))
      return;

    // var s = (Snake) parent.snake.clone();
    // s.changeAction(action);
    // s.move();

    if (this.findOurTail) {
      if ((row != endNode.p.row && col != endNode.p.col) && parent.snake.drawer.drawerState[row][col] == 'S')
        return;
    } else if (parent.snake.drawer.drawerState[row][col] == 'S')
      return;

    // if (depth == 1 && snake.isInvalidMove(action))
    // return;

    Node child = new Node(new Point(row, col), parent, action);
    child.snake = parent.snake;

    if (checkIfExistOnClosedSet(closedSet, child))
      return;

    child.g = parent.g + 1;
    child.h = calculateDistance(child.p, endNode.p);
    child.f = child.g + child.h;
    child.action = action;

    if (checkIfExistOnOpenSet(openSet, child))
      return;

    openSet.add(child);
    // } catch (CloneNotSupportedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
  }

  void seeNearNodes(Point p, Node parent, HashMap<String, Node> closedSet,
      PriorityQueue<Node> openSet, Node endNode) {
    lookAtDirection(p.row, p.col, 1, 0, UP, parent, openSet, closedSet, endNode);
    lookAtDirection(p.row, p.col, -1, 0, DOWN, parent, openSet, closedSet, endNode);
    lookAtDirection(p.row, p.col, 0, 1, RIGHT, parent, openSet, closedSet, endNode);
    lookAtDirection(p.row, p.col, 0, -1, LEFT, parent, openSet, closedSet, endNode);
  }

  int calculateDistance(Point startNode, Point endNode) {
    int a = Math.abs(startNode.col - endNode.col);
    int b = Math.abs(startNode.row - endNode.row);
    return a * a + b * b;
  }

  Stack<Node> findPath(Point start, Point end, boolean findShortPath, boolean findOurTail, Snake snake) {
    System.out.println("START: " + start + " END: " + end);
    path.clear();
    this.findShortPath = findShortPath;
    this.findOurTail = findOurTail;

    var closedSet = new HashMap<String, Node>();
    var openSet = new PriorityQueue<Node>((Node n1, Node n2) -> this.findShortPath ? n1.f - n2.f : n2.f - n1.f);
    var startNode = new Node(start, null, snake);
    var endNode = new Node(end, null);

    depth = 0;
    finded = false;
    openSet.add(startNode);

    while (!openSet.isEmpty() && (depth++ < 1000 && this.findShortPath)) {
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
        seeNearNodes(currentNode.p, currentNode, closedSet, openSet, endNode);
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

  void countNodes(int x, int y, AtomicInteger counter, final int maxIterations) {
    if (counter.get() >= maxIterations)
      return;

    // seeNearNodes(x, y,
    // (context) -> {
    // int col = context.p.row + context.rx, row = context.p.col + context.ry;

    // if (snake.drawer.itsOutsideMap(row, col))
    // return;

    // if (drawerState[row][col] == 'S')
    // return;

    // if (drawerState[row][col] == 'E')
    // counter.incrementAndGet();

    // if (counter.get() >= maxIterations)
    // return;

    // countNodes(row, col, counter, maxIterations);
    // });
  }

}
