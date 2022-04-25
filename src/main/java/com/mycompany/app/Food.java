package com.mycompany.app;

import java.awt.Color;
import java.awt.Graphics;

public class Food {
  public Point point = new Point();
  Snake snake;

  public void setSnake(Snake snake) {
    this.snake = snake;
  }

  Food() {
  }

  public void print(Graphics g) {
    g.setColor(Color.RED);
    g.fillOval(point.row * 10, point.col * 10, 11, 11);
  }

  int generateRandomCoord(int upperNumber) {
    return (int) Math.floor(Math.random() * (upperNumber));
  }

  public void generateNewFoodCoord() {
    int row = generateRandomCoord(10), col = generateRandomCoord(10);
    boolean isSnake = snake.drawer.drawerState[row][col] == 'S';
    if (isSnake) {
      point.row = point.col = -1;
    } else {
      point.row = col;
      point.col = row;
    }
  }
}
