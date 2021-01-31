package com.mycompany.app;

public class Food {
    public Point point = new Point();

    Food() {
        generateNewFoodCoord();
    }

    public void generateNewFoodCoord() {
        int x = (int) ((Math.random() * Drawer.WIDTH - 2) + 2);
        int y = (int) ((Math.random() * Drawer.HEIGHT - 2) + 2);
        point.x = x;
        point.y = y;
    }
}
