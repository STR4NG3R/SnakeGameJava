package com.mycompany.app;

import java.util.ArrayList;

public class Snake implements Actions, ICollider {
    Body head;
    ArrayList<Body> body = new ArrayList<>();

    public Snake() {
        head = new Body();
        head.action = RIGHT;
        head.location.x = Drawer.WIDTH / 2;
        head.location.y = Drawer.HEIGHT / 2;
        body.add(head);
    }

    public void eat() {
        Body b = new Body();
        Body tail = body.get(body.size() - 1);
        b.action = tail.action;
        b.location.x = tail.location.x;
        b.location.y = tail.location.y;
        movePiece(b, -1);
        body.add(b);
    }

    public void movePiece(Body current, Body next) {
        current.location.x = next.location.x;
        current.location.y = next.location.y;
    }

    public void movePiece(Body current, int action) {
        if (current.action == UP) {
            current.location.y += action;
        } else if (current.action == DOWN) {
            current.location.y -= action;
        } else if (current.action == LEFT) {
            current.location.x -= action;
        } else if (current.action == RIGHT) {
            current.location.x += action;
        }
        System.out.println(current.location);
    }

    public void changeAction(int action) {
        boolean invalidMove = head.action == UP && action == DOWN || head.action == LEFT && action == RIGHT
                || head.action == RIGHT && action == LEFT || head.action == DOWN && action == UP;
        if (!invalidMove) {
            head.action = action;
        }
    }

    public void move() {
        for (int i = body.size(); i-- > 1;) {
                Body next = body.get(i - 1);
                movePiece(body.get(i), next);
                // eatBody = head.location.x == next.location.x && head.location.y == next.location.y;
                // if (eatBody){
                //     System.out.println("Im cannibal");
                //     return;
                //  }
        }
        movePiece(body.get(0), 1);
    }

    boolean eatBody;

    public boolean isCanibal() {
        return eatBody;
    }
}
