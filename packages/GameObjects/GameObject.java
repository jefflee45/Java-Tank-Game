package GameObjects;

import BlockMap.Block;
import BlockMap.BlockMap;
import Main.GamePanel;
import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class GameObject
{
  
  protected Image image;
  protected BlockMap blockMap;
  protected int blockSize;
  
  //dimensions
  protected int width, height;
  
  //position
  protected double x, y;
  protected double xMap, yMap;
  
  //speed
  protected double xSpeed, ySpeed;
  
  //collision box
  protected int cWidth, cHeight;
  
  //collision
  protected int curRow, curCol;
  protected double xDest, yDest, xTemp, yTemp;
  protected boolean topLeft, topRight, bottomLeft, bottomRight;
  
  //animation
  protected Animation animation;
  protected int currentAction, prevAction;
  
  //movement
  protected boolean north, east, south, west, northEast, northWest, southEast, southWest;
  protected double moveSpeed, maxSpeed, stopSpeed;
  
  public GameObject (BlockMap blockMap) {
    this.blockMap = blockMap;
    blockSize = blockMap.getBlockSize();
  }
  
  public boolean intersects (GameObject obj) {
    Rectangle r1 = getRectangle();
    Rectangle r2 = obj.getRectangle();
    return r1.intersects(r2);
  }
  
  public Rectangle getRectangle() {
    return new Rectangle((int)x-cWidth, (int)y-cHeight, cWidth, cHeight);
  }
  
  public void calculateCorners(double x, double y) {
    
    //surrounding tiles
    int leftTile = (int)(x - cWidth/2) / blockSize;
    int rightTile = (int)(x + cWidth/2 - 1) / blockSize;
    int topTile = (int)(y - cHeight/2) / blockSize;
    int bottomTile = (int)(y + cHeight/2 - 1) / blockSize;
    
    //four corners
    int tL = blockMap.getType(topTile, leftTile);
    int tR = blockMap.getType(topTile, rightTile);
    int bL = blockMap.getType(bottomTile, leftTile);
    int bR = blockMap.getType(bottomTile, rightTile);
    
    //set booleans
    topLeft = tL != Block.EMPTY_TILE;
    topRight = tR != Block.EMPTY_TILE;
    bottomLeft = bL != Block.EMPTY_TILE;
    bottomRight = bR != Block.EMPTY_TILE;
    
  }
  
  /*
  if moving in a direction, check the corners of the
  next tile in that direction to see if its an EMPTY_TILE or not.
  if it is not an EMPTY_TILE, then stop moving in that direction,
  else continue moving
  */
  public void checkBlockMapCollision() {
    curCol = (int)x / blockSize;
    curRow = (int)y / blockSize;
    
    xDest = x + xSpeed;
    yDest = y + ySpeed;
    
    xTemp = x;
    yTemp = y;
   
    //y direction movement
    calculateCorners(x, yDest);
    //moving upwards (ySpeed is negative for upwards direction)
    if (ySpeed < 0) {
      //if there is a block above, stop the speed and place
      //the object right below the block
      
      if (topLeft || topRight) {
        ySpeed = 0;
        yTemp = curRow * blockSize + cHeight/2;
      }
      else {
        yTemp += ySpeed;
      }
    }
    //moving downwards
    if (ySpeed > 0) {
      if (bottomLeft || bottomRight) {
        ySpeed = 0;
        yTemp = (curRow + 1) * blockSize - cHeight/2;
      }
      else {
        yTemp += ySpeed;
      }
    }
    
    //x direction movement
    calculateCorners(xDest, y);
    //moving left
    if (xSpeed < 0) {
      if (topLeft || bottomLeft) {
        xSpeed = 0;
        xTemp = curCol * blockSize + cWidth/2;
      }
      else {
        xTemp += xSpeed;
      }
    }
    //moving right
    if (xSpeed > 0) {
      if (topRight || bottomRight) {
        xSpeed = 0;
        xTemp = (curCol + 1) * blockSize - cWidth/2;
      }
      else {
        xTemp += xSpeed;
      }
    }   
  }
  
  public int getX() {
    return (int) x;
  }
  
  public int getY() {
    return (int)y;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public int getCHeight() {
    return cHeight;
  }
  
  public int getCWidth() {
    return cWidth;
  }
  
  public void setPosition (double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public void setSpeeds(double xSpeed, double ySpeed) {
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
  }
  
  public void setMapPosition() {
    xMap = blockMap.getX();
    yMap = blockMap.getY();
  }
  
  public boolean notOnScreen() {
    return (x + xMap + width < 0)
        || (x + xMap - width > GamePanel.WIDTH)
        || (y + yMap + height < 0)
        || (y + yMap - height > GamePanel.HEIGHT);
  }
  
  public void setEast (boolean b) {
    east = b;
  } 
  
  public void setWest (boolean b) {
    west = b;
  }
  
  public void setNorth (boolean b) {
    north = b;
  }
  
  public void setSouth (boolean b) {
    south = b;
  }
  
  public void setNorthEast (boolean b) {
    northEast = b;
  }
  
  public void setNorthWest (boolean b) {
    northWest = b;
  }
  
  public void setSouthEast (boolean b) {
    southEast = b;
  }
  
  public void setSouthWest (boolean b) {
    southWest = b;
  }
}
