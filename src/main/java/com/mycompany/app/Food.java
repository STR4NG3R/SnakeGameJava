package com.mycompany.app;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

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
    g.fillRect(point.col * 10, point.row * 10, 11, 11);
  }

  int generateRandomCoord(int upperNumber) {
    return new Random().nextInt(upperNumber);
  }

  public void generateNewFoodCoord(Drawer drawer) {
    for (;;) {
      System.out.println("GENERATING FOOD");
      // int row = generateRandomCoord(Drawer.ROWS_N), col =
      // generateRandomCoord(Drawer.COLS_N);
      int row = generateRandomCoord(5), col = generateRandomCoord(5);
      if (drawer.itsOutsideMap(row, col))
        continue;
      boolean isSnake = snake.drawer.drawerState[row][col] == 'S';
      point.row = row;
      point.col = col;
      if (!isSnake)
        return;
    }
  }

  // drawer.drawerState[row][col] = 'F';
}
