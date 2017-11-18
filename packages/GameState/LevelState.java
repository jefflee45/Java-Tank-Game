package GameState;

import Main.GamePanel;
import TankGame.Background;
import BlockMap.BlockMap;
import GameObjects.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class LevelState extends GameState
{
  private Background bg;
  private Background p1Bg;
  private Background p2Bg;
  private Player p1;
  private Player p2;
  
  private int x;
  private int y;
  
  private BlockMap blockMap;
  private BlockMap p1BlockMap;
  private BlockMap p2BlockMap;
  
  public LevelState(GameStateManager gsm) {
    this.gsm = gsm;
    init();
         
     try {
       bg = new Background("Resources/BackgroundLarge.bmp");
       p1Bg = new Background("Resources/BackgroundLarge.bmp");
       p2Bg = new Background("Resources/BackgroundLarge.bmp");
     }
     catch (Exception e) {
       e.printStackTrace();
     }
  }

  @Override
  protected void init()
  {
    blockMap = new BlockMap();
    blockMap.setPosition(0, 0);
    blockMap.setTween(1);
    p1 = new Player(blockMap, Player.FIRST_PLAYER);
    p2 = new Player(blockMap, Player.SECOND_PLAYER);
    p1.setPosition(184, 234);
    p2.setPosition(200, 260);
  } 

  @Override
  public void update()
  {
    updatePlayer1();
    updatePlayer2();
//    p1.update();
//
//    blockMap.setPosition(GamePanel.WIDTH / 2 - p1.getX(),
//        GamePanel.HEIGHT / 2 - p1.getY());
//    bg.setPosition(blockMap.getX(), blockMap.getY());
    
  }
  
  public void updatePlayer1() {
    p1.setBlockMap(blockMap);
    p1.update();
    blockMap = p1.getBlockMap();
    p1BlockMap = blockMap;
    
    p1BlockMap.setPosition(GamePanel.WIDTH / 2 - p1.getX(),
        GamePanel.HEIGHT / 2 - p1.getY());
    p1Bg.setPosition(p1BlockMap.getX(), p1BlockMap.getY());

//    blockMap.setPosition(GamePanel.WIDTH / 2 - p1.getX(),
//        GamePanel.HEIGHT / 2 - p1.getY());
//    bg.setPosition(blockMap.getX(), blockMap.getY());
  }
  
  public void updatePlayer2() {
    p2.setBlockMap(blockMap);
    p2.update();
    blockMap = p2.getBlockMap();
    p2BlockMap = blockMap;
    
    p2BlockMap.setPosition(GamePanel.WIDTH/2 - p2.getX(),
        GamePanel.HEIGHT/2 - p2.getY());
    p2Bg.setPosition(p2BlockMap.getX(), p2BlockMap.getY());
  }

  @Override
  public void draw(Graphics2D gLeftScreen, Graphics2D gRightScreen)
  {
    p1Bg.draw(gLeftScreen);
    p2Bg.draw(gRightScreen);
    p1BlockMap.draw(gLeftScreen);
    p2BlockMap.draw(gRightScreen);

    p1.draw(gLeftScreen);
    p2.draw(gLeftScreen);
    p1.draw(gRightScreen);
    p2.draw(gRightScreen);
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
