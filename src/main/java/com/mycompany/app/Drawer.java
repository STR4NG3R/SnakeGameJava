package com.mycompany.app;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawer extends JPanel implements ICollider, KeyListener, Actions {
    private static final long serialVersionUID = -2951430038089172773L;

    public static final int WIDTH = 32, HEIGHT = 16;

    Snake snake = new Snake();
    Food food = new Food();

    Drawer() {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH * 100, HEIGHT * 100);
        /*if (snake.head.location.x >= WIDTH || snake.head.location.x <= 0 || snake.head.location.y <= 0
                || snake.head.location.y >= HEIGHT) {

        } else */if (snake.head.location.x == food.point.x && snake.head.location.y == food.point.y) {
            snake.eat();
            food.generateNewFoodCoord();
        } else if (snake.isCanibal()) {
        }

        snake.move();
        g.setColor(Color.WHITE);
        for (Body b : snake.body) {
            System.out.println("Printing snake: "+ b.location);
            g.fillRect(b.location.x * 10, b.location.y * 10, 10, 10);
        }
        g.setColor(Color.RED);
        g.fillRect(food.point.x * 10, food.point.y * 10, 10, 10);
        System.out.println("Painting");
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        int code = evt.getKeyCode();
        System.out.println("Move to: " + code);
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
