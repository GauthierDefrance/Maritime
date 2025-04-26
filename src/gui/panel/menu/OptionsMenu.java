package gui.panel.menu;

import config.GameConfiguration;
import config.GameOptions;
import gui.process.JComponentFactory;
import gui.process.ListenerBehaviorManager;
import music.MusicManager;
import saveSystem.process.OptSaveManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

/**
 * options menu for the game
 * @author Zue Jack-Arthur
 * @version 0.6
 */
public class OptionsMenu extends JPanel {

    private final int token;
    private final Object objectToken;

    private JPanel optionDisplay;
    private JPanel jPanelCenter;
    private JPanel goBack;

    private JButton muteButton;
    private JButton debugButton;

    private JLabel soundLabel;

    /**
     * Build the OptionsMenu using a token
     * @param token former GUI JPanel identifier
     */
    public OptionsMenu(int token, Object objectToken) {
        super();
        this.token = token;
        this.objectToken = objectToken;
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
        return JComponentFactory.gridMenuPanel(1,2,GameConfiguration.BUTTON_SEPARATOR, GameConfiguration.BUTTON_SEPARATOR, component1, component2);
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
    private void init() {

        this.setLayout(new BorderLayout());

        JLabel title = JComponentFactory.title("Options");
        JPanel titleDisplay = JComponentFactory.flowMenuPanel(title);

        JButton goBackButton = JComponentFactory.menuButton("Go back", new GoBackButtonListener());
        soundLabel = JComponentFactory.menuLabel("Sound Level ("+ GameOptions.getInstance().getVolume()+"/"+GameConfiguration.MAX_SOUND_LEVEL+")");
        JButton plusButton = JComponentFactory.menuButton("+", new PlusButtonListener());
        JButton minusButton = JComponentFactory.menuButton("-", new MinusButtonListener());
        JPanel soundOptionPanel = JComponentFactory.gridMenuPanel(1,2,10+GameConfiguration.BUTTON_SEPARATOR,10+GameConfiguration.BUTTON_SEPARATOR,plusButton, minusButton);
        JPanel soundPanel = lineMaker(soundLabel, soundOptionPanel);

        JLabel muteLabel = JComponentFactory.menuLabel("Mute");
        muteButton = JComponentFactory.menuButton(textSetter(GameOptions.getInstance().getIsMuted()), new MuteButtonListener());
        JPanel mutePanel = lineMaker(muteLabel, muteButton);

        JLabel debugLabel = JComponentFactory.menuLabel("Debug Menu");
        debugButton = JComponentFactory.menuButton(textSetter(GameOptions.getInstance().getShowDebug()), new DebugMenuListener());
        JPanel debugPanel = lineMaker(debugLabel, debugButton);

        goBack = JComponentFactory.borderMenuPanel();
        goBack.add(goBackButton);

        optionDisplay = JComponentFactory.gridMenuPanel(4, 1, GameConfiguration.BUTTON_SEPARATOR, GameConfiguration.BUTTON_SEPARATOR, soundPanel, mutePanel, debugPanel, goBack);

        jPanelCenter = JComponentFactory.voidPanel();
        jPanelCenter.add(optionDisplay);

        this.add(jPanelCenter, BorderLayout.CENTER);
        this.add(titleDisplay, BorderLayout.NORTH);

        jPanelCenter.setBackground(Color.gray);
        optionDisplay.setBackground(Color.lightGray);

        soundPanel.setBackground(Color.lightGray);
        soundOptionPanel.setBackground(Color.lightGray);
        mutePanel.setBackground(Color.lightGray);
        debugPanel.setBackground(Color.lightGray);
        goBack.setBackground(Color.lightGray);
        titleDisplay.setBackground(Color.lightGray);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
    }

    private void sizeUpdate() {
        optionDisplay.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.5),(int) (getWindow().getHeight()*0.5)));
        optionDisplay.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.03),(int) (getWindow().getHeight()*0.015), (int) (getWindow().getHeight()*0.02),(int) (getWindow().getHeight()*0.015)));
        jPanelCenter.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.02),0, (int) (getWindow().getHeight()*0.01),0));

        goBack.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.02),(int) (getWindow().getHeight()*0.06), 0,(int) (getWindow().getHeight()*0.06)));

        getWindow().revalidate();
        getWindow().repaint();
    }

    //Listener

    private class ComponentControls implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            sizeUpdate();
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }

    private class MinusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            GameOptions.getInstance().setVolume(lbm.decrement(GameConfiguration.MIN_SOUND_LEVEL, GameOptions.getInstance().getVolume()));
            MusicManager.getInstance().actualizeMusicPlayers();
            soundLabel.setText("Sound Level ("+GameOptions.getInstance().getVolume()+"/"+GameConfiguration.MAX_SOUND_LEVEL+")");
            updateLinkedFile();
        }
    }

    private class PlusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            GameOptions.getInstance().setVolume(lbm.increment(GameConfiguration.MAX_SOUND_LEVEL, GameOptions.getInstance().getVolume()));
            MusicManager.getInstance().actualizeMusicPlayers();
            soundLabel.setText("Sound Level ("+GameOptions.getInstance().getVolume()+"/"+GameConfiguration.MAX_SOUND_LEVEL+")");
            updateLinkedFile();
        }
    }

    private class MuteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameOptions.getInstance().setIsMuted(ListenerBehaviorManager.create().toggle(muteButton, GameOptions.getInstance().getIsMuted()));
            MusicManager.getInstance().actualizeMusicPlayers();
            updateLinkedFile();
        }
    }

    private class DebugMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            GameOptions.getInstance().setShowDebug(lbm.toggle(debugButton, GameOptions.getInstance().getShowDebug()));
            updateLinkedFile();
        }
    }

    private class GoBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager.create().goBack(token,objectToken);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehaviorManager.create().goBack(token,objectToken);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
