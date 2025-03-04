package experimental_gui;

import test.TestMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

        this.addKeyListener(new KeyControls());

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

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(JOptionPane.showConfirmDialog(StartMenu.this,"Do you want to quit ?","confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                    try {
                        System.exit(0);
                    } catch ( SecurityException e1 ) {
                        JOptionPane.showMessageDialog(StartMenu.this, "You are not allowed to exit!", "Error", JOptionPane.ERROR_MESSAGE );
                    }
                }
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
