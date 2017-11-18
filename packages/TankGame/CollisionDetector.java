package TankGame;

import GameObjects.Player;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionDetector {
  
  
  public CollisionDetector() {
    
  }
  
  public boolean checkCollision(Object obj1, Object obj2) {
     
    //player on player collision
    if (obj1 instanceof Player && obj2 instanceof Player) {
      if (((Player)obj1).intersects((Player)obj2)) {
        System.out.println("THEY INTERSECT");
              return true;

      }
          System.out.println(".");

    }
    
    return false;
  }
}
