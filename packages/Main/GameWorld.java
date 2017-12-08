package Main;

import TankGame.MusicPlayer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.JFrame;

public class GameWorld extends JApplet implements Runnable{
  
  public static void main(String[] args)
  {

//    JFrame window = new JFrame("Tank Game");
//    //window.setContentPane(new GamePanel());
//    window.setLocation(100, 100);
//    window.setSize(new Dimension(1024, 800));
//
//    GridLayout layout = new GridLayout(1, 2);
//    window.setLayout(layout);
//
//    JPanel jp = new GamePanel();
//    //jp.setBackground(new Color(0x00FF00FF));
//
//    JPanel jp2 = new GamePanel();
//    //jp2.setBackground(new Color(0x00000000));
//
//    window.add(jp);
//    window.add(jp2);
//    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    window.setResizable(false);
//    window.pack();
//    window.setVisible(true);
      
      GamePanel game = new GamePanel();
      ThreadPool pool = new ThreadPool(2);
    JFrame window = new JFrame("Tank Game");
    //window.setContentPane(new GamePanel());
    window.add(game);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.pack();
    window.setVisible(true);
    window.setLocationRelativeTo(null);
    
    MusicPlayer musicPlayer = new MusicPlayer("Music.mid");
    
    pool.runTask(game);
    pool.runTask(musicPlayer);
    pool.join();//runs both thread at same time
  }

  @Override
  public void run()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
