package GameObjects;

import BlockMap.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
import java.util.Arrays;

public class Player extends GameObject {

  private File dir;
  private String[] EXTENSIONS;
  private FilenameFilter IMAGE_FILTER;
  
  private int health, maxHealth;
  private int score;
  
  private int oldKey, newKey;
  
  private boolean dead;
  
  //attacking
  private boolean firing;
  private boolean flinching;
  private int bulletDamage;
  private long flinchTimer;
  
  //animations
  BufferedImage[] frames;
  
  public boolean[] animations;
  private BufferedImage[] movingDirection;
  
  public Player(BlockMap blockMap)
  {
    super(blockMap);
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
    moveSpeed = 0.3;
    angleMoveSpeed = 1;
    
    angle = 180;
    
    maxSpeed = 1.6;
    maxAngleSpeed = 3;
    stopSpeed = 0.4;
    flinchTimer = 100000;
    
    health = maxHealth = 5;
    bulletDamage = 1;
    
    animations = new boolean[28];
    movingDirection = new BufferedImage[8];
    Arrays.fill(animations, Boolean.FALSE);
    animation = new Animation();
    rect = new Rectangle(0, 0, width, height);
  }
  
  public static Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
  
  private static BufferedImage imageToBufferedImage(Image image) {

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return bufferedImage;

    }
  
  public static BufferedImage makeBackgroundTransparent(BufferedImage image) {
    
    int color = image.getRGB(0,0);
    Image pic = makeColorTransparent(image, Color.GREEN);
    return imageToBufferedImage(pic);
  }
  
  private void loadSprites() {
    loadFramesFromFolder("Resources/t1an64pxl");
  }
  
  public void loadFramesFromFolder(String file) {
    
    File dir = new File(file);

    EXTENSIONS = new String[]{
        "gif", "png", "jpg"
    };
    
    IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
    //frames rotating 45 degrees counter clockwise
    frames = new BufferedImage[60];
    int count = 0;
    
    if (dir.isDirectory())
    { 
      for (final File f : dir.listFiles(IMAGE_FILTER))
      {
        try {
          BufferedImage image = ImageIO.read(f);
          image = makeBackgroundTransparent(image);
          
          frames[count++] = image;

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
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
      
      
      speed += moveSpeed;
      if (speed > maxSpeed) {
        speed = maxSpeed;
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
      
      speed -= moveSpeed;
      if (speed < -maxSpeed) {
        speed = -maxSpeed;
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
  
  public void update() {
    rect.setLocation((int)x, (int)y);
    getNextPosition();
    checkBlockMapCollision();
    setPosition(xTemp, yTemp);
    rect.setLocation((int)x, (int)y);
    
//    x += speed * Math.sin(Math.toRadians(angle));
//    y += speed * Math.cos(Math.toRadians(angle));
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
}
