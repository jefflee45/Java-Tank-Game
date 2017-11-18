package Main;

import GameState.GameStateManager;
import TankGame.GameEvents;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

  //dimensions
  public static final int WIDTH = 540;
  public static final int HEIGHT = 480;
  public static final int SCALE = 2;
  public static final int FULL_WIDTH = 1080;
  public static final int FULL_HEIGHT = 480;

  //game thread
  private Thread thread;
  private boolean running;
  private final int FPS = 60;
  private final long targetTime = 1000/FPS;

  //image
  private BufferedImage leftScreen;
  private BufferedImage rightScreen;
  private Graphics2D gLeftScreen;
  private Graphics2D gRightScreen;
  
  //game state manager
  private GameStateManager gsm;
  
  GameEvents gameEvent1, gameEvent2;

  public GamePanel() {
    super();
    setPreferredSize(new Dimension(FULL_WIDTH * SCALE, FULL_HEIGHT * SCALE));
    setFocusable(true);
    requestFocus();
  }
  
  @Override
  public void addNotify() {
    super.addNotify();
    
    if(thread == null) {
      thread = new Thread(this);
      addKeyListener(this);
      thread.start();
    }
  }
  
  private void init() {
    leftScreen = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    rightScreen = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    gLeftScreen = (Graphics2D) leftScreen.getGraphics();
    gRightScreen = (Graphics2D) rightScreen.getGraphics();
    running = true;
    
    gsm = new GameStateManager();
  }
  
  //game loop
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
      //TODO add targetTime
      wait = targetTime - elapsed/1000000;
      
      try {
        if (wait < 0) {
          wait = 5;
        }
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
    gsm.draw(gLeftScreen, gRightScreen);
   }
  
  private void drawToScreen() {
    Graphics g2 = getGraphics();
    g2.drawImage(leftScreen, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
    g2.drawImage(rightScreen, FULL_WIDTH - 1, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
    g2.dispose();
  }


  @Override
  public void keyTyped(KeyEvent key)
  {
    
  }

  @Override
  public void keyPressed(KeyEvent key)
  {
    gsm.keyPressed(key.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent key)
  {
    gsm.keyReleased(key.getKeyCode());
  }
}
