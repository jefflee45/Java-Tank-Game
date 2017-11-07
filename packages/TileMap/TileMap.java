package TileMap;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class TileMap
{
  private double x;
  private double y;
  
  private int xmin;
  private int ymin;
  private int xmax;
  private int ymax;
  
  //map
  private int[][] map;
  private int tileSize;
  private int numRows;
  private int numCols;
  private int width;
  private int height;
  
  //tileset
  private BufferedImage tileset;
  private int numTilesAcross;
  //private Tile[][] tiles;
  
  public TileMap (int tilesSize) {
    this.tileSize = tileSize;
    //numRowsToDraw = GamePanel.HEIGHT/tileSize + 2;
  
  
  }
  
  public void loadTiles(String s) {
   // try {
      //tileset = ImageIO.read
    //}
  }
}
