package gui.panel.menu;

import config.GameConfiguration;
import engine.MapGame;
import engine.process.creational.EngineBuilder;
import engine.process.manager.FactionManager;
import gui.PopUp;
import gui.panel.display.GameDisplay;
import gui.utilities.GUILoader;
import gui.process.JComponentFactory;
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

    private JPanel dashboardJPanel;

    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel NorthVoidpanel;
    private JPanel buttonDisplay;
    private JLabel title;

    private GameDisplay dashboard;
    private final FactionManager factionManager = FactionManager.getInstance();
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
        jPanelATH = JComponentFactory.borderMenuPanel();
        dashboardJPanel = JComponentFactory.borderMenuPanel();
        jNorthATHPanel = JComponentFactory.borderMenuPanel();
        NorthVoidpanel = JComponentFactory.voidPanel();
        title = JComponentFactory.title(" Maritime ");
        dashboard = new GameDisplay();

        title.setOpaque(true);
        title.setForeground(Color.black);
        title.setBackground(Color.lightGray);

        JLabel credits = JComponentFactory.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        JButton newGame = JComponentFactory.menuButton("New Game", new StartGameListener());

        JButton loadGame = JComponentFactory.menuButton("Load Game", new LoadMenuListener());

        JButton options = JComponentFactory.menuButton("Options", new OptionsMenuListener());

        JButton exit = JComponentFactory.menuButton("Exit", new ExitListener());

        JPanel titleDisplay = JComponentFactory.flowMenuPanel(title);

        JPanel creditsDisplay = JComponentFactory.flowMenuPanel(credits);

        buttonDisplay = JComponentFactory.gridMenuPanel(1,0,10,0,newGame, loadGame, options, exit);
        JPanel tmpButtonDisplay = JComponentFactory.flowMenuPanel(buttonDisplay);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();

        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);

        jPanelATH.setOpaque(false);
        jNorthATHPanel.setOpaque(false);
        tmpButtonDisplay.setOpaque(false);
        titleDisplay.setOpaque(false);
        creditsDisplay.setOpaque(true);
        buttonDisplay.setOpaque(false);
        jNorthATHPanel.add(NorthVoidpanel,BorderLayout.NORTH);
        jNorthATHPanel.add(titleDisplay,BorderLayout.CENTER);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        jPanelATH.add(jNorthATHPanel, BorderLayout.NORTH);
        jPanelATH.add(tmpButtonDisplay, BorderLayout.CENTER);
        jPanelATH.add(creditsDisplay, BorderLayout.SOUTH);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        this.add(jLayeredPane);
        sizeUpdate();

        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }
    private void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());
        NorthVoidpanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.025)));
        jNorthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) Math.max(75,(getWindow().getHeight()*0.10))));
        buttonDisplay.setPreferredSize(new Dimension((int) Math.max(600,getWindow().getWidth()*0.6),(int) (getWindow().getHeight()*0.08)));
        title.setFont(new Font( "Noto Sans Display", Font.BOLD, Math.max(30,(int) (getWindow().getHeight()*0.05))));


        getWindow().revalidate();
        getWindow().repaint();
    }

    /**
     * An ActionListener allowing to start the game (currently starts the TestMove demo)
     */
    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            TestMove.addBoatTest();
            GUILoader.loadMainGame();
        }
    }

    public class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadOptionsMenu(GameConfiguration.ROOT_START_MENU,null);
        }
    }

    public class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadChargeGameMenu(GameConfiguration.ROOT_START_MENU,null);
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
            sizeUpdate();
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
            dashboard.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
