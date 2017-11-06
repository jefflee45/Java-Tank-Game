package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

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
