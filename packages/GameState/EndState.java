
package GameState;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class EndState extends GameState{
    
    private Font font;
    
    
    public EndState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    protected void init() {
        font = new Font("Arial", Font.PLAIN, 40);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D gEndScreen, Graphics2D nullValue, Graphics2D nullValue2) {
//    public void draw(Graphics2D gEndScreen, Graphics2D nullValue, Graphics2D nullValue2, Graphics2D nullValue3) {
       gEndScreen.setFont(font);
       gEndScreen.drawString("Game Over!", 300, 100);
       gEndScreen.drawString("Press Escape to go back to the menu", 200, 300);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void keyReleased(int k) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
