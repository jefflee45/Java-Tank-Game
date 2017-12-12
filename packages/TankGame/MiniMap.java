
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
    
    width = 250;
    height = 150;
    
    blockMap = new BlockMap();
    
    
    
    miniMapRatioHeight = height / (blockMap.getHeight());
    miniMapRatioWidth = width / (blockMap.getWidth());
    
  }
  
  public void updatePlayers(Player p1, Player p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
  
  public void draw(Graphics2D m) {
    m.setColor(Color.BLACK);
    m.fillRect(375, 10, (int) width, (int) height); //(x-pos, y-pos, width, height)

    
    m.setColor(Color.RED); //p1 color
    m.fillOval((int)(375 + p1.getX() * miniMapRatioWidth),(int)(10 + p1.getY() * miniMapRatioHeight), 10, 10);
    
    m.setColor(Color.BLUE); //p1 color
    m.fillOval((int)(375 + p2.getX() * miniMapRatioWidth),(int)(10 + p2.getY() * miniMapRatioHeight), 10, 10);
    System.out.println(p1.getX());
    System.out.println(p2.getY());
    
  }
  
}
