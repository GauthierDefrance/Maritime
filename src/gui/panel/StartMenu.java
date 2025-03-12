package gui.panel;

import config.GameConfiguration;
import config.MapBuilder;
import engine.process.FactionManager;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;
import gui.process.ListenerBehaviorManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getMap;
import static gui.MainGUI.getWindow;

/**
 * Simple start menu for the game, serves as the entrypoint of the program
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @version 0.5
 */
public class StartMenu extends SimpleMenu implements Runnable {

    private JLabel title;
    private JLabel credits;

    private JButton newGame;
    private JButton loadGame;
    private JButton exit;
    private JButton options;

    private JPanel titleDisplay;
    private JPanel creditsDisplay;
    private JPanel buttonDisplay;
    private JPanel jPanel0 = new JPanel();
    private JPanel jPanel1 = new JPanel();

    private GameDisplay dashboard;
    private MapBuilder map = new MapBuilder(0);
    private FactionManager factionManager = new FactionManager(map);
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public StartMenu() {
        super();
        init();
    }
    public void init() {

        this.setLayout(new BorderLayout());
        dashboard = new GameDisplay(map);
        jPanel1.setLayout(new BorderLayout());
        jPanel0.setLayout(new BorderLayout());

        title = JComponentBuilder.title("Maritime");

        credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        newGame = JComponentBuilder.menuButton("New Game",new StartGameListener());

        loadGame = JComponentBuilder.menuButton("Load Game",new LoadMenuListener());

        options = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        exit = JComponentBuilder.menuButton("Exit", new ExitListener());

        titleDisplay = JComponentBuilder.flowMenuPanel(title);

        creditsDisplay = JComponentBuilder.flowMenuPanel(credits);

        buttonDisplay = JComponentBuilder.flowMenuPanel(newGame, loadGame, options, exit);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        jPanel0.setBounds(getWindow().getBounds());
        jPanel1.setBounds(getWindow().getBounds());

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();

        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);

        jPanel1.setOpaque(false);
        titleDisplay.setOpaque(false);
        creditsDisplay.setOpaque(true);
        buttonDisplay.setOpaque(false);

        jPanel0.add(dashboard,BorderLayout.CENTER);
        jPanel1.add(titleDisplay, BorderLayout.NORTH);
        jPanel1.add( buttonDisplay, BorderLayout.CENTER);
        jPanel1.add(creditsDisplay, BorderLayout.SOUTH);

        jLayeredPane.add(jPanel0,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanel1,JLayeredPane.PALETTE_LAYER);

        this.add(jLayeredPane);


        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * An ActionListener allowing to start the game (currently starts the TestMove demo)
     */
    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadMainGame(getMap());
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadOptionsMenu(GameConfiguration.ROOT_START_MENU);
        }
    }

    public class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadChargeGameMenu(GameConfiguration.ROOT_START_MENU);
        }
    }

    /**
     * An ActionListener allowing to exit the game
     */
    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager lbm = ListenerBehaviorManager.create();
            lbm.exit(StartMenu.this);
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
        public void componentMoved(ComponentEvent e) { }

        @Override
        public void componentShown(ComponentEvent e) { }

        @Override
        public void componentHidden(ComponentEvent e) { }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(JOptionPane.showConfirmDialog(StartMenu.this,"Do you want to quit ?","confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                    ListenerBehaviorManager ls = ListenerBehaviorManager.create();
                    ls.exit(StartMenu.this);
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }

    @Override
    public void run() {
        while (!ThreadStop) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            factionManager.nextRound();
            dashboard.repaint();
        }
    }
}
