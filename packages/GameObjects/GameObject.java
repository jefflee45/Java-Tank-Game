package GameObjects;

import BlockMap.Block;
import BlockMap.BlockMap;
import Main.GamePanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public abstract class GameObject
{
  
  protected Image image;
  protected BlockMap blockMap;
  protected Rectangle rect;
  protected Shape collisionBox;
    private Player otherPlayer;
  protected int blockSize;
  
  protected boolean intersected;
  protected boolean topInter, botInter, rightInter, leftInter;
  
  //dimensions
  protected int width, height;
  
  //position
  protected double x, y;
  protected double xMap, yMap;
  
  //speed
  protected double xSpeed, ySpeed;
  
  //collision box
  protected int cWidth, cHeight;
  
    
  protected double angle;
  protected double angleMoveSpeed;
  protected double angleSpeed;
  protected double maxAngleSpeed;
  
  protected double speed;
  
  //collision
  protected int curRow, curCol;
  protected double xDest, yDest, xTemp, yTemp;
  protected boolean topLeft, topRight, bottomLeft, bottomRight;
  
  protected int currentAction, prevAction;
  
  //movement
  protected boolean forward, turnLeft, backwards, turnRight;
  protected double moveSpeed, maxSpeed, stopSpeed;
  
  public GameObject (BlockMap blockMap) {
    this.blockMap = blockMap;
    blockSize = blockMap.getBlockSize();
  }
  
  public boolean intersects (GameObject obj) {
     return collisionBox.intersects(obj.getCollisionBox().getBounds2D());
  }
  
  public Rectangle getRectangle() {
    return new Rectangle((int)x-cWidth, (int)y-cHeight, cWidth, cHeight);
  }
  
  public void setTransformation(int x, int y) {
    AffineTransform at = new AffineTransform();
    rect.setBounds(x-cWidth/2, y-cHeight/2, cWidth, cHeight);
    at.rotate(Math.toRadians(-angle + 6), rect.getCenterX(), rect.getCenterY());
    collisionBox = at.createTransformedShape(rect);
  }
  
  public void calculateCorners(double x, double y) {
    
    //surrounding tiles for 64pxl
    int leftTile = (int)(x - cWidth/2) / blockSize;
    int rightTile = (int)(x + cWidth/2 - 1) / blockSize;
    int topTile = (int)(y - cHeight/2) / blockSize;
    int bottomTile = (int)(y + cHeight/2 - 1) / blockSize;
    
    //four corners for 64pxl
    int tL = blockMap.getType(topTile, leftTile);
    int tR = blockMap.getType(topTile, rightTile);
    int bL = blockMap.getType(bottomTile, leftTile);
    int bR = blockMap.getType(bottomTile, rightTile);
   
   //handles objects larger than 32pxl
    if (cWidth > 32 && height > 32) { 
    int cWidthX = 32;
    int cHeightX = 32;
    int xBuffer = 10;
    int leftTileX = (int)(x - xBuffer - blockSize + cWidthX/2) / blockSize;
    int rightTileX = (int)(x + xBuffer + cWidthX/2 - 1) / blockSize;
    int topTileX = (int)(y - cHeightX/2) / blockSize;
    int bottomTileX = (int)(y + cHeightX/2 - 1) / blockSize;
    int tLX = blockMap.getType(topTileX, leftTileX);
    int tRX = blockMap.getType(topTileX, rightTileX);
    int bLX = blockMap.getType(bottomTileX, leftTileX);
    int bRX = blockMap.getType(bottomTileX, rightTileX);
    
    int cWidthY = 32;
    int cHeightY = 44;
    int yBuffer = 10;
    int leftTileY = (int)(x - yBuffer + cWidthY/2) / blockSize;
    int rightTileY = (int)(x + yBuffer + cWidthY/2 - 1) / blockSize;
    int topTileY = (int)(y - cHeightY/2) / blockSize;
    int bottomTileY = (int)(y + cHeightY/2 - 1) / blockSize;
    int tLY = blockMap.getType(topTileY, leftTileY);
    int tRY = blockMap.getType(topTileY, rightTileY);
    int bLY = blockMap.getType(bottomTileY, leftTileY);
    int bRY = blockMap.getType(bottomTileY, rightTileY);
    
    
    //set booleans
    topLeft = (tL != Block.EMPTY_TILE) || (tLX != Block.EMPTY_TILE) || (tLY != Block.EMPTY_TILE);
    topRight = (tR != Block.EMPTY_TILE) || (tRX != Block.EMPTY_TILE) || (tRY != Block.EMPTY_TILE);
    bottomLeft = (bL != Block.EMPTY_TILE) || (bLX != Block.EMPTY_TILE) || (bLY != Block.EMPTY_TILE);
    bottomRight = (bR != Block.EMPTY_TILE) || (bRX != Block.EMPTY_TILE) || (bRY != Block.EMPTY_TILE);
    }
    else {
    topLeft = (tL != Block.EMPTY_TILE);
    topRight = (tR != Block.EMPTY_TILE);
    bottomLeft = (bL != Block.EMPTY_TILE);
    bottomRight = (bR != Block.EMPTY_TILE);
    }
  
  }
  
  /*
  if moving in a direction, check the corners of the
  next tile in that direction to see if its an EMPTY_TILE or not.
  if it is not an EMPTY_TILE, then stop moving in that direction,
  else continue moving
  */
  public void checkObjectCollision() {

    if (intersects(otherPlayer))
    {
      boolean speedBoosted = false;
      Rectangle2D r1 = collisionBox.getBounds2D();
      Rectangle2D r2 = collisionBox.getBounds2D();
      double xDif = getX() - otherPlayer.getX();
      double yDif = getY() - otherPlayer.getY();

      //locate sides of intersection
        if (speed < 0)
        {
          //other player is below them
          if (yDif < 0)
          {
            botInter = true;
          } //other player is above them
          else if (yDif > 0)
          {
            topInter = true;
          }

          //other player is on the right
          if (xDif < 0)
          {
            rightInter = true;
          } //other player is on the left
          else if (xDif > 0)
          {

            leftInter = true;
          }
        }

        if (speed > 0)
        {
          //other player is below them
          if (yDif < 0)
          {
            botInter = true;
          } //other player is above them
          else if (yDif > 0)
          {
            topInter = true;
          }

          //other player is on the right
          if (xDif < 0)
          {
            rightInter = true;
          } //other player is on the left
          else if (xDif > 0)
          {
            leftInter = true;
          }
        }
        
      //---------------------------------------
      //handle cases of intersection
      //moving forward
      if (speed < 0)
      {
        if (botInter)
        {
          if (rightInter)
          {
            speed = 0;
            xTemp = curCol * blockSize + cWidth / 2;
          } else if (leftInter)
          {
            speed = 0;
            xTemp = (curCol + 1) * blockSize - cWidth/2;
          }
          
          speed = 0;
          yTemp = curRow * blockSize + cHeight / 2 + 1;
        } 

        else if (topInter)
        {
          if (rightInter)
          {
            speed = 0;
            xTemp = curCol * blockSize + cWidth / 2;
          } else if (leftInter)
          {
            speed = 0;
            xTemp = (curCol + 1) * blockSize - cWidth/2;
          }
          speed = 0;
          yTemp = (curRow + 1) * blockSize - cHeight / 2 - 1;
      }
      }

      //moving backwards
      if (speed > 0)
      {
        if (botInter)
        {
          if (rightInter)
          {
            speed = 0;
            xTemp = (curCol + 1) * blockSize - cWidth / 2;
          } 
          else if (leftInter)
          {
            speed = 0;
            xTemp = curCol * blockSize + cWidth / 2;
          }
          
          speed = 0;
          yTemp = (curRow + 1) * blockSize - cHeight / 2;
        }
        else if (topInter)
        {
          if (rightInter)
          {
            speed = 0;
            xTemp = (curCol + 1) * blockSize - cWidth / 2;
          } 
          else if (leftInter)
          {
            speed = 0;
            xTemp = curCol * blockSize + cWidth / 2;
          }
          speed = 0;
          yTemp = curRow * blockSize + cHeight / 2 + 1;
          }
        }
      
    } else
    {
      botInter = topInter = rightInter = leftInter = false;
    }
  }
  
  public void checkBlockMapCollision() {
    
    boolean speedBoosted = false;
    curCol = (int)x / blockSize;
    curRow = (int)y / blockSize;
    
    xDest = x + speed * Math.sin(Math.toRadians(angle));
    yDest = y + speed * Math.cos(Math.toRadians(angle));
    
    xTemp = x;
    yTemp = y;
    
    //y direction movement
    calculateCorners(x, yDest);
    
    //moving upwards (ySpeed is negative for upwards direction)
    if (speed < 0) {
      //if there is a block above, stop the speed and place
      //the object right below the block
      
      if (topLeft || topRight) {
        speed = 0;
        yTemp = curRow * blockSize + cHeight/2 + 1;
      } 
      else if (bottomLeft || bottomRight) {
        speed = 0;
        yTemp = (curRow + 1) * blockSize - cHeight/2;
      }
      else {
        if (!speedBoosted) {
        xTemp += speed * Math.sin(Math.toRadians(angle));
        yTemp += speed * Math.cos(Math.toRadians(angle));
        speedBoosted = true;
        }
      }
    }
    //moving downwards
    if (speed > 0) {
      if (bottomLeft || bottomRight) {
        speed = 0;
        yTemp = (curRow + 1) * blockSize - cHeight/2;
      } 
      else if (topLeft || topRight) {
        speed = 0;
        yTemp = curRow * blockSize + cHeight/2 + 1;
      }
      else {
        if (!speedBoosted) {

        xTemp += speed * Math.sin(Math.toRadians(angle));
        yTemp += speed * Math.cos(Math.toRadians(angle));
        speedBoosted = true;
        }
      }
    }
    
//    //x direction movement
    calculateCorners(xDest, y);
    //moving left
    if (speed < 0) {
      if (topLeft || bottomLeft) {
        speed = 0;
        xTemp = curCol * blockSize + cWidth/2;
      }
      else if (topRight || bottomRight) {
        speed = 0;
        xTemp = (curCol + 1) * blockSize - cWidth/2;
      }
      else {
        if (!speedBoosted) {

        xTemp += speed * Math.sin(Math.toRadians(angle));
        yTemp += speed * Math.cos(Math.toRadians(angle));
        speedBoosted = true;
        }
      }
    }
    //moving right
    if (speed > 0) {
      if (topRight || bottomRight) {
        speed = 0;
        xTemp = (curCol + 1) * blockSize - cWidth/2;
      }
      else if (topLeft || bottomLeft) {
        speed = 0;
        xTemp = curCol * blockSize + cWidth/2;
      }
      else {
        if (!speedBoosted) {

        xTemp += speed * Math.sin(Math.toRadians(angle));
        yTemp += speed * Math.cos(Math.toRadians(angle));
        speedBoosted = true;
        }
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
  
   public Shape getCollisionBox() {
    return collisionBox;
  }
  
  public void setOtherPlayer(Player otherPlayer) {
    this.otherPlayer = otherPlayer;
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
  
  public void setTurnRight (boolean b) {
    turnRight = b;
  } 
  
  public void setTurnLeft (boolean b) {
    turnLeft = b;
  }
  
  public void setForward (boolean b) {
    forward = b;
  }
  
  public void setBackwards (boolean b) {
    backwards = b;
  }
  
  public void setCurrentAction(int k) {
    currentAction = k;
  }
  
  public void setPrivousAction(int k) {
    prevAction = k;
  }
}

