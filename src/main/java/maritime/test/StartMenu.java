package maritime.test;

import maritime.config.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple start menu for the game
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class StartMenu extends JFrame {

    private JLabel Title;
    private JLabel credits;
    private JLabel credits2;

    private JButton newGame;
    private JButton loadGame;
    private JButton exit;
    private JButton options;

    private JPanel TitleDisplay;
    private JPanel creditsDisplay;
    private JPanel buttonDisplay;

    public StartMenu() {
        super("Maritime");
        init();
    }

    private void init() {
        Container window = getContentPane();
        window.setLayout(new BorderLayout());

        //Setting up elements

        Title = new JLabel("Maritime");
        Title.setFont(GameConfiguration.FONT);

        credits = new JLabel("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");
        credits.setFont(GameConfiguration.CREDITS_FONT);

        credits2 = new JLabel("2025");
        credits2.setFont(GameConfiguration.CREDITS_FONT);

        newGame = new JButton("New Game");
        newGame.setFont(GameConfiguration.FONT);
        newGame.addActionListener(new StartGameListener());

        loadGame = new JButton("Load Game");
        loadGame.setFont(GameConfiguration.FONT);
        loadGame.addActionListener(new LoadGameListener());

        options = new JButton("Options");
        options.setFont(GameConfiguration.FONT);
        options.addActionListener( new OptionsMenuListener());

        exit = new JButton("Exit");
        exit.setFont(GameConfiguration.FONT);
        exit.addActionListener(new ExitListener());

        TitleDisplay = new JPanel();
        TitleDisplay.setLayout(new FlowLayout(FlowLayout.CENTER));
        TitleDisplay.add(Title);

        creditsDisplay = new JPanel();
        creditsDisplay.setLayout(new FlowLayout(FlowLayout.CENTER));
        creditsDisplay.add(credits);
        creditsDisplay.add(credits2);

        buttonDisplay = new JPanel();
        buttonDisplay.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonDisplay.add(newGame);
        buttonDisplay.add(loadGame);
        buttonDisplay.add(options);
        buttonDisplay.add(exit);

        //Window arrangement

        window.add(BorderLayout.NORTH, TitleDisplay);
        window.add(BorderLayout.CENTER, buttonDisplay);
        window.add(BorderLayout.SOUTH, creditsDisplay);

        setSize(GameConfiguration.WINDOW_SIZE);
        setVisible(true); setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TestMove gameMainGUI = new TestMove("game");
                Thread gameThread = new Thread(gameMainGUI);
                gameThread.start();
                dispose();
            } catch ( IllegalThreadStateException e1 ) {
                JOptionPane.showMessageDialog( StartMenu.this, "Game is already running!", "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    private class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Work in progress
        }
    }

    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.exit(0);
            } catch (SecurityException ex) {
                JOptionPane.showMessageDialog(StartMenu.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    private class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Work in progress
        }
    }

    public static void main(String[] args) {
        new StartMenu();
    }

}
