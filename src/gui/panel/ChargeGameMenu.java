package gui.panel;

import config.GameConfiguration;
import config.MapBuilder;
import gui.process.GUILoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChargeGameMenu extends SimpleMenu {

    private int token;
    private MapBuilder map;

    private JButton goBackButton;
    private JButton loadFile1;
    private JButton loadFile2;
    private JButton loadFile3;

    public ChargeGameMenu(int token, Container window, MapBuilder map) {
        super(window);
        this.token = token;
        this.map = map;
        init();
    }
    public void init(){
        this.addKeyListener(new KeyControls());
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == GameConfiguration.ROOT_STARTMENU) GUILoader.loadStartMenu(getWindow());
            else GUILoader.loadPauseMenu(token,getWindow(),map);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if (token == GameConfiguration.ROOT_STARTMENU) GUILoader.loadStartMenu(getWindow());
                else GUILoader.loadPauseMenu(token,getWindow(),map);
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
