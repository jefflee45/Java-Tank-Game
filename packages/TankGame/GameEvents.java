package TankGame;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class GameEvents extends Observable{
    int type;
    final int keyE = 1;
    final int collision = 2;
    int collisionDamage;
    Object event;
       
    public void setValue(KeyEvent e) {
        type = keyE; // let's assume this means key input. 
        //Should use CONSTANT value for this when you program
        event = e;
        setChanged();
        // trigger notification
        notifyObservers(this);  
   }

    public void setValue(String msg) {
        type = collision; 
        event = msg;
        setChanged();
        
       // trigger notification
       notifyObservers(this);  
    }
    
    public int getType(){
        return this.type;
    }
    
    public Object getEvent(){
        return this.event;
    }
    
}
