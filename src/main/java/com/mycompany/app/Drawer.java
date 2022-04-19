package com.mycompany.app;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawer extends JPanel implements ICollider, KeyListener, Actions {
  private static final long serialVersionUID = -2951430038089172773L;

  public static final int WIDTH = 32, HEIGHT = 21;

  Character[][] drawerState = new Character[HEIGHT][WIDTH];
  Snake snake;
  Food food;

  Drawer() {
    setIgnoreRepaint(true);

    cleanDrawer();
    snake = new Snake(drawerState);
    food = new Food(snake);

    snake.findPath(food.point);

  }

  boolean a = true;

  @Override
  public void paint(Graphics g) {
    // super.paint(g);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH * 100, HEIGHT * 100);

    if (App.IA) {
      if (!snake.path.isEmpty()) {
        Node action = snake.path.pop();
        snake.changeAction(action.action);
        for (Node node : snake.path)
          drawerState[node.p.col][node.p.row] = 'P';
      } else
        snake.findPath(food.point);
    }

    if (snake.head.location.row == food.point.row && snake.head.location.col == food.point.col
        || (food.point.row == -1 && food.point.col == -1)) {
      snake.eat();
      food.generateNewFoodCoord();
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

}
