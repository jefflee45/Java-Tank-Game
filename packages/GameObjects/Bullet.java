package GameObjects;

import BlockMap.PowerUp;
import BlockMap.Block;
import BlockMap.BlockMap;
import java.awt.*;
public class Bullet extends GameObject{
  private final int BULLET_BOUNCE = 2;
  
  private Player myPlayer;
  private double angle;
  private boolean show;
  private boolean hitOther;
  private boolean hitSelf;
  private int bounceCounter;

  private Explosion explosion;
  
  public Bullet(BlockMap blockMap, double angle, double x, double y) {
    super(blockMap);
    //add offset so shoots from front of tank, needs fine tuning
    this.x = (x - blockSize/2) - 30 * Math.sin(Math.toRadians(angle));
    this.y = (y - blockSize/2) - 25 * Math.cos(Math.toRadians(angle));
    
    //collision
    height = 24;
    width = 24;
    cWidth = 10;
    cHeight  = 10;
    
    //initialize explosion
    explosion = new Explosion(Explosion.NO_EXPLOSION, 0 ,0);
    
    show = true;
    hitOther = false;
    hitSelf = false;
    speed = 5;
    this.angle = angle - 3;
    bounceCounter = 0;
    loadSprites();
  }
  
  public void update() {
      this.x -= speed * Math.sin(Math.toRadians(angle));
      this.y -= speed * Math.cos(Math.toRadians(angle));
      
      calculateCorners(x, y);
      
      if (bulletIntersects(otherPlayer)) {
          explosion = new Explosion(Explosion.LARGE_EXPLOSION,
          (int)(x + xMap - width / 2), 
          (int)(y + yMap - height / 2));
          show = false;
          hitOther = true;
        }
      
      if (bulletIntersects(myPlayer) && bounceCounter > 0) {//if bullet hits self
          explosion = new Explosion(Explosion.LARGE_EXPLOSION,
          (int)(x + xMap - width / 2), 
          (int)(y + yMap - height / 2));
          show = false;
          hitSelf = true;
        }
      
      if(topLeft || topRight || bottomLeft || bottomRight) {//if bullet hits wall
        rect = getRectangle();
        
        if(bounceCounter < BULLET_BOUNCE) {
          bounceCounter++;
          
          if(topLeft && topRight) {
              if (blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize).getType() == Block.BREAKABLE ||
                  blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize).getType() == PowerUp.P1_SHIELD ||
                  blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize - 1, (int) x / blockSize);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
            angle = 180 - angle; 
          }
          else if(bottomLeft && bottomRight) {
            if (blockMap.getBlock((int)y/blockSize + 1, (int)x/blockSize).getType() == Block.BREAKABLE ||
                blockMap.getBlock((int)y/blockSize + 1, (int)x/blockSize).getType() == PowerUp.P1_SHIELD ||
                blockMap.getBlock((int)y/blockSize + 1, (int)x/blockSize).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize + 1, (int) x / blockSize);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
            angle = 180 - angle;
          }
          else if(topLeft && bottomLeft) {
            if (blockMap.getBlock((int)y/blockSize, (int)x/blockSize - 1).getType() == Block.BREAKABLE ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize - 1).getType() == PowerUp.P1_SHIELD ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize - 1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize, (int) x / blockSize - 1);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
              angle = 360 - angle;
          }
          else if(topRight && bottomRight) {
            if (blockMap.getBlock((int)y/blockSize, (int)x/blockSize + 1).getType() == Block.BREAKABLE ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize + 1).getType() == PowerUp.P1_SHIELD ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize + 1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize, (int) x / blockSize + 1);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
            angle = 360 - angle;
          }
          else if(topLeft) {
            if (blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize-1).getType() == Block.BREAKABLE ||
                  blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize-1).getType() == PowerUp.P1_SHIELD ||
                  blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize-1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize - 1, (int) x / blockSize -1);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
          }
          else if(topRight) {
            if (blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize+1).getType() == Block.BREAKABLE ||
                  blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize+1).getType() == PowerUp.P1_SHIELD ||
                  blockMap.getBlock((int)y/blockSize-1, (int)x/blockSize+1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize - 1, (int) x / blockSize + 1);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
          }
          else if(bottomLeft) {
            if (blockMap.getBlock((int)y/blockSize+1, (int)x/blockSize-1).getType() == Block.BREAKABLE ||
                  blockMap.getBlock((int)y/blockSize+1, (int)x/blockSize-1).getType() == PowerUp.P1_SHIELD ||
                  blockMap.getBlock((int)y/blockSize+1, (int)x/blockSize-1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize + 1, (int) x / blockSize - 1);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
          }
          else if(bottomRight) {
            if (blockMap.getBlock((int)y/blockSize+1, (int)x/blockSize+1).getType() == Block.BREAKABLE ||
                  blockMap.getBlock((int)y/blockSize+1, (int)x/blockSize+1).getType() == PowerUp.P1_SHIELD ||
                  blockMap.getBlock((int)y/blockSize+1, (int)x/blockSize+1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize + 1, (int) x / blockSize + 1);
              explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
              show = false;
            }
          } 
        }
        else {
        explosion = new Explosion(Explosion.SMALL_EXPLOSION,
              (int)(x + xMap - width / 2), 
              (int)(y + yMap - height / 2));
        show = false;
      }
    }
  }
  
  private void loadSprites() {
    loadFramesFromFolder("Resources/Shell24x24");
  }
  
  public boolean getShow() {
    return this.show;
  }
  
  public Explosion getExplosion() {
    return explosion;
  }
  
  public void setMyPlayer(Player mp) {
      myPlayer = mp;
  }
  
  public boolean getHitOther() {
      return hitOther;
  }
  
  public void setHitOther(boolean b) {
      hitOther = b;
  }
  
  public boolean getHitSelf() {
      return hitSelf;
  }
  
  public void setHitSelf(boolean b) {
      hitSelf = b;
  }
  
  public void draw(Graphics2D g) {
    setMapPosition();

  if (angle  < 0) {
      angle += 360;
    }
    
    int frame = (int)angle/6 - 1;
    
    if (frame < 0 || frame > 59) {
      frame = 0;
    }
    
    //accounts for the frame displacement in regards to the moving direction
    frame = (frame + 15) % 59;

    g.drawImage(frames[frame],
        (int)(x + xMap - width / 2),
        (int)(y + yMap - height / 2),
        null);
  }
}