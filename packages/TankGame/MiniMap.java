
package TankGame;

import BlockMap.Block;
import BlockMap.BlockMap;
import GameObjects.Player;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Jeffrey
 */
public class MiniMap {
  private Player p1;
  private Player p2;
  
  private BlockMap blockMap;
  
  private double miniMapRatioHeight;
  private double miniMapRatioWidth;
  private double height;
  private double width;
  public static final int EMPTY_TILE = 0;
  public static final int BREAKABLE = 1;
  public static final int UNBREAKABLE = 2;
  
  public MiniMap(Player p1, Player p2) {
    this.p1 = p1;
    this.p2 = p2;
    
    width = 240;
    height = 180;
    
    blockMap = new BlockMap();
    
    miniMapRatioHeight = height / (blockMap.getHeight());
    miniMapRatioWidth = width / (blockMap.getWidth());
    
  }
  
  public void updatePlayers(Player p1, Player p2, BlockMap bm) {
    this.p1 = p1;
    this.p2 = p2;
    this.blockMap = bm;
  }
  
  public void draw(Graphics2D m) {
    m.setColor(Color.BLACK);
    m.fillRect(375, 100, (int) width, (int) height); //(x-pos, y-pos, width, height)
    
    for(int i = 0; i < blockMap.getRows(); i++) {
      for(int j = 0; j < blockMap.getColumns(); j++) {
        if(blockMap.getType(i, j) == BREAKABLE) {
          //paint breakable block
          m.setColor(Color.RED);
          m.fillRect(
              (int)(375 + j * width/blockMap.getColumns()),
              (int)(100 + i * height/blockMap.getRows()),
              (int)(width/blockMap.getColumns()),
              (int)(height/blockMap.getRows()));
          
        }
        else if(blockMap.getType(i, j) == UNBREAKABLE) {
          //paint unbreakable block
          m.setColor(Color.ORANGE);
          m.fillRect(
              (int)(375 + j * width/blockMap.getColumns()),
              (int)(100 + i * height/blockMap.getRows()),
              (int)(width/blockMap.getColumns()),
              (int)(height/blockMap.getRows()));
        }
        
        
        
        //paint power-ups
        
        
      }
    }
    
    
    m.setColor(Color.RED); //p1 color
    m.fillOval((int)(370 + p1.getX() * miniMapRatioWidth),(int)(95 + p1.getY() * miniMapRatioHeight), 8, 8);
    
    m.setColor(Color.BLUE); //p1 color
    m.fillOval((int)(370 + p2.getX() * miniMapRatioWidth),(int)(95 + p2.getY() * miniMapRatioHeight), 8, 8);
    
  }
  
}
