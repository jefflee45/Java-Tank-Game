package GameObjects;

import BlockMap.BlockMap;
import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class GameObject
{
  
  protected Image image;
  protected BlockMap bm;
  
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
  }
  
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract int getWidth();
  
  public  abstract int getHeight();
  
  public abstract  void setX(double x);
  
  public abstract void setY (double y);
  
  public void draw (Graphics2D g, ImageObserver obs) {
    g.drawImage(image, (int)x, (int)y, obs);
  }
}
