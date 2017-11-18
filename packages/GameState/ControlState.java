/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import TankGame.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author jeffreylee
 */
public class ControlState extends GameState {

    private Background bg;
    private Font font;
    private String[] controls = {"Up", "Down", "Left", "Right", "Shoot"};
    private String[] player2Controls = {"Up Arrow Key", "Down Arrow Key", "Left Arrow Key", "Right Arrow Key", "Space"};
    private String[] player1Controls = {"W Key", "S Key", "A Key", "D Key", "V Key"};

    public ControlState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new Background("Resources/TankGameMenuLarge.jpg");
            font = new Font("Arial", Font.PLAIN, 28);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D gLeftScreen, Graphics2D gRightScreen) {
        bg.draw(gLeftScreen);
        gLeftScreen.setColor(Color.WHITE);
        gLeftScreen.drawString("Player 1", 125, 65);
        gLeftScreen.setFont(new Font("Arial", Font.PLAIN, 20));
        
        bg.draw(gRightScreen);
        gRightScreen.setColor(Color.WHITE);
        gRightScreen.drawString("Player 2", 125, 65);
        gRightScreen.setFont(new Font("Arial", Font.PLAIN, 20));

        //player one
        for (int i = 0; i < 5; i++) {
            gLeftScreen.drawString(controls[i] + ": ", 100, 100 + i * 35);
            gLeftScreen.drawString(player2Controls[i], 170, 100 + i * 35);
            gRightScreen.drawString(controls[i] + ": ", 100, 100 + i * 35);
            gRightScreen.drawString(player1Controls[i], 170, 100 + i * 35);
        }
        
        //back button settings
        gLeftScreen.setFont(font);
        gLeftScreen.setColor(Color.RED);
        gLeftScreen.drawString("Back", 320, 300);
        
        gRightScreen.setFont(font);
        gRightScreen.setColor(Color.RED);
        gRightScreen.drawString("Back", 320, 300);
        
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void keyReleased(int k) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
