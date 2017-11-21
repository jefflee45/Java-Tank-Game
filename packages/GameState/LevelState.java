package GameState;

import Main.GamePanel;
import TankGame.Background;
import BlockMap.BlockMap;
import GameObjects.Player;
import BlockMap.PowerUp;
import GameObjects.Explosion;
import TankGame.CollisionDetector;
import TankGame.HUD;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LevelState extends GameState
{
  private Background p1Bg;
  private Background p2Bg;
  
  private Player p1;
  private Player p2;
  
  private boolean canFireP1;
  private boolean canFireP2;
  
  private HUD hud;
  
  private BlockMap blockMap;
  
  private boolean reset;
  private ArrayList<Explosion> explosions;
  
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
    blockMap = new BlockMap();
    explosions = new ArrayList();
    blockMap.setPosition(0, 0);
    blockMap.setTween(1);
    p1 = new Player(blockMap, Player.FIRST_PLAYER);
    p2 = new Player(blockMap, Player.SECOND_PLAYER);
    p1.setPosition(190, 434);
    p2.setPosition(300, 480);
    canFireP1 = true;
    canFireP2 = true;
    reset = false;
  } 

  @Override
  public void update()
  {
     if(reset) {
            init();
        }
        if(!p1.getDead()) {
          updatePlayer1();
        }
        else {
            reset = true;
            gsm.setState(gsm.ENDSTATE);
        }
        if(!p2.getDead()) {
          updatePlayer2();
        }
        else {
            reset = true;
            gsm.setState(gsm.ENDSTATE);
        }

    updateBulletList();
    updateExplosionsList();
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
  
   public void updateBulletList() {
        //Player 1 Bullets
        for (int i = 0; i < p1.getBulletList().size(); i++) {
          explosions.add(p1.getBulletList().get(i).getExplosion());
            if (p1.getBulletList().get(i).getShow()) {
                p1.getBulletList().get(i).setMyPlayer(p1);
                p1.getBulletList().get(i).setOtherPlayer(p2);
                p1.getBulletList().get(i).update();
                if(p1.getBulletList().get(i).getHitOther()) {//P1 hits P2
                    p2.setHealth(p2.getHealth() - p1.getBulletDamage());
                    p1.getBulletList().get(i).setHitOther(false);
                }
                if(p1.getBulletList().get(i).getHitSelf()) {//P1 hits self
                    p1.setHealth(p1.getHealth() - p1.getBulletDamage());
                    p1.getBulletList().get(i).setHitSelf(false);
                }

            } 
            else {
                p1.getBulletList().remove(i);
            }
        }

        //Player 2 Bullets
        for (int i = 0; i < p2.getBulletList().size(); i++) {
          explosions.add(p1.getBulletList().get(i).getExplosion());
            if (p2.getBulletList().get(i).getShow()) {
                p2.getBulletList().get(i).setMyPlayer(p2);
                p2.getBulletList().get(i).setOtherPlayer(p1);
                p2.getBulletList().get(i).update();
                if(p2.getBulletList().get(i).getHitOther()) {//P2 hits P1
                    p1.setHealth(p1.getHealth() - p2.getBulletDamage());
                    p2.getBulletList().get(i).setHitOther(false);
                }
                if(p2.getBulletList().get(i).getHitSelf()) {//P2 hits self
                    p2.setHealth(p2.getHealth() - p2.getBulletDamage());
                    p2.getBulletList().get(i).setHitSelf(false);
                }
            }
            else {
                p2.getBulletList().remove(i);
            }
        }
    }
   
   private void updateExplosionsList() {
    for (int i = 0; i < explosions.size(); i++) {
      if (explosions.get(i).hasPlayedOnce()) {
        explosions.remove(i);
      }
    }
   }

  @Override
  public void draw(Graphics2D gLeftScreen, Graphics2D gRightScreen, Graphics2D gHUDScreen)
  {
    hud.draw(gHUDScreen);
    p2Bg.draw(gRightScreen);
    p2.getBlockMapObject().draw(gRightScreen);
    p1.draw(gRightScreen);
    p2.draw(gRightScreen); 
    for(int i = 0; i < p2.getBulletList().size(); i++) {
      p2.getBulletList().get(i).draw(gRightScreen);
    }
    
    for(int i = 0; i < p1.getBulletList().size(); i++) {
      p1.getBulletList().get(i).draw(gRightScreen);
    }
    
    for (int i = 0; i < explosions.size(); i++) {
      explosions.get(i).draw(gRightScreen);
    }
    
    p1.setBlockMapPosition(GamePanel.WIDTH / 2 - p1.getX(), 
        GamePanel.HEIGHT / 2 - p1.getY());
    
    p1Bg.draw(gLeftScreen);
    p1.getBlockMapObject().draw(gLeftScreen);
    p1.draw(gLeftScreen);
    p2.draw(gLeftScreen);

    for(int i = 0; i < p1.getBulletList().size(); i++) {
      p1.getBulletList().get(i).draw(gLeftScreen);
    }
    
    for(int i = 0; i < p2.getBulletList().size(); i++) {
      p2.getBulletList().get(i).draw(gLeftScreen);
    }
    
    for (int i = 0; i < explosions.size(); i++) {
      explosions.get(i).draw(gLeftScreen);
    }
    
    p1.playerHealth(gHUDScreen, 150, 990);
    p2.playerHealth(gHUDScreen, 650, 990);
  }

  @Override
  public void keyPressed(int k)
  {
    switch (k)
    {
      //Player 2
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
      case KeyEvent.VK_ENTER:
        if(canFireP2) {
          p2.fire(); 
          canFireP2 = false;
        }
      break; 
        
       //Player 1 
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
      case KeyEvent.VK_SPACE:
        if(canFireP1) {
          p1.fire();
          canFireP1 = false;
        }
      break;
    }
  }

  @Override
  public void keyReleased(int k)
  {
    switch (k)
    {
      //Player 2
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
      case KeyEvent.VK_ENTER:
        canFireP2 = true;
        break;
        
      //Player 1
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
      case KeyEvent.VK_SPACE:
        canFireP1 = true;
        break;
    }
  }
  
}
