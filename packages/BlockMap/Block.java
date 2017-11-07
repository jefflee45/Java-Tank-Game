package BlockMap;

import java.awt.image.BufferedImage;

public abstract class Block{
  
  protected BufferedImage image;
  protected int type;
  
  public static final int BREAKABLE = 0;
  public static final int UNBREAKABLE = 1;
  
  public abstract BufferedImage getImage();
  
  public abstract int getType();
}
