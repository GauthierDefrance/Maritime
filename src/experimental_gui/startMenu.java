package experimental_gui;

import config.GameConfiguration;
import test.TestMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple start menu for the game, serves as the entrypoint of the program
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class startMenu extends simpleMenu {

    private JLabel Title;
    private JLabel credits;

    private JButton newGame;
    private JButton loadGame;
    private JButton exit;
    private JButton options;

    private JPanel TitleDisplay;
    private JPanel creditsDisplay;
    private JPanel buttonDisplay;

    /**
     * Typical constructor to make the startMenu appear
     */
    public startMenu(Container window) {
        super(window);
        init();
    }
    public void init() {

        //Setting up elements

        Title = new JLabel("Maritime");
        Title.setFont(GameConfiguration.FONT);

        credits = new JLabel("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");
        credits.setFont(GameConfiguration.CREDITS_FONT);

        newGame = new JButton("New Game");
        newGame.setFont(GameConfiguration.FONT);
        newGame.addActionListener(new StartGameListener());

        loadGame = new JButton("Load Game");
        loadGame.setFont(GameConfiguration.FONT);
        loadGame.addActionListener(new LoadGameListener());

        options = new JButton("Options");
        options.setFont(GameConfiguration.FONT);
        options.addActionListener(new OptionsMenuListener());

        exit = new JButton("Exit");
        exit.setFont(GameConfiguration.FONT);
        exit.addActionListener(new ExitListener());

        TitleDisplay = new JPanel();
        TitleDisplay.setLayout(new FlowLayout(FlowLayout.CENTER));
        TitleDisplay.add(Title);

        creditsDisplay = new JPanel();
        creditsDisplay.setLayout(new FlowLayout(FlowLayout.CENTER));
        creditsDisplay.add(credits);

        buttonDisplay = new JPanel();
        buttonDisplay.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonDisplay.add(newGame);
        buttonDisplay.add(loadGame);
        buttonDisplay.add(options);
        buttonDisplay.add(exit);

        //Window arrangement

        this.add(TitleDisplay, BorderLayout.NORTH);
        this.add( buttonDisplay, BorderLayout.CENTER);
        this.add(creditsDisplay, BorderLayout.SOUTH);

    }

    /**
     * An ActionListener allowing to start the game (currently starts the TestMove demo)
     */
    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TestMove gameMainGUI = new TestMove("game");
                Thread gameThread = new Thread(gameMainGUI);
                gameThread.start();
            } catch ( IllegalThreadStateException e1 ) {
                JOptionPane.showMessageDialog( startMenu.this, "Game is already running!", "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(0,window);
        }
    }

    public class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Work in progress
        }
    }

}
