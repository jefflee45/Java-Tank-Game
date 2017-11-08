package GameObjects;

import BlockMap.BlockMap;
import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class GameObject
{
  
  protected Image image;
  protected BlockMap bm;
  protected int blockSize;
  
  //dimensions
  protected int width, height;
  
  //position
  protected double x, y;
  
  //speed
  protected double xSpeed, ySpeed;
  
  //collision box
  protected int cWidth, cHeight;
  
  //collision
  protected int curRow, curCol;
  protected double xDest, yDest, xTemp, yTemp;
  protected boolean topLeft, topRight, bottomLeft, bottomRight;
  
  //animation
  //protected Animation animation;
  protected int curAction, prevAction;
  
  //movement
  protected boolean left, right, up, down;
  protected double moveSpeed, maxSpeed, stopSpeed;
  
  public GameObject (BlockMap bm) {
    this.bm = bm;
    blockSize = bm.getBlockSize();
  }
  
  public boolean intersects (GameObject obj) {
    Rectangle r1 = getRectangle();
    Rectangle r2 = obj.getRectangle();
    return r1.intersects(r2);
  }
  
  public Rectangle getRectangle() {
    return new Rectangle((int)x-cWidth, (int)y-cHeight, cWidth, cHeight);
  }
  
  public void caluculateCorners(double x, double y) {
    
  }
  
  public void checkBlockMapCollision() {
    curCol = (int)x / blockSize;
    curRow = (int)y / blockSize;
    
    xDest = x + xSpeed;
    yDest = y + ySpeed;
    
    //calculateCorners(x, yDest);
  }
  
  public void draw (Graphics2D g, ImageObserver obs) {
    g.drawImage(image, (int)x, (int)y, obs);
  }
}
