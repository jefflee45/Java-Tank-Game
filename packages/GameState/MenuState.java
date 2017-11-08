package GameState;

import TankGame.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState
{
  private Background bg;
  
  private String[] options = { "  Start", "Controls", "   Quit" };
  private int currentChoice;
  private Color titleColor;
  private Font titleFont;
  private Font font;
  
  public MenuState(GameStateManager gsm) {
     this.gsm = gsm;
     
     try {
       bg = new Background("Resources/TankGameMenuLarge.jpg");
       
       titleColor = new Color(128, 0, 0);
       titleFont = new Font("Century Gothic", Font.PLAIN, 36);
       font = new Font("Arial", Font.PLAIN, 28);
     } 
     catch (Exception e) {
       e.printStackTrace();
     }
  }

  @Override
  public void init()
  {
  }

  @Override
  public void update()
  {
  }

  @Override
  public void draw(Graphics2D g)
  {
    //draw background
    bg.draw(g);
    
    //draw title
    g.setColor(titleColor);
    g.setFont(titleFont);
    g.drawString("Tank Game", 240, 100);
    
    //draw menu options
    g.setFont(font);
    for (int i = 0; i < options.length; i++) {
      if(i == currentChoice) {
        g.setColor(Color.RED);
      } else
      {
        g.setColor(Color.GREEN);
      }
      g.drawString (options[i], 280, 160 + i * 35);
    }
  }
  
  public void select() {
    switch(currentChoice) {
      case 0:
        gsm.setState(GameStateManager.LEVELSTATE);
        break;
      case 1:
        gsm.setState(GameStateManager.CONTROLSTATE);
        break;
      case 2:
        System.exit(0);
      default:
        break;
    }
  }

  @Override
  public void keyPressed(int k)
  {
    if (k == KeyEvent.VK_ENTER) {
      select();
    }
    
    if (k == KeyEvent.VK_UP) {
     currentChoice--;
     if (currentChoice == -1) {
       currentChoice = options.length - 1;
     }
    }
    
    if (k == KeyEvent.VK_DOWN) {
      currentChoice++;
      if (currentChoice == options.length) {
        currentChoice = 0;
      }
    }
  }

  @Override
  public void keyReleased(int k)
  {
  }
}
