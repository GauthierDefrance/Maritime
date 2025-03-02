package experimental_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChargeGameMenu extends SimpleMenu {

    private int token;

    private JButton goBackButton;
    private JButton loadFile1;
    private JButton loadFile2;
    private JButton loadFile3;

    public ChargeGameMenu(int token, Container window) {
        super(window);
        this.token = token;
        init();
    }
    public void init(){
        this.addKeyListener(new KeyControls());
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == 0) GUILoader.loadStartMenu(getWindow());
            else GUILoader.loadPauseMenu(1,getWindow());
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if (token == 0) GUILoader.loadStartMenu(getWindow());
                else GUILoader.loadPauseMenu(0,getWindow());
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
