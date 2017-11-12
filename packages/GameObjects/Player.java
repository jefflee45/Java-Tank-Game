package GameObjects;

import BlockMap.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
  
  private boolean dead;
  
  //attacking
  private boolean firing;
  private boolean flinching;
  private int bulletDamage;
  private long flinchTimer;
  
  //animations
  ArrayList<BufferedImage[]> sprites;
  
  //animation action index for sprites ArrayList and booleans
  //relative to counter clockwise motion
  //EAST = pointing in positive x axis
  private static final int TURN_EAST_TO_NORTHEAST = 0;
  private static final int TURN_NORTHEAST_TO_EAST = 1;
  private static final int TURN_NORTHEAST_TO_NORTH = 2;
  private static final int TURN_NORTH_TO_NORTHEAST = 3;
  private static final int TURN_NORTH_TO_NORTHWEST = 4;
  private static final int TURN_NORTHWEST_TO_NORTH = 5;
  private static final int TURN_NORTHWEST_TO_WEST = 6;
  private static final int TURN_WEST_TO_NORTHWEST = 7;
  private static final int TURN_WEST_TO_SOUTHWEST = 8;
  private static final int TURN_SOUTHWEST_TO_WEST = 9;
  private static final int TURN_SOUTHWEST_TO_SOUTH = 10;
  private static final int TURN_SOUTH_TO_SOUTHWEST = 11;
  private static final int TURN_SOUTH_TO_SOUTHEAST = 12;
  private static final int TURN_SOUTHEAST_TO_SOUTH = 13;
  private static final int TURN_SOUTHEAST_TO_EAST = 14;
  private static final int TURN_EAST_TO_SOUTHEAST = 15;
  
  private static final int MOVING_EAST = 16;
  private static final int MOVING_NORTHEAST = 17;
  private static final int MOVING_NORTH = 18;
  private static final int MOVING_NORTHWEST = 19;
  private static final int MOVING_WEST = 20;
  private static final int MOVING_SOUTHWEST = 21;
  private static final int MOVING_SOUTH = 22;
  private static final int MOVING_SOUTHEAST = 23;
  
  private static final int TURN_EAST_TO_NORTH = 24;
  private static final int TURN_NORTH_TO_EAST = 25;
  private static final int TURN_NORTH_TO_WEST = 26;
  private static final int TURN_WEST_TO_NORTH = 27;
  private static final int TURN_WEST_TO_SOUTH = 28;
  private static final int TURN_SOUTH_TO_WEST = 29;
  private static final int TURN_SOUTH_TO_EAST = 30;
  private static final int TURN_EAST_TO_SOUTH = 31;
  
  private static final int TURN_EAST_TO_WEST = 32;
  private static final int TURN_WEST_TO_EAST = 33;
  private static final int TURN_NORTH_TO_SOUTH = 34;
  private static final int TURN_SOUTH_TO_NORTH = 35;
  
  private static final int[] animationFrames = {7, 8, 7, 8, 7, 8, 7, 8};

  private static final int TURN45_NUM_FRAMES = 8;
  public boolean[] animations;
  private BufferedImage[] movingDirection;
  
  public Player(BlockMap blockMap)
  {
    super(blockMap);
    init();

    loadSprites();
    
    currentAction = 16;
    animation.setFrames(sprites.get(16));
    animation.setDelay(-1);
  }
  
  private void init() {
    width = 68;
    height = 64;
    cWidth = 52;
    cHeight = 44;
    
    moveSpeed = 0.3;
    maxSpeed = 1.6;
    stopSpeed = 0.4;
    flinchTimer = 100000;
    
    health = maxHealth = 5;
    bulletDamage = 1;
    
    sprites = new ArrayList<>();
    animations = new boolean[28];
    movingDirection = new BufferedImage[8];
    Arrays.fill(animations, Boolean.FALSE);
    animation = new Animation();
    
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
    BufferedImage[] frames = new BufferedImage[TURN45_NUM_FRAMES];
    int frameCounter = 0;
    int count = 0;
    int mvCount = 0;
    
    //frames rotating 45 degrees clockwise
    BufferedImage[] reverseFrames = new BufferedImage[TURN45_NUM_FRAMES];
    int reverseCount = TURN45_NUM_FRAMES;
    
    if (dir.isDirectory())
    { 
      for (final File f : dir.listFiles(IMAGE_FILTER))
      {
        try {
          BufferedImage image = ImageIO.read(f);
          image = makeBackgroundTransparent(image);
          
          if (count == 0) {
            movingDirection[mvCount++] = image;
          }
          
          frames[count++] = image;
          reverseFrames[--reverseCount] = image;
          
          //adds frames and reverseFrames to sprites ArrayList
          if (count == animationFrames[frameCounter]) {
            sprites.add(frames);
            sprites.add(reverseFrames);
            frames = new BufferedImage[TURN45_NUM_FRAMES];
            reverseFrames = new BufferedImage[TURN45_NUM_FRAMES];
            count = 0;
            reverseCount = TURN45_NUM_FRAMES;
            frameCounter++;
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }
    }
    for (int i = 0; i < mvCount; i++) {
      BufferedImage[] img = new BufferedImage[1];
      img[0] = movingDirection[i];
      sprites.add(img);
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
    if (east || northEast || southEast) {
      xSpeed += moveSpeed;
      if (xSpeed > maxSpeed) {
        xSpeed = maxSpeed;
      }
    }
    //moving in negative x direction
    if (west || northWest || southWest) {
      xSpeed -= moveSpeed;
      if (xSpeed < -maxSpeed) {
        xSpeed = -maxSpeed;
      }
    }
    //moving in positive y direction
    if (south || southWest || southEast) {
      ySpeed += moveSpeed;
      if (ySpeed > maxSpeed) {
        ySpeed = maxSpeed;
      }
    }
    //moving in negative y direction
    if (north || northWest || northEast) {
      ySpeed -= moveSpeed;
      if (ySpeed < -maxSpeed) {
        ySpeed = -maxSpeed;
      }
    }
    //not moving in any direction
    if (isNotMoving()) {
      //stop moving in the x direction
      if (xSpeed > 0) {
        xSpeed -= stopSpeed;
        if (xSpeed < 0) {
          xSpeed = 0;
        }
      }
      else if (xSpeed < 0) {
        xSpeed += stopSpeed;
        if (xSpeed > 0) {
          xSpeed = 0;
        }
      }
      //stop moving in the y direction
      if (ySpeed > 0) {
        ySpeed -= stopSpeed;
        if (ySpeed < 0) {
          ySpeed = 0;
        }
      }
      else if (ySpeed < 0) {
        ySpeed += stopSpeed;
        if (ySpeed > 0) {
          ySpeed = 0;
        }
      }
    }
  }
  
  private boolean isNotMoving() {
    return !north && !northEast
        && !east && !southEast && !south
        && !southWest && !west && !northWest;
  }
  
  public void update() {
    getNextPosition();
    checkBlockMapCollision();
    setPosition(xTemp, yTemp);
    
    int temp;
    int temp2;
    //set animation
    
    for(int i = 0; i < animations.length; i++) {
      
      if (animations[i] == true) {
        if (currentAction != i) {
          
          //turn 45 degrees
          if (i < 16) {
            prevAction = currentAction;
            currentAction = i;
            animation.setFrames(sprites.get(i));
            animation.setDelay(10);
            width = 30;
            break;
          }
          
          //moving in a direction
          if (i > 15 && i < 24) {
            prevAction = currentAction;
            currentAction = i;
            animation.setFrames(sprites.get(i));
            animation.setDelay(-1);
            width = 30;
            break; 
          }
          
          //turn 180 degrees
          if (i > 23 && i < 32) {
            prevAction = currentAction;
            currentAction = i;
            temp = (i - 16) * 2;
            temp2 = (temp - 16) * 2;
            
            animation.setFrames(sprites.get(temp2));
            animation.setDelay(2);
            temp2 += 2;
            animation.setFrames(sprites.get(temp2));
            animation.setDelay(2);
            width = 30;
            
            temp += 2;
            temp2 = (temp - 16) * 2;
            
            animation.setFrames(sprites.get(temp2));
            animation.setDelay(2);
            temp2 += 2;
            animation.setFrames(sprites.get(temp2));
            animation.setDelay(2);
            width = 30;
            break;
          }
          
          //turn 90 degrees
          if (i > 31) {
            prevAction = currentAction;
            currentAction = i;
            temp = (i - 16) * 2;
            
            animation.setFrames(sprites.get(temp));
            animation.setDelay(5);
            temp += 2;
            animation.setFrames(sprites.get(temp));
            animation.setDelay(5);
            width = 30;
            break;
          }
        }
      }
    }
    animation.update();
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
    
    g.drawImage(animation.getImage(),
        (int)(x + xMap - width / 2),
        (int)(y + yMap - height / 2),
        null);
    
  }
  
  

}
