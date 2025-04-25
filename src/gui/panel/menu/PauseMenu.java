package gui.panel.menu;

import config.GameConfiguration;
import gui.MainGUI;
import gui.utilities.GUILoader;
import gui.process.JComponentFactory;
import gui.process.ListenerBehaviorManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static config.GameOptions.getInstance;
import static gui.MainGUI.getWindow;

/**
 * A pause menu for the game
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class PauseMenu extends JPanel {

    private final int token;
    private final Object objectToken;
    private JPanel totalButtonDisplay;
    private JPanel jPanelCenter;
    private JPanel bigButtonDisplay;

    /**
     * Generate the PauseMenu using a token and makes it appear
     * @param token former GUI identifier
     */
    public PauseMenu(int token, Object objectToken) {
        super();
        this.token = token;
        this.objectToken = objectToken;
        init();
    }

    /**
     * Makes all necessary operations to initialize the panel
     */
    private void init() {

        this.setLayout(new BorderLayout());

        //Elements
        JLabel title = JComponentFactory.title("Maritime");

        JLabel credits = JComponentFactory.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        //Regular Button
        JButton saveButton = JComponentFactory.menuButton("Save game", new SaveMenuListener());
        saveButton.setEnabled(token!=GameConfiguration.ROOT_COMBAT);

        JButton loadButton = JComponentFactory.menuButton("Load game", new LoadMenuListener());

        JButton optionsButton = JComponentFactory.menuButton("Options", new OptionsMenuListener());

        JButton mainMenuButton = JComponentFactory.menuButton("Main Menu", new MainMenuListener());

        //Big Button
        JButton backToGameButton = JComponentFactory.menuButton("Back to game", new ResumeButtonListener());

        JButton exitButton = JComponentFactory.menuButton("Exit", new ExitListener());

        JButton debugButton = JComponentFactory.menuButton("Debug Menu", new debugListener());

        debugButton.setVisible(getInstance().getShowDebug());

        JPanel buttonsDisplay = JComponentFactory.gridMenuPanel(2, 2, 20+GameConfiguration.BUTTON_SEPARATOR, 5+GameConfiguration.BUTTON_SEPARATOR, saveButton, loadButton, optionsButton, mainMenuButton);
        bigButtonDisplay = JComponentFactory.gridMenuPanel(3, 1, 5+GameConfiguration.BUTTON_SEPARATOR, 5+GameConfiguration.BUTTON_SEPARATOR, exitButton, backToGameButton, debugButton);

        JPanel tmpBigButtonDisplay = JComponentFactory.voidPanel();
        tmpBigButtonDisplay.add(bigButtonDisplay);

        totalButtonDisplay = JComponentFactory.gridMenuPanel(2, 1, 10, 20, buttonsDisplay, tmpBigButtonDisplay);

        JPanel titleDisplay = JComponentFactory.flowMenuPanel(title);

        JPanel creditsDisplay = JComponentFactory.flowMenuPanel(credits);

        jPanelCenter = JComponentFactory.voidPanel();
        jPanelCenter.add(totalButtonDisplay);

        this.addKeyListener(new KeyControls());
        this.add(titleDisplay, BorderLayout.NORTH);
        this.add(jPanelCenter, BorderLayout.CENTER);

        this.add(creditsDisplay, BorderLayout.SOUTH);

        jPanelCenter.setBackground(Color.gray);
        totalButtonDisplay.setBackground(Color.gray);
        buttonsDisplay.setBackground(Color.gray);
        tmpBigButtonDisplay.setBackground(Color.gray);
        bigButtonDisplay.setBackground(Color.gray);


        creditsDisplay.setBackground(Color.lightGray);
        titleDisplay.setBackground(Color.lightGray);

        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
    }

    private void sizeUpdate() {
        totalButtonDisplay.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.5),(int) (getWindow().getHeight()*0.7)));
        totalButtonDisplay.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.03),0, 0,0));

        bigButtonDisplay.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.4),(int) (getWindow().getHeight()*0.3)));
        getWindow().revalidate();
        getWindow().repaint();
    }

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

    private class MainMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadStartMenu();
        }
    }

    private class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadChargeGameMenu(token+4,objectToken);
        }
    }

    private class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadSaveGameMenu(token+4,objectToken);
        }
    }

    private class debugListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainGUI.getDebug().setVisible(!MainGUI.getDebug().isVisible());
        }
    }

    /**
     * An ActionListener allowing to exit the game
     */
    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager.create().exit(PauseMenu.this);
        }
    }

    private class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(token+4,objectToken);
        }
    }

    private class ResumeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager.create().goBack(token,objectToken);
        }
    }

    private class KeyControls implements KeyListener {
        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ListenerBehaviorManager.create().goBack(token,objectToken);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
