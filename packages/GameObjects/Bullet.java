package GameObjects;

import BlockMap.BlockMap;
import Main.GamePanel;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;
import java.awt.Rectangle;

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
    this.angle = angle;
    bounceCounter = 0;
    
  }
  
  public void update() {
      this.x -= speed * Math.sin(Math.toRadians(angle));
      this.y -= speed * Math.cos(Math.toRadians(angle));
      
      calculateCorners(x, y);
      if (bulletIntersects(otherPlayer)) {
          System.out.println("Boom");
          show = false;
        }
      
      if(topLeft || topRight || bottomLeft || bottomRight) {//if bullet hits wall
        if(bounceCounter < BULLET_BOUNCE) {
          bounceCounter++;
          
          if(topLeft && topRight) {
            angle = 180 - angle; 
          }
          else if(bottomLeft && bottomRight) {
            angle = 180 - angle;
          }
          else if(topLeft && bottomLeft) {
              angle = 360 - angle;
          }
          else if(topRight && bottomRight) {
            angle = 360 - angle;
          }
          
        }
        else {
          show = false;
        }
      }
    }

    
   
  
  
  public boolean getShow() {
    return this.show;
  }
  
  public void draw(Graphics2D g) {
    setMapPosition();
    g.setColor(Color.black);
    g.fillOval((int) (this.x + xMap),//xMap and yMap offsets
             (int) (this.y + yMap),
             10, 10);//bullet of 10 x 10 size
  }
}