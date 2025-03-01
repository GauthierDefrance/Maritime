package experimental_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * pause menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class pauseMenu extends simpleMenu {

    private int token;

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

    public pauseMenu(int token, Container window) {
        super(window);
        this.token = token;
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

    public class ResumeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }
}
