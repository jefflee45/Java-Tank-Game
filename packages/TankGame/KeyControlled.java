package TankGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyControlled extends KeyAdapter {
    private GameEvents gameEvents;
    
    public KeyControlled(){
        
    }
    
    public KeyControlled (GameEvents ge){
        this.gameEvents = ge;
    }
    
    public void keyPressed (KeyEvent e) {
        gameEvents.setValue(e);
    }
    
    public void keyReleased(KeyEvent e){
        gameEvents.setValue(e);
    }
    
    public Object getKeyEvent() {
      return gameEvents.getEvent();
    }
    
}
