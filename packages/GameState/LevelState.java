package GameState;

import Main.GamePanel;
import TankGame.Background;
import BlockMap.BlockMap;
import GameObjects.Player;
import java.awt.Color;
import java.awt.Graphics2D;
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
    
    this.gsm = gsm;
     
     try {
       bg = new Background("Resources/BackgroundLarge.bmp");
     }
     catch (Exception e) {
       e.printStackTrace();
     }
  }

  @Override
  public void init()
  {
    blockMap = new BlockMap();
    p1 = new Player(blockMap);
  } 

  @Override
  public void update()
  {
  }

  @Override
  public void draw(Graphics2D g)
  {
    bg.draw(g);
    blockMap.draw(g);
  }

  @Override
  public void keyPressed(int k)
  {
  }

  @Override
  public void keyReleased(int k)
  {
  }
  
}
