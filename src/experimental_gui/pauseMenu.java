package experimental_gui;

import config.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * pause menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 */
public class pauseMenu extends simpleMenu {

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

    public pauseMenu(Container window) {
        super(window);
        init();
    }

    public void init() {
        Title = new JLabel("Maritime");
        Title.setFont(GameConfiguration.FONT);

        credits = new JLabel("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");
        credits.setFont(GameConfiguration.CREDITS_FONT);

        saveButton = new JButton("Save game");
        saveButton.setFont(GameConfiguration.FONT);

        loadButton = new JButton("Load game");
        loadButton.setFont(GameConfiguration.FONT);

        optionsButton = new JButton("Options");
        optionsButton.setFont(GameConfiguration.FONT);
        optionsButton.addActionListener(new OptionsMenuListener());

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(GameConfiguration.FONT);
        mainMenuButton.addActionListener(new MainMenuListener());

        backToGameButton = new JButton("Back to Game");
        backToGameButton.setFont(GameConfiguration.FONT);

        exitButton = new JButton("Exit");
        exitButton.setFont(GameConfiguration.FONT);
        exitButton.addActionListener(new ExitListener());

        buttonsDisplay = new JPanel();
        buttonsDisplay.add(saveButton);
        buttonsDisplay.add(loadButton);
        buttonsDisplay.add(optionsButton);
        buttonsDisplay.add(mainMenuButton);

        bigButtonsDisplay = new JPanel();
        bigButtonsDisplay.add(backToGameButton);
        bigButtonsDisplay.add(exitButton);

        this.add(Title);
        this.add(buttonsDisplay);
        this.add(bigButtonsDisplay);
        this.add(credits);

    }

    public class MainMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadStartMenu(window);
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(1,window);
        }
    }
}
