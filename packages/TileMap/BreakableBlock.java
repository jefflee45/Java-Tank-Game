package TileMap;

import TileMap.Block;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BreakableBlock extends Block{

  public BreakableBlock (BufferedImage image) {
    this.image = image;
    this.type = Block.BREAKABLE;
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
