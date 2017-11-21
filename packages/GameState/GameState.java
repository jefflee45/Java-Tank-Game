package GameState;

import java.awt.Graphics2D;

public abstract class GameState
{
  protected GameStateManager gsm;
 
   protected abstract void init();
   
   public abstract void update();
   
   public abstract void draw(Graphics2D gLeftScreen, Graphics2D gRightScreen, Graphics2D gHUDScreen);
   
   public abstract void keyPressed(int k);
   
   public abstract void keyReleased(int k);
}