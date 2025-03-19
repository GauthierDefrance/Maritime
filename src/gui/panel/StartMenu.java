package gui.panel;

import config.GameConfiguration;
import engine.MapGame;
import engine.utilities.EngineBuilder;
import engine.process.FactionManager;
import gui.PopUp;
import gui.panel.Display.GameDisplay;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;
import gui.process.ListenerBehaviorManager;
import test.TestMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

/**
 * Simple start menu for the game, serves as the visual entrypoint of the program
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @version 0.5
 */
public class StartMenu extends JPanel implements Runnable {

    private final JPanel jPanel0 = new JPanel();

    private final JPanel jPanel1 = new JPanel();

    private GameDisplay dashboard;
    private final FactionManager factionManager = new FactionManager();
    private boolean ThreadStop;

    /**
     * Typical constructor to make the StartMenu appear
     */
    public StartMenu() {
        super();
        init();
    }

    /**
     * Makes all necessary operations to initialize the panel
     */
    public void init() {
        EngineBuilder.mapInit(0);
        this.setLayout(new BorderLayout());
        dashboard = new GameDisplay();
        jPanel1.setLayout(new BorderLayout());
        jPanel0.setLayout(new BorderLayout());

        JLabel title = JComponentBuilder.title("Maritime");

        JLabel credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        JButton newGame = JComponentBuilder.menuButton("New Game", new StartGameListener());

        JButton loadGame = JComponentBuilder.menuButton("Load Game", new LoadMenuListener());

        JButton options = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        JButton exit = JComponentBuilder.menuButton("Exit", new ExitListener());

        JPanel titleDisplay = JComponentBuilder.flowMenuPanel(title);

        JPanel creditsDisplay = JComponentBuilder.flowMenuPanel(credits);

        JPanel buttonDisplay = JComponentBuilder.flowMenuPanel(newGame, loadGame, options, exit);

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
        jPanel1.add(buttonDisplay, BorderLayout.CENTER);
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
            TestMove.addBaotTest();
            GUILoader.loadMainGame();
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
            ListenerBehaviorManager.create().exit(StartMenu.this);
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
                    ListenerBehaviorManager.create().exit(StartMenu.this);
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
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
