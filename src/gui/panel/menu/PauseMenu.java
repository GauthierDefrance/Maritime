package gui.panel.menu;

import config.GameConfiguration;
import gui.MainGUI;
import gui.utilities.GUILoader;
import gui.process.JComponentBuilder;
import gui.process.ListenerBehaviorManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static config.GameOptions.getInstance;

/**
 * A pause menu for the game
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class PauseMenu extends JPanel {

    private final int token;
    private final Object objectToken;

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
    public void init() {

        this.setLayout(new BorderLayout());

        //Elements
        JLabel title = JComponentBuilder.title("Maritime");

        JLabel credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        //Regular Button
        JButton saveButton = JComponentBuilder.menuButton("Save game", new SaveMenuListener());
        saveButton.setEnabled(token!=GameConfiguration.ROOT_COMBAT);

        JButton loadButton = JComponentBuilder.menuButton("Load game", new LoadMenuListener());

        JButton optionsButton = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        JButton mainMenuButton = JComponentBuilder.menuButton("Main Menu", new MainMenuListener());

        //Big Button
        JButton backToGameButton = JComponentBuilder.menuButton("Back to game", new ResumeButtonListener());

        JButton exitButton = JComponentBuilder.menuButton("Exit", new ExitListener());

        JButton debugButton = JComponentBuilder.menuButton("Debug Menu", new debugListener());

        debugButton.setVisible(getInstance().getShowDebug());

        JPanel buttonsDisplay = JComponentBuilder.gridMenuPanel(2, 2, GameConfiguration.BUTTON_SEPARATOR, GameConfiguration.BUTTON_SEPARATOR, saveButton, loadButton, optionsButton, mainMenuButton);
        JPanel bigButtonDisplay = JComponentBuilder.gridMenuPanel(3, 1, GameConfiguration.BUTTON_SEPARATOR, GameConfiguration.BUTTON_SEPARATOR, backToGameButton, exitButton, debugButton);
        JPanel totalButtonDisplay = JComponentBuilder.gridMenuPanel(2, 1, 10, 10, buttonsDisplay, bigButtonDisplay);

        JPanel titleDisplay = JComponentBuilder.flowMenuPanel(title);

        JPanel creditsDisplay = JComponentBuilder.flowMenuPanel(credits);

        this.addKeyListener(new KeyControls());
        this.add(titleDisplay, BorderLayout.NORTH);
        this.add(totalButtonDisplay, BorderLayout.CENTER);

        this.add(JComponentBuilder.voidPanel(), BorderLayout.EAST);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.WEST);
        this.add(creditsDisplay, BorderLayout.SOUTH);
    }

    public class MainMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadStartMenu();
        }
    }

    public class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadChargeGameMenu(token+3,objectToken);
        }
    }

    public class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadSaveGameMenu(token+3,objectToken);
        }
    }

    public class debugListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainGUI.getDebug().setVisible(!MainGUI.getDebug().isVisible());
        }
    }

    /**
     * An ActionListener allowing to exit the game
     */
    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager.create().exit(PauseMenu.this);
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(token+3,objectToken);
        }
    }

    public class ResumeButtonListener implements ActionListener {
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
