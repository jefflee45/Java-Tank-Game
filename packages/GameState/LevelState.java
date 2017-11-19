package GameState;

import Main.GamePanel;
import TankGame.Background;
import BlockMap.BlockMap;
import GameObjects.Player;
import TankGame.CollisionDetector;
import TankGame.HUD;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class LevelState extends GameState
{
  private Background p1Bg;
  private Background p2Bg;
  
  private CollisionDetector collisionDetector;
  private Player p1;
  private Player p2;
  
  private HUD hud;
  
  private BlockMap blockMap;
  
  public LevelState(GameStateManager gsm) {
    this.gsm = gsm;
    init();
  }

  @Override
  protected void init()
  {
    try {
       p1Bg = new Background("Resources/BackgroundLarge.bmp");
       p2Bg = new Background("Resources/BackgroundLarge.bmp");
     }
     catch (Exception e) {
       e.printStackTrace();
     }
    
    hud = new HUD("Resources/Desert-Camo.jpg");
    collisionDetector = new CollisionDetector();
    blockMap = new BlockMap();
    blockMap.setPosition(0, 0);
    blockMap.setTween(1);
    p1 = new Player(blockMap, Player.FIRST_PLAYER);
    p2 = new Player(blockMap, Player.SECOND_PLAYER);
    p1.setPosition(184, 434);
    p2.setPosition(300, 480);
  } 

  @Override
  public void update()
  {
    updatePlayer1();
    //collisionDetector.checkCollision(p1, p2);
    updatePlayer2();
  }
  
  public void updatePlayer1() {
    p1.setBlockMapArray(blockMap.getBlockMapArray());
    p1.setOtherPlayer(p2);
    p1.update();
    
    blockMap.setBlockMapArray(p1.getBlockMapArray());

    p1.setBlockMapPosition(GamePanel.WIDTH/2 - p1.getX(),
        GamePanel.HEIGHT/2 - p1.getY());
    p1Bg.setPosition(p1.getBlockMapObject().getX(), p1.getBlockMapObject().getY());
  }
  
  public void updatePlayer2() {
    p2.setBlockMapArray(blockMap.getBlockMapArray());
    p2.setOtherPlayer(p1);
    p2.update();
    blockMap.setBlockMapArray(p2.getBlockMapArray());
    
    p2.setBlockMapPosition(GamePanel.WIDTH/2 - p2.getX(),
        GamePanel.HEIGHT/2 - p2.getY());
    p2Bg.setPosition(p2.getBlockMapObject().getX(), p2.getBlockMapObject().getY());
  }

  @Override
  public void draw(Graphics2D gLeftScreen, Graphics2D gRightScreen, Graphics2D gHUDScreen)
  {
    hud.draw(gHUDScreen);
    p2Bg.draw(gRightScreen);
    p2.getBlockMapObject().draw(gRightScreen);
    p1.draw(gRightScreen);
    p2.draw(gRightScreen);  
    gRightScreen.setColor(Color.RED);

    p1.setBlockMapPosition(GamePanel.WIDTH / 2 - p1.getX(), 
        GamePanel.HEIGHT / 2 - p1.getY());
    p1Bg.draw(gLeftScreen);
    p1.getBlockMapObject().draw(gLeftScreen);
    p1.draw(gLeftScreen);
    p2.draw(gLeftScreen);
    gLeftScreen.setColor(Color.RED);
    
//    System.out.println("-----------------------------");
//    System.out.println("Tank1:");
//    System.out.println("X position: " + p1.getX() +" Shape X Position: " + p1.getCollisionBox().getBounds().getCenterX());
//    System.out.println("Y position: " + p1.getY() +" Shape Y Position: " + p1.getCollisionBox().getBounds().getCenterY());
//    System.out.println("Tank 2:");
//    System.out.println("X position: " + p2.getX() +" Shape X Position: " + p2.getCollisionBox().getBounds().getCenterX());
//    System.out.println("Y position: " + p2.getY() +" Shape Y Position: " + p2.getCollisionBox().getBounds().getCenterY());

  }

  @Override
  public void keyPressed(int k)
  {
    switch (k)
    {
      case KeyEvent.VK_LEFT:
        p2.setTurnLeft(true);
        break;
      case KeyEvent.VK_RIGHT:
        p2.setTurnRight(true);
        break;
      case KeyEvent.VK_UP:
        p2.setForward(true);
        break;
      case KeyEvent.VK_DOWN:
        p2.setBackwards(true);
        break;
      case KeyEvent.VK_A:
        p1.setTurnLeft(true);
        break;
      case KeyEvent.VK_D:
        p1.setTurnRight(true);
        break;
      case KeyEvent.VK_W:
        p1.setForward(true);
        break;
      case KeyEvent.VK_S:
        p1.setBackwards(true);
        break;
    }
  }

  @Override
  public void keyReleased(int k)
  {
    switch (k)
    {
      case KeyEvent.VK_LEFT:
        p2.setTurnLeft(false);
        break;
      case KeyEvent.VK_RIGHT:
        p2.setTurnRight(false);
        break;
      case KeyEvent.VK_UP:
        p2.setForward(false);
        break;
      case KeyEvent.VK_DOWN:
        p2.setBackwards(false);
        break;
      case KeyEvent.VK_A:
        p1.setTurnLeft(false);
        break;
      case KeyEvent.VK_D:
        p1.setTurnRight(false);
        break;
      case KeyEvent.VK_W:
        p1.setForward(false);
        break;
      case KeyEvent.VK_S:
        p1.setBackwards(false);
        break;
    }
  }
  
}
