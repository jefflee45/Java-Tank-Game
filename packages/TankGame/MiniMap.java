
package TankGame;

import GameObjects.Player;
import Main.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Jeffrey
 */
public class MiniMap {
  private Player p1;
  private Player p2;
  
  private GamePanel gamePanel;
  
  private double miniMapRatioHeight;
  private double miniMapRatioWidth;
  private double height;
  private double width;
  
  public MiniMap(Player p1, Player p2) {
    this.p1 = p1;
    this.p2 = p2;
    
    width = 250;
    height = 150;
    
    gamePanel = new GamePanel();
    
    miniMapRatioHeight = height / GamePanel.HEIGHT;
    miniMapRatioWidth = width / GamePanel.WIDTH;
    System.out.println(width);
    System.out.println(height);
    System.out.println(GamePanel.HEIGHT);
    System.out.println(GamePanel.WIDTH);
    System.out.println(miniMapRatioHeight);
    System.out.println(miniMapRatioWidth);
    
  }
  
  public void updatePlayers(Player p1, Player p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
  
  public void draw(Graphics2D m) {
    m.setColor(Color.BLACK);
    m.fillRect(375, 100, (int) width, (int) height);
    
    m.setColor(Color.RED); //p1 color
    m.fillOval((int)(375 + p1.getX() * miniMapRatioWidth),(int)(100 + p1.getY() * miniMapRatioHeight), 10, 10);
    
    m.setColor(Color.BLUE); //p1 color
    m.fillOval((int)(375 + p2.getX() * miniMapRatioWidth),(int)(100 + p2.getY() * miniMapRatioHeight), 10, 10);
    
  }
  
}
