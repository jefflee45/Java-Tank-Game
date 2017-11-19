package TankGame;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class HUD
{ 
  private BufferedImage image;
  
  public HUD(String s) {
    try {
      image = ImageIO.read(new File(s));
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void draw(Graphics2D g) {
    g.drawImage(image, 0, 0, null);
  }
}

