package BlockMap;

import java.awt.image.BufferedImage;

public class UnbreakableBlock extends Block{
  
  public UnbreakableBlock (BufferedImage image) {
    this.image = image;
    this.type = Block.UNBREAKABLE;
  }
  
  @Override
  public BufferedImage getImage()
  {
    return image;
  }

  @Override
  public int getType()
  {
    return type;
  }
}
