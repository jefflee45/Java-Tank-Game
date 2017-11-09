package BlockMap;

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
  private Block[][] blockMap;
  
  //in pixels
  private int width;
  private int height;

  public BlockMap () {
    init();
    loadBlocks();  
  }
  
  private void init() {
    blockSize = 32;
    width = 640;
    height = 480;
    rows = 15;
    columns = 20;
    blockMap = new Block[rows][columns];
  }
  
  private void loadBlocks() {
    try {
      
      InputStream in = new FileInputStream("Resources/level.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      
      //for loop to load the blocks into blockMap
      for (int i = 0; i < rows; i++) {
        line = br.readLine();
        
        for (int j = 0; j < columns; j++) {
          if (line.charAt(j) != '.') {
            try {
              blockMap[i][j] = new Block(Integer.parseInt(Character.toString(line.charAt(j))));
            }
            catch (Exception e) {
              e.printStackTrace();
            } 
          } else {
            blockMap[i][j] = new Block(Block.EMPTY_TILE);
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  public void draw(Graphics2D g) {
    try {
      int yPos = 0;
      
      //for loop to draw the blocks
      for (int i = 0; i < rows; i++) {
        int xPos = 0;
        
        for (int j = 0; j < columns; j++) {
          if (blockMap[i][j].getType() != Block.EMPTY_TILE) {
              g.drawImage(blockMap[i][j].getImage(), xPos, yPos, null);
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
  
  public Block[][] getBlockMap() {
    return blockMap;
  }
  
  
  public int getBlockSize() {
    return blockSize;
  }
  
  public int getType(int row, int col) {
    return blockMap[row][col].getType();
  }
}
