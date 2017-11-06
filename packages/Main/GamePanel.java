package Main;

import GameState.GameStateManager;
import TankGame.GameEvents;
import TankGame.KeyControlled;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable {

  //dimensions
  public static final int WIDTH = 320;
  public static final int HEIGHT = 240;
  public static final int SCALE = 2;

  //game thread
  private Thread thread;
  private boolean running;
  private final int FPS = 60;
  private final long targetTime = 1000/FPS;

  //image
  private BufferedImage image;
  private Graphics2D g;
  
  //game state manager
  private GameStateManager gsm;
  
  GameEvents gameEvent1, gameEvent2;

  public GamePanel() {
    super();
    setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
    setFocusable(true);
    requestFocus();
  }
  
  @Override
  public void addNotify() {
    super.addNotify();
    
    if(thread == null) {
      thread = new Thread(this);
      //addKeyListener(this)
      thread.start();
    }
  }
  
  private void init() {
    image = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    g = (Graphics2D) g;
    running = true;
    
    gsm = new GameStateManager();
    
    gameEvent1 = new GameEvents();
    gameEvent1 = new GameEvents();
    KeyControlled key1 = new KeyControlled(gameEvent1);
    KeyControlled key2 = new KeyControlled(gameEvent2);
    
    
  }
  
  @Override
  public void run() {
    init();
    
    long start;
    long elapsed;
    long wait;
    
    while(running) {
      
      start = System.nanoTime();
      update();
      draw();
      drawToScreen();
      
      elapsed = System.nanoTime() - start;
      wait = targetTime - elapsed/1000000;
      
      try {
        Thread.sleep(wait);
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
  }
  
  private void update() {
    gsm.update();
  }
  
  private void draw() {
    gsm.draw(g);
   }
  
  private void drawToScreen() {
    Graphics g2 = getGraphics();
    g2.drawImage(image, 0, 0, null);
    g2.dispose();
  }
  
  public void keyTyped(GameEvents event) {
    
  }
  
  public void keyPressed(GameEvents event) {
    try {
    KeyEvent key = (KeyEvent)(event.getEvent());
    gsm.keyPressed(key.getKeyCode());
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void keyReleased(GameEvents event) {
    try {
    KeyEvent key = (KeyEvent)(event.getEvent());
    gsm.keyReleased(key.getKeyCode());
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
