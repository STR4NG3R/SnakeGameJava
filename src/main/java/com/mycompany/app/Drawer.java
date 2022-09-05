package com.mycompany.app;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawer extends JPanel implements ICollider, KeyListener, Actions, Cloneable {
  public static final int COLS_N = 32, ROWS_N = 21;

  Character[][] drawerState = new Character[ROWS_N][COLS_N];
  Snake snake;
  Food food;

  Drawer() {
    setIgnoreRepaint(true);

    cleanDrawer();
    food = new Food();
    snake = new Snake(this, food, 3);

    food.setSnake(snake);
    food.generateNewFoodCoord(this);
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, COLS_N * 100, ROWS_N * 100);

    if (App.IA) {
      if (!snake.astar.path.isEmpty()) {
        Node action = snake.astar.path.pop();
        snake.changeAction(action.action);
      } else {
        snake.changeAction('Z');
        snake.findPath(food.point);
      }
    }

    snake.move();
    snake.printPath(g);
    snake.print(g);
    food.print(g);
  }

  void cleanDrawer() {
    for (int i = 0; i < ROWS_N; i++)
      for (int j = 0; j < COLS_N; j++)
        drawerState[i][j] = 'E';
  }

  void checkIfNotInMap(int row, int col) {
    if (col < 0)
      snake.head.location.col = Drawer.COLS_N - 1;
    else if (col >= Drawer.COLS_N)
      snake.head.location.col = 0;
    else if (row < 0)
      snake.head.location.row = Drawer.ROWS_N - 1;
    else if (row >= Drawer.ROWS_N)
      snake.head.location.row = 0;
  }

  boolean itsOutsideMap(int row, int col) {
    return (col < 0 || col >= Drawer.COLS_N) || (row < 0 || row >= Drawer.ROWS_N);
  }

  @Override
  public void keyPressed(KeyEvent evt) {
    if (App.IA)
      return;
    int code = evt.getKeyCode();
    switch (code) {
      case KeyEvent.VK_DOWN:
        snake.changeAction(UP);
        break;
      case KeyEvent.VK_LEFT:
        snake.changeAction(LEFT);
        break;
      case KeyEvent.VK_UP:
        snake.changeAction(DOWN);
        break;
      case KeyEvent.VK_RIGHT:
        snake.changeAction(RIGHT);
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent arg0) {
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }

  public void printDrawer() {
    // System.out.println("---------------------Drawer----------------------");
    // for (int i = 0; i < HEIGHT; i++) {
    // for (int j = 0; j < WIDTH; j++)
    // System.out.print(drawerState[i][j] + " ");
    // System.out.println();
    // }
    // System.out.println("END---------------------Drawer----------------------END");
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    var d = (Drawer) super.clone();
    d.drawerState = new Character[ROWS_N][COLS_N];

    for (int i = 0; i < ROWS_N; i++)
      for (int j = 0; j < COLS_N; j++)
        d.drawerState[i][j] = drawerState[i][j];

    return d;
  }
}
