package BlockMap;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Block{
  
  private BufferedImage image;
  private Rectangle rect;
  private int type;
  
  public static final int EMPTY_TILE = 0;
  public static final int BREAKABLE = 1;
  public static final int UNBREAKABLE = 2;
  
  public Block(int type, int x, int y, int width, int height) {
    this.type = type;
    rect = new Rectangle(x, y, width, height);
    loadImage();
  }
  
  public BufferedImage getImage() {
    return image;
  }
  
  private void loadImage()
  {
    try
    {
      if (type == Block.BREAKABLE)
      {
        image = ImageIO.read(new File("Resources/Wall2.gif"));
      }

      if (type == Block.UNBREAKABLE)
      {
        image = ImageIO.read(new File("Resources/Wall1.gif"));

      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public int getType() {
    return type;
  }
  
  public Rectangle getRectangle() {
    return rect;
  }
}
