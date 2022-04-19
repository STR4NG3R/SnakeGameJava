package com.mycompany.app;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar implements Actions {
  int depth = 0;

  final int MAX_NUMBER_ITERATIONS = Drawer.WIDTH * Drawer.HEIGHT;
  Character[][] drawerState;
  Snake snake;

  AStar(Character[][] _drawerState, Snake snake) {
    drawerState = _drawerState;
    this.snake = snake;
  }

  void lookAtDirection(int x, int y, int rx, int ry, char action, Node parent,
      PriorityQueue<Node> openSet, HashMap<String, Node> closedSet, Node endNode) {
    int col = x + rx, row = y + ry;

    if (!(((col >= 0) && (col < Drawer.WIDTH)) && ((row >= 0) && (row < Drawer.HEIGHT))))
      return;

    System.out.println(drawerState[row][col]);

    if (drawerState[row][col] == 'S')
      return;

    if (depth == 1 && snake.isInvalidMove(action))
      return;

    Node child = new Node(new Point(col, row), parent, action);
    if (checkIfExistOnClosedSet(closedSet, child))
      return;

    child.g = parent.g + 1;
    child.h = calculateDistance(child.p, endNode.p);
    child.f = child.g + child.h;
    child.action = action;

    if (checkIfExistOnOpenSet(openSet, child))
      return;

    openSet.add(child);
  }

  void seeNearNodes(Point p, Node parent, HashMap<String, Node> closedSet,
      PriorityQueue<Node> openSet, Node endNode) {
    lookAtDirection(p.row, p.col, 1, 0, RIGHT, parent, openSet, closedSet, endNode);
    lookAtDirection(p.row, p.col, -1, 0, LEFT, parent, openSet, closedSet, endNode);
    lookAtDirection(p.row, p.col, 0, -1, DOWN, parent, openSet, closedSet, endNode);
    lookAtDirection(p.row, p.col, 0, 1, UP, parent, openSet, closedSet, endNode);
  }

  int calculateDistance(Point startNode, Point endNode) {
    int a = Math.abs(startNode.col - endNode.col);
    int b = Math.abs(startNode.row - endNode.row);
    return a * a + b * b;
  }

  Stack<Node> findPath(Point start, Point end) {
    Stack<Node> path = new Stack<Node>();
    HashMap<String, Node> closedSet = new HashMap<String, Node>();
    PriorityQueue<Node> openSet = new PriorityQueue<Node>((Node n1, Node n2) -> n1.f - n2.f);

    Node startNode = new Node(start, null);
    Node endNode = new Node(end, null);
    depth = 0;
    openSet.add(startNode);

    while (!openSet.isEmpty() && depth++ < MAX_NUMBER_ITERATIONS) {
      Node currentNode = openSet.poll();
      closedSet.put(currentNode.p.row + "-" + currentNode.p.col, currentNode);

      if (currentNode.p.row == endNode.p.row && currentNode.p.col == endNode.p.col) {
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
      if (current.p.row == element.p.row && current.p.col == element.p.col)
        return element.f <= current.f;
    return false;
  }
}
