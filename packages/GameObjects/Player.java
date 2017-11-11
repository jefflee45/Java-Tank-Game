package GameObjects;

import BlockMap.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Player extends GameObject {

    private File dir;
  private String[] EXTENSIONS;
  private FilenameFilter IMAGE_FILTER;
  
  private int health, maxHealth;
  private int score;
  
  private boolean dead;
  
  //attacking
  private boolean firing;
  private int bulletDamage;
  
  //animations
  ArrayList<BufferedImage[]> sprites;
  
  //animation action index for sprites ArrayList
  //relative to counter clockwise motion
  //0 degrees = pointing in positive x axis
  private static final int TURN_0to45 = 0;
  private static final int TURN_45to0 = 1;

  private static final int TURN_45to90 = 2;
  private static final int TURN_90to45 = 3;

  private static final int TURN_90to135 = 4;
  private static final int TURN_135to905 = 5;

  private static final int TURN_135to180 = 6;
  private static final int TURN_180to135 = 7;

  private static final int TURN_180to225 = 8;
  private static final int TURN_225to180 = 9;

  private static final int TURN_225to270 = 10;
  private static final int TURN_270to225 = 11;

  private static final int TURN_270to315 = 12;
  private static final int TURN_315to270 = 13;

  private static final int TURN_315to360 = 14;
  private static final int TURN_360to315 = 15;

  private static final int TURN45_NUM_FRAMES = 8;
  
  public Player(BlockMap blockMap)
  {
    super(blockMap);
    sprites = new ArrayList<>();
    loadSprites();
  }
  
  private void loadSprites() {
    loadFramesFromFolder("Resources/t1an32pxl");
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
    int count = 0;
    
    //frames rotating 45 degrees clockwise
    BufferedImage[] reverseFrames = new BufferedImage[TURN45_NUM_FRAMES];
    int reverseCount = TURN45_NUM_FRAMES;
    
    if (dir.isDirectory())
    { 
      for (final File f : dir.listFiles(IMAGE_FILTER))
      {
        try {
          BufferedImage image = ImageIO.read(f);
          frames[count++] = image;
          reverseFrames[--reverseCount] = image;
          
          //adds frames and reverseFrames to sprites ArrayList
          if (count == TURN45_NUM_FRAMES) {
            sprites.add(frames);
            sprites.add(reverseFrames);
            frames = new BufferedImage[TURN45_NUM_FRAMES];
            reverseFrames = new BufferedImage[TURN45_NUM_FRAMES];
            count = 0;
            reverseCount = TURN45_NUM_FRAMES;
          }
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
  
  public void update() {
    
  }
  
  

}
