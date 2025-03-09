package gui.panel;

import config.GameConfiguration;
import config.MapBuilder;
import gui.process.GUILoader;
import gui.process.ListenerBehavior;

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

    public ChargeGameMenu(int token, MapBuilder map) {
        super();
        this.token = token;
        this.map = map;
        init();
    }
    public void init(){
        this.setLayout(new BorderLayout());
        this.addKeyListener(new KeyControls());


    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior ls = ListenerBehavior.create();
            ls.goBack(token, map);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehavior ls = ListenerBehavior.create();
                ls.goBack(token, map);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
