package gui.panel;


import gui.utilities.JComponentBuilder;
import gui.process.ListenerBehaviorManager;
import saveSystem.process.OptSaveManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static config.GameOptions.getInstance;
import static config.GameConfiguration.*;

/**
 * options menu for the game
 * @author Zue Jack-Arthur
 * @version 0.6
 */
public class OptionsMenu extends JPanel {

    private final int token;

    private JButton muteButton;

    private JButton debugButton;

    /**
     * Build the OptionsMenu using a token
     * @param token former GUI JPanel identifier
     */
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
        if (active) return "turn Off"; //if active --> user may want to turn off
        return "turn On";
    }

    /**
     * Build a simple Line for the OptionsMenu
     * @param component1 JComponent that must be put at the start of this line
     * @param component2 JComponent that must be put at the end of this line
     * @return Completed line
     */
    private JPanel lineMaker(JComponent component1, JComponent component2) {
        return JComponentBuilder.gridMenuPanel(1,2,BUTTON_SEPARATOR, BUTTON_SEPARATOR, component1, component2);
    }

    /**
     * Avoid code-redundancy, by streamlining the action of writing to the "Options save file"
     */
    private void updateLinkedFile(){
        OptSaveManager.create().writeParamFile();
    }

    //Initialisation

    /**
     * Makes all necessary operations to initialize the panel
     */
    public void init() {

        this.setLayout(new BorderLayout());

        JButton goBackButton = JComponentBuilder.menuButton("Go back", new goBackButtonListener());

        JLabel soundLabel = JComponentBuilder.menuLabel("Sound Level");
        JButton plusButton = JComponentBuilder.menuButton("+", new plusButtonListener());
        JButton minusButton = JComponentBuilder.menuButton("-", new minusButtonListener());
        JPanel soundOptionPanel = lineMaker(plusButton, minusButton);
        JPanel soundPanel = lineMaker(soundLabel, soundOptionPanel);

        JLabel muteLabel = JComponentBuilder.menuLabel("Mute");
        muteButton = JComponentBuilder.menuButton(textSetter(getInstance().getIsMuted()), new muteButtonListener());
        JPanel mutePanel = lineMaker(muteLabel, muteButton);

        JLabel debugLabel = JComponentBuilder.menuLabel("Debug Menu");
        debugButton = JComponentBuilder.menuButton(textSetter(getInstance().getShowDebug()), new debugMenuListener());
        JPanel debugPanel = lineMaker(debugLabel, debugButton);

        JPanel optionDisplay = JComponentBuilder.gridMenuPanel(4, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, goBackButton, soundPanel, mutePanel, debugPanel);

        this.addKeyListener(new KeyControls());
        this.add(optionDisplay, BorderLayout.CENTER);
        for (String position : new String[]{BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
            this.add(JComponentBuilder.voidPanel(), position);
        }

    }

    //Listener

    public class minusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            getInstance().setVolume(lbm.decrement(MIN_SOUND_LEVEL, getInstance().getVolume()));
            updateLinkedFile();
        }
    }

    public class plusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            getInstance().setVolume(lbm.increment(MAX_SOUND_LEVEL, getInstance().getVolume()));
            updateLinkedFile();
        }
    }

    public class muteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getInstance().setIsMuted(ListenerBehaviorManager.create().toggle(muteButton, getInstance().getIsMuted()));
            updateLinkedFile();
        }
    }

    public class debugMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            getInstance().setShowDebug(lbm.toggle(debugButton, getInstance().getShowDebug()));
            updateLinkedFile();
        }
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager.create().goBack(token);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehaviorManager.create().goBack(token);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
