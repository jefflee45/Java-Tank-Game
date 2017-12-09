
package TankGame;

import GameObjects.Player;
import java.awt.Graphics2D;

/**
 *
 * @author Jeffrey
 */
public class MiniMap {
  private Player p1;
  private Player p2;
  
  public MiniMap(Player p1, Player p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
  
  public void updatePlayers(Player p1, Player p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
  
  public void draw(Graphics2D m) {
    
  }
  
}
