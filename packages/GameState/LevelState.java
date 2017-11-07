package GameState;

import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class LevelState extends GameState
{
  private Background bg;
  
  //private TileMap tileMap;
  
  public LevelState(GameStateManager gsm) {
    this.gsm = gsm;
    init();
    
    this.gsm = gsm;
     
     try {
       bg = new Background("Resources/Background.bmp");
     }
     catch (Exception e) {
       e.printStackTrace();
     }
  }

  @Override
  public void init()
  {
    //tileMap = new tileMap(30);
  }

  @Override
  public void update()
  {
  }

  @Override
  public void draw(Graphics2D g)
  {
    bg.draw(g);
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
