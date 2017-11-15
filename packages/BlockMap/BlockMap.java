package BlockMap;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BlockMap
{
  //position
  private double x;
  private double y;
  private double tween;
  
  //bounds
  private int xMin, yMin, xMax, yMax;
  
  //blockset
  private BufferedImage breakableBlock;
  private BufferedImage unbreakableBlock;
  
  //map
  private int blockSize;
  private int rows;
  private int columns;
  private Block[][] blockMap;

  
  private int width;
  private int height;
  
  //drawing
  private int rowOffset;
  private int colOffset;
  private int numRowsToDraw;
  private int numColsToDraw;

  public BlockMap () {
    init();
    loadBlocks();  
  }
  
  private void init() {
    blockSize = 32;
    rows = 30;
    columns = 40;
    width = columns * blockSize;
    height = rows * blockSize;
    blockMap = new Block[rows][columns];
    numRowsToDraw = GamePanel.HEIGHT / blockSize + 2;
    numColsToDraw = GamePanel.WIDTH / blockSize + 2;
    xMin = GamePanel.WIDTH - width;
    yMin = GamePanel.HEIGHT - height;
    xMax = 0;
    yMax = 0;
    tween = 0.07;
  }
  
  private void loadBlocks() {
    try {
      
      InputStream in = new FileInputStream("Resources/level.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      int xBlock = 0;
      int yBlock = 0;
      
      //for loop to load the blocks into blockMap
      for (int i = 0; i < rows; i++) {
        line = br.readLine();
        for (int j = 0; j < columns; j++) {
          if (line.charAt(j) != '.') {
            try {
              blockMap[i][j] = new Block(Integer.parseInt(Character.toString(line.charAt(j))),
                                        xBlock, yBlock, blockSize, blockSize);
            }
            catch (Exception e) {
              e.printStackTrace();
            } 
          } else {
            blockMap[i][j] = new Block(Block.EMPTY_TILE,
                                      xBlock, yBlock, blockSize, blockSize);
          }
          yBlock += blockSize;
        }
        xBlock += blockSize;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  public void draw(Graphics2D g) {
    try {
      
      //for loop to draw the blocks
      for (int row = rowOffset; row < (rowOffset + numRowsToDraw); row++) {
        if (row >= rows) {
          break;
        }
        
        for (int col = colOffset; col < (colOffset + numColsToDraw); col++) {
          if( col >= columns) {
            break;
          }
          if (blockMap[row][col].getType() == Block.EMPTY_TILE) {
            continue;
          }
          g.drawImage(blockMap[row][col].getImage(),
              (int)x + col * blockSize, (int)y + row * blockSize, null);
        }

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
  
  public int getX() {
    return (int)x;
  }
  
  public int getY() {
    return (int)y;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public void setTween(double d) {
    tween = d;
  }
  
  public void setPosition (double x, double y) {
    this.x += (x - this.x) * tween;
    this.y += (y - this.y) * tween;
    
    fixBounds();
    
    colOffset = (int)-this.x / blockSize;
    rowOffset = (int)-this.y / blockSize;
  }
  
  private void fixBounds() {
    if (x < xMin) {
      x = xMin;
    }
    
    if (y < yMin) {
      y = yMin;
    }
    
    if (x > xMax) {
      x = xMax;
    }
    
    if (y > yMax) {
      y = yMax;
    }
  }
}
