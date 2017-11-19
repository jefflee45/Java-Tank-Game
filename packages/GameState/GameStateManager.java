package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

/*
Switches between different Game States
*/
public class GameStateManager
{
  private ArrayList<GameState> gameStates;
  private int currentState;
  
  public static final int MENUSTATE = 0;
  public static final int LEVELSTATE = 1;
  public static final int CONTROLSTATE = 2;
  
  public GameStateManager() {
    
    gameStates = new ArrayList<GameState>();
    
    currentState = MENUSTATE;
    
    gameStates.add(new MenuState(this));
    gameStates.add(new LevelState(this));
    gameStates.add(new ControlState(this));
  }
  
  public void setState(int state) {
    currentState = state;
    gameStates.get(currentState).init();
  }
  
  public void update() {
    gameStates.get(currentState).update();
  }
  
  public void draw(Graphics2D gLeftScreen, Graphics2D gRightScreen, Graphics2D gHUDScreen) {
    gameStates.get(currentState).draw(gLeftScreen, gRightScreen, gHUDScreen);
  }
  
  public void keyPressed(int k) {
    gameStates.get(currentState).keyPressed(k);
  }
  
  public void keyReleased(int k) {
    gameStates.get(currentState).keyReleased(k);
  }
  
  public int getCurrentState() {
    return currentState;
  }
}
