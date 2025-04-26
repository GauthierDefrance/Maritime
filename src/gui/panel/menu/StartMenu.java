package gui.panel.menu;

import config.GameConfiguration;
import engine.MapGame;
import engine.process.creational.EngineBuilder;
import gui.PopUp;
import gui.panel.display.GameDisplay;
import gui.utilities.GUILoader;
import gui.process.JComponentFactory;
import gui.process.ListenerBehaviorManager;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import test.TestMove;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private static Logger logger = LoggerUtility.getLogger(StartMenu.class);
    private JLayeredPane jLayeredPane;
    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel NorthVoidpanel;
    private JPanel buttonDisplay;
    private JPanel tmpButtonDisplay;
    private JPanel titleDisplay;
    private JLabel title;

    private GameDisplay dashboard;
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
    private void init() {
        EngineBuilder.mapInit(0);
        this.setLayout(new BorderLayout());
        jLayeredPane = JComponentFactory.JLayeredPane();
        jPanelATH = JComponentFactory.borderMenuPanel();
        dashboardJPanel = JComponentFactory.borderMenuPanel();
        NorthVoidpanel = JComponentFactory.voidPanel();
        title = JComponentFactory.title(" Maritime ");
        titleDisplay = JComponentFactory.flowMenuPanel(title);
        dashboard = new GameDisplay();

        JLabel credits = JComponentFactory.credits("A Game by Ammad Kenan, Defrance Gauthier & Zue Jack-Arthur");
        JButton newGame = JComponentFactory.menuButton("New Game", new StartGameListener());
        JButton loadGame = JComponentFactory.menuButton("Load Game", new LoadMenuListener());
        JButton options = JComponentFactory.menuButton("Options", new OptionsMenuListener());
        JButton exit = JComponentFactory.menuButton("Exit", new ExitListener());

        JPanel creditsDisplay = JComponentFactory.flowMenuPanel(credits);

        buttonDisplay = JComponentFactory.gridMenuPanel(1,0,10,0,newGame, loadGame, options, exit);
        tmpButtonDisplay = JComponentFactory.flowMenuPanel(buttonDisplay);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        //Window arrangement
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);

        jPanelATH.setOpaque(false);
        tmpButtonDisplay.setOpaque(false);
        creditsDisplay.setOpaque(true);
        buttonDisplay.setOpaque(false);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        jPanelATH.add(titleDisplay, BorderLayout.NORTH);
        jPanelATH.add(tmpButtonDisplay, BorderLayout.CENTER);
        jPanelATH.add(creditsDisplay, BorderLayout.SOUTH);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        this.add(jLayeredPane);

        title.setOpaque(true);
        title.setBackground(new Color(30, 30, 30,200));
        titleDisplay.setBackground(new Color(64, 64, 64,100));
        creditsDisplay.setBackground(new Color(64, 64, 64,100));
        credits.setForeground(Color.lightGray);
        title.setForeground(Color.lightGray);

        sizeUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());
        tmpButtonDisplay.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.025),0,0,0));
        NorthVoidpanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.025)));
        buttonDisplay.setPreferredSize(new Dimension((int) Math.max(600,getWindow().getWidth()*0.6),(int) (getWindow().getHeight()*0.08)));
        titleDisplay.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.005),0,(int) (getWindow().getHeight()*0.005),0));
        title.setFont(new Font( "Noto Sans Display", Font.BOLD, Math.max(30,(int) (getWindow().getHeight()*0.05))));

        jLayeredPane.revalidate();
        jLayeredPane.repaint();
    }

    /**
     * An ActionListener allowing to start the game (currently starts the TestMove demo)
     */
    private class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            EngineBuilder.newGame();
            GUILoader.loadMainGame();
        }
    }

    private class OptionsMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadOptionsMenu(GameConfiguration.ROOT_START_MENU,null);
        }
    }

    private class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadChargeGameMenu(GameConfiguration.ROOT_START_MENU,null);
        }
    }

    /**
     * An ActionListener allowing to exit the game
     */
    private class ExitListener implements ActionListener {
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
                logger.error(e.getMessage());
            }
            jLayeredPane.revalidate();
            jLayeredPane.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
