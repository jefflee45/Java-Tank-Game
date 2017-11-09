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
  
  public Player(BlockMap blockMap)
  {
    super(blockMap);
    loadSprites();
  }
  
  private void loadSprites() {
    
    File dir = new File("Resources/t1an32pxl");

    // array of supported extensions (use a List if you prefer)
    EXTENSIONS = new String[]{
        "gif", "png", "jpg" // and other formats you need
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
    
    if (dir.isDirectory())
    { 
      for (final File f : dir.listFiles(IMAGE_FILTER))
      {
        try {
          BufferedImage image = ImageIO.read(f);

                  //TODO store images in some type of BufferedImage array
                    System.out.println("image: " + f.getName());
                    System.out.println(" width : " + image.getWidth());
                    System.out.println(" height: " + image.getHeight());
                    System.out.println(" size  : " + f.length());
                    System.out.println("NUMBER OF TIMES THROUGH: " + i);

        } catch (Exception e) {
          e.printStackTrace();
        }
        i++;
      }
    }
  }

}
