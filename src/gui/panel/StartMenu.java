package gui.panel;

import config.GameConfiguration;
import engine.MapGame;
import engine.process.builder.EngineBuilder;
import engine.process.FactionManager;
import gui.PopUp;
import gui.panel.Display.GameDisplay;
import gui.utilities.GUILoader;
import gui.process.JComponentBuilder;
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
        jPanelATH = JComponentBuilder.borderMenuPanel();
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        jNorthATHPanel = JComponentBuilder.borderMenuPanel();
        NorthVoidpanel = JComponentBuilder.voidPanel();
        title = JComponentBuilder.title(" Maritime ");
        dashboard = new GameDisplay();

        title.setOpaque(true);
        title.setForeground(Color.black);
        title.setBackground(Color.lightGray);

        JLabel credits = JComponentBuilder.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");

        JButton newGame = JComponentBuilder.menuButton("New Game", new StartGameListener());

        JButton loadGame = JComponentBuilder.menuButton("Load Game", new LoadMenuListener());

        JButton options = JComponentBuilder.menuButton("Options", new OptionsMenuListener());

        JButton exit = JComponentBuilder.menuButton("Exit", new ExitListener());

        JPanel titleDisplay = JComponentBuilder.flowMenuPanel(title);

        JPanel creditsDisplay = JComponentBuilder.flowMenuPanel(credits);

        buttonDisplay = JComponentBuilder.gridMenuPanel(1,0,10,0,newGame, loadGame, options, exit);
        JPanel tmpButtonDisplay = JComponentBuilder.flowMenuPanel(buttonDisplay);

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
            factionManager.nextRound();
            dashboard.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
