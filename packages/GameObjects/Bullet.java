package GameObjects;

import BlockMap.PowerUp;
import BlockMap.Block;
import BlockMap.BlockMap;
import java.awt.*;

public class Bullet extends GameObject{
  private final int BULLET_BOUNCE = 1;
  
  private double angle;
  private boolean show;
  private int bounceCounter;
  
  public Bullet(BlockMap blockMap, double angle, double x, double y) {
    super(blockMap);
    //add offset so shoots from front of tank, needs fine tuning
    this.x = x - 26 * Math.sin(Math.toRadians(angle));
    this.y = y - 22 * Math.cos(Math.toRadians(angle));
    
    //collision
    cWidth = 10;
    cHeight  = 10;
    
    
    show = true;
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
          show = false;
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
              show = false;
            }
            angle = 180 - angle; 
          }
          else if(bottomLeft && bottomRight) {
            if (blockMap.getBlock((int)y/blockSize + 1, (int)x/blockSize).getType() == Block.BREAKABLE ||
                blockMap.getBlock((int)y/blockSize + 1, (int)x/blockSize).getType() == PowerUp.P1_SHIELD ||
                blockMap.getBlock((int)y/blockSize + 1, (int)x/blockSize).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize + 1, (int) x / blockSize);
              show = false;
            }
            angle = 180 - angle;
          }
          else if(topLeft && bottomLeft) {
            if (blockMap.getBlock((int)y/blockSize, (int)x/blockSize - 1).getType() == Block.BREAKABLE ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize - 1).getType() == PowerUp.P1_SHIELD ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize - 1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize, (int) x / blockSize - 1);
              show = false;
            }
              angle = 360 - angle;
          }
          else if(topRight && bottomRight) {
            if (blockMap.getBlock((int)y/blockSize, (int)x/blockSize + 1).getType() == Block.BREAKABLE ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize + 1).getType() == PowerUp.P1_SHIELD ||
                blockMap.getBlock((int)y/blockSize, (int)x/blockSize + 1).getType() == PowerUp.P2_SHIELD) {
              blockMap.setBlockType(Block.EMPTY_TILE, (int) y / blockSize, (int) x / blockSize + 1);
              show = false;
            }
            angle = 360 - angle;
          }
          
        }
        else {
        show = false;
      }
    }
  }
  
  private void loadSprites() {
    loadFramesFromFolder("Resources/Shell10x10");
  }
  
  public boolean getShow() {
    return this.show;
  }
  
  public void draw(Graphics2D g) {
    setMapPosition();
//    g.setColor(Color.black);
//    g.fillOval((int) (this.x + xMap),//xMap and yMap offsets
//             (int) (this.y + yMap),
//             10, 10);//bullet of 10 x 10 size
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