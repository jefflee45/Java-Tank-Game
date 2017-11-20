package GameObjects;

import BlockMap.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Player extends GameObject {

  //For removing green background
  
  private int player;
  public final static int FIRST_PLAYER = 1;
  public final static int SECOND_PLAYER = 2;
  
  private int health, maxHealth;
  private int score;
  private boolean dead;
    
  //attacking
  private ArrayList<Bullet> bulletList;
  private boolean firing;
  private boolean flinching;
  private int bulletDamage;
  private long flinchTimer;
  
  //animation
  private BufferedImage[] movingDirection;
  
  public Player(BlockMap blockMap, int player)
  {
    super(blockMap);
    this.player = player;
    init();

    loadSprites();
    
    currentAction = 16;
  }
  
  private void init() {
    //tested values
    width = 68;
    height = 64;
    cWidth = 52;
    cHeight = 44;
    
    speed = 0;
    angleSpeed = 0;
    moveSpeed = 1;
    angleMoveSpeed = 1.5;
    
    angle = 180;
    
    maxSpeed = 1.5;
    maxAngleSpeed = 3;
    stopSpeed = 0.5;
    flinchTimer = 100000;
    
    health = 3;
    maxHealth = 5;
    bulletDamage = 1;
    bulletList = new ArrayList<Bullet>();
    
    movingDirection = new BufferedImage[8];
    rect = new Rectangle((int)x, (int)y, cWidth-4, cHeight);
    collisionBox = rect;
  }
  
  private void loadSprites() {
    if (player == 1) {
      loadFramesFromFolder("Resources/t1an64pxl");
    }
    if (player == 2) {
      loadFramesFromFolder("Resources/t2an64pxl");
    }
  }
  
  public int getHealth() { 
    return health;
  }
  
  public int getMaxHealth() {
    return maxHealth;
  }
  
  public void setFiring() {
    firing = true;
  }
  
  private void getNextPosition() {
    //moving in positive x direction
    
     if (turnLeft) {
      angleSpeed += angleMoveSpeed;
      if (angleSpeed > maxAngleSpeed) {
        angleSpeed = maxAngleSpeed;
      }
      angle += angleSpeed;
      
      if (angle > 360) {
        angle = angle - 360 + angleSpeed;
      }
      
       if (forward)
       {
         speed += moveSpeed;
         if (speed > maxSpeed)
         {
           speed = maxSpeed;
         }
       } else if (backwards)
       {
         speed -= moveSpeed;
         if (speed < -maxSpeed)
         {
           speed = -maxSpeed;
         }
       }
    }
    //moving in negative x direction
     if (turnRight) {
      angleSpeed -= angleMoveSpeed;
      if (angleSpeed < -maxAngleSpeed) {
        angleSpeed = -maxAngleSpeed;
      }
      angle += angleSpeed;
      
      if (angle < -360) {
        angle = angle + 360 - angleSpeed;
      }
      
      if (backwards)
      {
        speed -= moveSpeed;
        if (speed < -maxSpeed)
        {
          speed = -maxSpeed;
        }
      } else if (forward)
      {
        speed += moveSpeed;
        if (speed > maxSpeed)
        {
          speed = maxSpeed;
        }
      }
    }
    
    //moving in positive y direction
    if (backwards) {
      speed += moveSpeed;
      if (speed > maxSpeed) {
        speed = maxSpeed;
      }
    }
    //moving in negative y direction
    if (forward) {
      speed -= moveSpeed;
      if (speed < -maxSpeed) {
        speed = -maxSpeed;
      }
    }
    
    //not moving forward or backwards direction
    if (isNotMoving()) {
      //stop moving in the x direction
      if (speed > 0) {
        speed -= stopSpeed;
        if (speed < 0) {
          speed = 0;
        }
      }
      else if (speed < 0) {
        speed += stopSpeed;
        if (speed > 0) {
          speed = 0;
        }
      }
      
     if (isNotTurning()) {
        //stop moving in the y direction
      if (angleSpeed > 0) {
        angleSpeed -= stopSpeed;
        if (angleSpeed < 0) {
          angleSpeed = 0;
        }
      }
      else if (angleSpeed < 0) {
        angleSpeed += stopSpeed;
        if (angleSpeed > 0) {
          angleSpeed = 0;
        }
      }
     }     
    }
  }
  
  private boolean isNotMoving() {
    return !forward && !backwards;
  }
  
  private boolean isNotTurning() {
    return !turnLeft && !turnRight;
  }
  
  private void checkPowerUp() {
    if (useShield) {
      health += 1;
      if (maxHealth < health) {
        health = maxHealth;
      }
    }
    setUseShield(false);
  }
  
  public void update() {
    getNextPosition();

    checkObjectCollision();
    checkBlockMapCollision();
    checkPowerUp();
    setPosition(xTemp, yTemp);
    setTransformation((int)xTemp, (int)yTemp);
  }
  
  public void setBlockMapPosition(double x, double y) {
    blockMap.setPosition(x, y);
  }
  
  public void setBlockMapArray(Block[][] bm) {
    blockMap.setBlockMapArray(bm);
  }
  
  public Block[][] getBlockMapArray() {
    return blockMap.getBlockMapArray();
  }
  
  public BlockMap getBlockMapObject() {
    return blockMap;
  }
  
  public void setBlockMapObject(BlockMap bm) {
    blockMap = bm;
  }
  
  public int getPlayerNumber() {
    return player;
  }
  
  public void draw(Graphics2D g) {
    setMapPosition();
    
    //draw player
    if (flinching) {
      long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
      if (elapsed / 100 % 2 == 0) {
        return;
      }
    }
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
  
  public void fire() {
      Bullet b = new Bullet(blockMap, angle, x, y);
      bulletList.add(b);
      
  }
  
  public ArrayList<Bullet> getBulletList() {
    return bulletList;
  }
}
