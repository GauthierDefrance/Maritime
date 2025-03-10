package gui.panel;

import config.GameConfiguration;
import config.GameParameter;
import config.MapBuilder;
import gui.MainGUI;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;
import gui.process.ListenerBehavior;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * options menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 * @version 0.4
 */
public class OptionsMenu extends SimpleMenu {

    private int token;

    private JButton goBackButton;

    private JPanel optionDisplayer;

    private JPanel soundOptionPanel;
    private JPanel soundPanel;
    private JLabel soundLabel;
    private JButton plusButton;
    private JButton minusButton;

    private JLabel muteLabel;
    private JButton muteButton;
    private JPanel mutePanel;

    private JLabel debugLabel;
    private JButton debugButton;
    private JPanel debugPanel;

    public OptionsMenu(int token) {
        super();
        this.token = token;
        init();
    }

    //Utilities

    /**
     * Gives the correct Text for the Button
     * @param active boolean linked with the Button Behavior
     * @return Expected text
     */
    private String textSetter(boolean active){
        if (active) return "Off"; //if active --> user may want to turn off
        return "On";
    }

    /**
     * Build a simple Line for the OptionsMenu
     * @param component1 JComponent that must be put at the start of this line
     * @param component2 JComponent that must be put at the end of this line
     * @return Completed line
     */
    private JPanel lineMaker(JComponent component1, JComponent component2) {
        return JComponentBuilder.gridMenuPanel(1,2,GameConfiguration.BUTTON_SEPARATOR, GameConfiguration.BUTTON_SEPARATOR, component1, component2);
    }

    //Initialisation
    public void init() {

        this.setLayout(new BorderLayout());

        goBackButton = JComponentBuilder.menuButton("Go back", new goBackButtonListener());

        soundLabel = JComponentBuilder.menuLabel("Sound Level");
        plusButton = JComponentBuilder.menuButton("+", new plusButtonListener());
        minusButton = JComponentBuilder.menuButton("-", new minusButtonListener());
        soundOptionPanel = lineMaker(plusButton, minusButton);
        soundPanel = lineMaker(soundLabel, soundOptionPanel);

        muteLabel = JComponentBuilder.menuLabel("Mute");
        muteButton = JComponentBuilder.menuButton(textSetter(GameParameter.isMuted), new muteButtonListener());
        mutePanel = lineMaker(muteLabel, muteButton);

        debugLabel = JComponentBuilder.menuLabel("Debug Menu");
        debugButton = JComponentBuilder.menuButton(textSetter(GameParameter.showDebug), new debugMenuListener());
        debugPanel = lineMaker(debugLabel, debugButton);

        optionDisplayer = JComponentBuilder.gridMenuPanel(4,1, GameConfiguration.BUTTON_SEPARATOR, GameConfiguration.BUTTON_SEPARATOR, goBackButton, soundPanel, mutePanel, debugPanel);

        this.addKeyListener(new KeyControls());
        this.add(optionDisplayer, BorderLayout.CENTER);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.NORTH);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.SOUTH);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.WEST);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.EAST);

    }

    //Listener

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
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            GameParameter.isMuted = listenerBehavior.toggle(muteButton, GameParameter.isMuted);
        }
    }

    public class debugMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            GameParameter.showDebug = listenerBehavior.toggle(debugButton, GameParameter.showDebug);
        }
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            listenerBehavior.goBack(token);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehavior listenerBehavior = ListenerBehavior.create();
                listenerBehavior.goBack(token);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
