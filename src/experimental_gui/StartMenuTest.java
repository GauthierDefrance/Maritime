package experimental_gui;

import config.GameConfiguration;
import config.MapBuilder;
import engine.process.FactionManager;
import gui.GameDisplay;
import test.TestMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @version 0.0
 */
public class StartMenuTest extends SimpleMenu implements Runnable {

    private JLabel title;
    private JLabel credits;

    private JButton newGame;
    private JButton loadGame;
    private JButton exit;
    private JButton options;

    private JPanel TitleDisplay;
    private JPanel creditsDisplay;
    private JPanel buttonDisplay;
    private JPanel jPanel0 = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private GameDisplay dashboard;
    private MapBuilder map = new MapBuilder(0);
    private FactionManager factionManager = new FactionManager(map);

    /**
     * Typical constructor to make the startMenu appear
     */
    public StartMenuTest(Container window) {
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
        this.getWindow().addComponentListener(new ComponentControls());
        jPanel0.setBounds(getWindow().getBounds());
        jPanel1.setBounds(getWindow().getBounds());

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        jPanel1.setLayout(new BorderLayout());
        jPanel0.setLayout(new BorderLayout());

        jPanel1.setOpaque(false);
        buttonDisplay.setOpaque(false);
        TitleDisplay.setOpaque(false);
        creditsDisplay.setOpaque(false);

        jPanel0.setBackground(Color.red);

        dashboard = new GameDisplay(map);
        dashboard.setBackground(new Color(78, 172, 233));
        jPanel0.add(dashboard,BorderLayout.CENTER);
        jPanel1.add(TitleDisplay, BorderLayout.NORTH);
        jPanel1.add( buttonDisplay, BorderLayout.CENTER);
        jPanel1.add(creditsDisplay, BorderLayout.SOUTH);

        jLayeredPane.add(jPanel0,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanel1,JLayeredPane.PALETTE_LAYER);

        this.add(jLayeredPane);

        Thread gameThread = new Thread(this);
        gameThread.start();
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
                JOptionPane.showMessageDialog( StartMenuTest.this, "Game is already running!", "Error", JOptionPane.ERROR_MESSAGE );
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

    private class ComponentControls implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            jPanel0.setBounds(getWindow().getBounds());
            jPanel1.setBounds(getWindow().getBounds());
            getWindow().revalidate();
            getWindow().repaint();

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

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(JOptionPane.showConfirmDialog(StartMenuTest.this,"Vous voulez quitter ?","confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                    try {
                        System.exit(0);
                    } catch ( SecurityException e1 ) {
                        JOptionPane.showMessageDialog(StartMenuTest.this, "You are not allowed to exit!", "Error", JOptionPane.ERROR_MESSAGE );
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

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!map.isTimeStop()){
                factionManager.nextRound();
            }
            dashboard.repaint();
        }
    }
}
