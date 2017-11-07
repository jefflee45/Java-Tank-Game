package BlockMap;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BlockMap
{
  private double x;
  private double y;
  
  private BufferedImage breakableBlock;
  private BufferedImage unbreakableBlock;
  
  //map
  private int blockSize;
  private int rows;
  private int columns;
  private int width;
  private int height;

  public BlockMap () {
    init();
    loadBlocks();
    //numRowsToDraw = GamePanel.HEIGHT/tileSize + 2;
  
  }
  
  private void init() {
    blockSize = 32;
    width = 640;
    height = 480;
    rows = 15;
    columns = 20;
  }
  
  private void loadBlocks() {
    try {
      breakableBlock = ImageIO.read(new File("Resources/Wall2.gif"));
      unbreakableBlock = ImageIO.read(new File("Resources/Wall1.gif"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  public void draw(Graphics2D g) {
    try {
      InputStream in = new FileInputStream("Resources/level.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      int yPos = 0;
      //for loop to draw the blocks
      for (int i = 0; i < rows; i++) {
        line = br.readLine();
        System.out.println("Here's the line:\n " + line);
        int xPos = 0;
        for (int j = 0; j < line.length(); j++) {
          switch (line.charAt(j)) {
            case '0':
              g.drawImage(breakableBlock, xPos, yPos, null);
              break;
            case '1':
              g.drawImage(unbreakableBlock, xPos, yPos, null);
              break;
            default:
              break;
          }
          xPos += blockSize;
        }
        yPos += blockSize;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
