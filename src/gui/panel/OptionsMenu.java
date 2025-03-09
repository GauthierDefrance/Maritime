package gui.panel;

import config.GameConfiguration;
import config.MapBuilder;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * options menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class OptionsMenu extends SimpleMenu {

    private int token;
    private MapBuilder map;

    private JButton goBackButton;
    private JPanel goBackPanel;

    private JPanel optionDisplayer;
    private JPanel suboptionDisplayer;

    private JPanel soundPanel;
    private JLabel soundLevel;
    private JButton plusButton;
    private JButton minusButton;

    private JLabel muteLabel;
    private JButton muteButton;

    private JLabel debugLabel;
    private JButton debugButton;


    public OptionsMenu(int token, Container window, MapBuilder map) {
        super(window);
        this.token = token;
        this.map = map;
        init();
    }

    public void init() {

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        goBackButton = JComponentBuilder.menuButton("Go back", new goBackButtonListener());
        goBackPanel = JComponentBuilder.flowMenuPanel(goBackButton);

        soundLevel = JComponentBuilder.menuLabel("Sound Level");

        plusButton = JComponentBuilder.menuButton("+", new plusButtonListener());

        minusButton = JComponentBuilder.menuButton("-", new minusButtonListener());

        soundPanel = JComponentBuilder.flowMenuPanel(plusButton, minusButton);

        muteLabel = JComponentBuilder.menuLabel("Mute");
        muteButton = JComponentBuilder.menuButton("Off", new muteButtonListener());

        debugLabel = JComponentBuilder.menuLabel("Debug Menu");
        debugButton = JComponentBuilder.menuButton("Off", new debugMenuListener());

        optionDisplayer = JComponentBuilder.borderMenuPanel();
        suboptionDisplayer = JComponentBuilder.gridMenuPanel(3,2,soundLevel, soundPanel, muteLabel, muteButton, debugLabel, debugButton);
        optionDisplayer.add(goBackPanel, BorderLayout.NORTH);
        optionDisplayer.add(suboptionDisplayer, BorderLayout.CENTER);

        this.addKeyListener(new KeyControls());
        this.add(optionDisplayer);
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == GameConfiguration.ROOT_STARTMENU) GUILoader.loadStartMenu(getWindow());
            else GUILoader.loadPauseMenu(token,getWindow(),map);
        }
    }

    public class minusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
        }
    }

    public class plusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
        }
    }

    public class muteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (muteButton.getText().equals("Off")) {
                muteButton.setText("On");
            } else muteButton.setText("Off");
        }
    }

    public class debugMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
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
