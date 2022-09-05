package com.mycompany.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Hello world!
 *
 */
public class App extends JFrame {
  private static final long serialVersionUID = 1L;
  public static boolean RUNNING = true;
  final Timer timer = new Timer(100, null);
  public static boolean IA = true;

  App() throws InterruptedException {
    setLocationRelativeTo(null);
    Drawer d = new Drawer();
    setSize(Drawer.COLS_N * 10, Drawer.ROWS_N * 10);
    addKeyListener(d);
    add(d);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    timer.addActionListener(e -> {
      // if (!this.isVisible())
      // timer.stop();
      if (RUNNING)
        d.repaint();
    });

    timer.start();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        new App();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

}
