package gui.panel;

import config.MapBuilder;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;

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
    private MapBuilder map;

    //Elements
    private JLabel Title;
    private JLabel credits;
    private JPanel buttonsDisplay;
    private JPanel bigButtonsDisplay;

    //Regular Button
    private JButton saveButton;
    private JButton loadButton;
    private JButton optionsButton;
    private JButton mainMenuButton;

    //Big Button
    private JButton backToGameButton;
    private JButton exitButton;

    public PauseMenu(int token, Container window, MapBuilder map) {
        super(window);
        this.token = token;
        this.map = map;
        init();
    }

    public void init() {
        Title = JComponentBuilder.title("Maritime");

        credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        saveButton = JComponentBuilder.menuButton("Save game");

        loadButton = JComponentBuilder.menuButton("Load game");

        optionsButton = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        mainMenuButton = JComponentBuilder.menuButton("Main Menu", new MainMenuListener());

        backToGameButton = JComponentBuilder.menuButton("Back to game", new ResumeButtonListener());

        exitButton = JComponentBuilder.menuButton("Exit", new ExitListener());

        buttonsDisplay = JComponentBuilder.flowMenuPanel(saveButton, loadButton, optionsButton, mainMenuButton);

        bigButtonsDisplay = JComponentBuilder.flowMenuPanel(backToGameButton, exitButton);

        this.addKeyListener(new KeyControls());
        this.add(Title);
        this.add(buttonsDisplay);
        this.add(bigButtonsDisplay);
        this.add(credits);
    }

    public class MainMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadStartMenu(getWindow());
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(token,getWindow(), map);
        }
    }

    public class ResumeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == 4) GUILoader.loadMainGameMenu(getWindow(), map);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if (token == 4) GUILoader.loadMainGameMenu(getWindow(), map);
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
