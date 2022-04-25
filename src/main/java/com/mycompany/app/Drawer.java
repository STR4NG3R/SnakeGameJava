package com.mycompany.app;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawer extends JPanel implements ICollider, KeyListener, Actions {
  public static final int WIDTH = 32, HEIGHT = 21;

  Character[][] drawerState = new Character[HEIGHT][WIDTH];
  Snake snake;
  Food food;

  Drawer() {
    // setIgnoreRepaint(true);

    cleanDrawer();
    food = new Food();
    snake = new Snake(this, food, 3);

    food.setSnake(snake);
    food.generateNewFoodCoord();
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH * 100, HEIGHT * 100);

    if (App.IA) {
      if (!snake.astar.path.isEmpty()) {
        Node action = snake.astar.path.pop();
        snake.changeAction(action.action);
      } else
        snake.findPath(food.point);
    }

    snake.move();

    snake.printPath(g);
    snake.print(g);
    food.print(g);

    // if (!snake.isAlive)
    // System.exit(0);
    cleanDrawer();
  }

  void cleanDrawer() {
    for (int i = 0; i < WIDTH; i++)
      for (int j = 0; j < HEIGHT; j++)
        drawerState[j][i] = 'E';
  }

  @Override
  public void keyPressed(KeyEvent evt) {
    if (App.IA)
      return;
    int code = evt.getKeyCode();
    Logger.log("Move to: " + code);
    switch (code) {
      case KeyEvent.VK_DOWN:
        snake.changeAction(UP);
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
    System.out.println("---------------------Drawer----------------------");
    for (int i = 0; i < drawerState.length; i++) {
      for (int j = 0; j < drawerState[0].length; j++) {
        System.out.print(drawerState[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println("END---------------------Drawer----------------------END");
  }
}
