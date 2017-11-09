package GameObjects;

import BlockMap.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends GameObject {

  private int health, maxHealth;
  
  public Player(BlockMap blockMap)
  {
    super(blockMap);
  }

}
