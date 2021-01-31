package com.mycompany.app;

import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App extends JFrame {
    private static final long serialVersionUID = 1L;

    App() throws InterruptedException {
        setLocationRelativeTo(null);
        Drawer d = new Drawer();
        setSize(Drawer.WIDTH * 10, Drawer.HEIGHT * 10);
        addKeyListener(d);
        add(d);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true) {
            d.repaint();
           Thread.sleep(100);
        }
    }

    public static void main(String[] args) {
        try {
            
        new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
