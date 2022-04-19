package com.mycompany.app;

import java.awt.Color;
import java.awt.Graphics;

public class Food {
  public Point point = new Point();
  private Snake snake;

  Food(Snake _snake) {
    snake = _snake;
    generateNewFoodCoord();
  }

  public void print(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(point.row * 10, point.col * 10, 10, 10);
  }

  int generateRandomCoord(int upperNumber) {
    return (int) Math.floor(Math.random() * (upperNumber));
  }

  public void generateNewFoodCoord() {
    int row = generateRandomCoord(10), col = generateRandomCoord(10);
    boolean isSnake = snake.drawer[row][col] == 'S';
    if (isSnake) {
      point.row = point.col = -1;
    } else {
      point.row = col;
      point.col = row;
    }

  }

  public void generateNewFoodCoord(Graphics g) {
    this.generateNewFoodCoord();
    if (point.row != -1 && point.col != -1) {
      snake.findPath(point);
    }
  }
}
