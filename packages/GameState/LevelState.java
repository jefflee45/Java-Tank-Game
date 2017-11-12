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
  private Player p1;
  
  private int x;
  private int y;
  
  private BlockMap blockMap;
  
  public LevelState(GameStateManager gsm) {
    this.gsm = gsm;
    init();
         
     try {
       bg = new Background("Resources/BackgroundLarge.bmp");
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
    p1 = new Player(blockMap);
    p1.setPosition(64, 64);
  } 

  @Override
  public void update()
  {
    p1.update();

    blockMap.setPosition(GamePanel.WIDTH / 2 - p1.getX(),
        GamePanel.HEIGHT / 2 - p1.getY());
  }

  @Override
  public void draw(Graphics2D g)
  {
    bg.draw(g);
    blockMap.draw(g);

    p1.draw(g);
  }

  @Override
  public void keyPressed(int k)
  {
    switch (k)
    {
      case KeyEvent.VK_LEFT:
        p1.setWest(true);
        break;
      case KeyEvent.VK_RIGHT:
        p1.setEast(true);
        break;
      case KeyEvent.VK_UP:
        p1.setNorth(true);
        break;
      case KeyEvent.VK_DOWN:
        p1.setSouth(true);
        break;
    }
  }

  @Override
  public void keyReleased(int k)
  {
    switch (k)
    {
      case KeyEvent.VK_LEFT:
        p1.setWest(false);
        break;
      case KeyEvent.VK_RIGHT:
        p1.setEast(false);
        break;
      case KeyEvent.VK_UP:
        p1.setNorth(false);
        break;
      case KeyEvent.VK_DOWN:
        p1.setSouth(false);
        break;
    }
  }
  
}
