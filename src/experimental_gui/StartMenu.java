package experimental_gui;

import test.TestMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple start menu for the game, serves as the entrypoint of the program
 * @author Zue Jack-Arthur
 * @version 0.4
 */
public class StartMenu extends SimpleMenu {

    private JLabel title;
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
    public StartMenu(Container window) {
        super(window);
        init();
    }
    public void init() {

        this.setLayout(new BorderLayout());

        title = JComponentBuilder.title("Maritime");

        credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        newGame = JComponentBuilder.menuButton("New Game",new StartGameListener());

        loadGame = JComponentBuilder.menuButton("Load Game",new LoadGameListener());

        options = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        exit = JComponentBuilder.menuButton("Exit", new ExitListener());

        TitleDisplay = JComponentBuilder.flowMenuPanel(title);

        creditsDisplay = JComponentBuilder.flowMenuPanel(credits);

        buttonDisplay = JComponentBuilder.flowMenuPanel(newGame, loadGame, options, exit);

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
                JOptionPane.showMessageDialog( StartMenu.this, "Game is already running!", "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadOptionsMenu(0,getWindow());
        }
    }

    public class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Work in progress
        }
    }
}
