package gui.panel;

import config.GameConfiguration;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;
import gui.process.ListenerBehavior;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * pause menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class PauseMenu extends SimpleMenu {

    private int token;

    private JPanel titleDisplay;
    private JPanel creditsDisplay;

    //Elements
    private JLabel title;
    private JLabel credits;
    private JPanel buttonsDisplay;
    private JPanel bigButtonDisplay;
    private JPanel totalButtonDisplay;

    //Regular Button
    private JButton saveButton;
    private JButton loadButton;
    private JButton optionsButton;
    private JButton mainMenuButton;

    //Big Button
    private JButton backToGameButton;
    private JButton exitButton;

    public PauseMenu(int token) {
        super();
        this.token = token;
        init();
    }

    public void init() {

        this.setLayout(new BorderLayout());

        title = JComponentBuilder.title("Maritime");

        credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        saveButton = JComponentBuilder.menuButton("Save game", new SaveMenuListener());

        loadButton = JComponentBuilder.menuButton("Load game", new LoadMenuListener());

        optionsButton = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        mainMenuButton = JComponentBuilder.menuButton("Main Menu", new MainMenuListener());

        backToGameButton = JComponentBuilder.menuButton("Back to game", new ResumeButtonListener());

        exitButton = JComponentBuilder.menuButton("Exit", new ExitListener());

        buttonsDisplay = JComponentBuilder.gridMenuPanel(2,2, GameConfiguration.BUTTON_SEPARATOR,GameConfiguration.BUTTON_SEPARATOR, saveButton, loadButton, optionsButton, mainMenuButton);
        bigButtonDisplay = JComponentBuilder.gridMenuPanel(2,1,GameConfiguration.BUTTON_SEPARATOR,GameConfiguration.BUTTON_SEPARATOR, backToGameButton, exitButton);
        totalButtonDisplay = JComponentBuilder.gridMenuPanel(3,1,10, 10, buttonsDisplay, bigButtonDisplay);

        titleDisplay = JComponentBuilder.flowMenuPanel(title);

        creditsDisplay = JComponentBuilder.flowMenuPanel(credits);

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
            GUILoader.loadChargeGameMenu(token+2);
        }
    }

    public class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadSaveGameMenu(token+2);
        }
    }

    /**
     * An ActionListener allowing to exit the game
     */
    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            listenerBehavior.exit(PauseMenu.this);
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(token+2);
        }
    }

    public class ResumeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            listenerBehavior.goBack(token);
        }
    }

    private class KeyControls implements KeyListener {
        @Override
        public void keyPressed(KeyEvent event) {
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            listenerBehavior.goBack(token);
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
