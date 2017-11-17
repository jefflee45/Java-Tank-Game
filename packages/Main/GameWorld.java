package Main;

import javax.swing.*;
import javax.swing.JFrame;

public class GameWorld extends JApplet implements Runnable{
  
  public static void main(String[] args)
  {
    JFrame window = new JFrame("Tank Game");
    window.setContentPane(new GamePanel());
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.pack();
    window.setVisible(true);
  }

  @Override
  public void run()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
