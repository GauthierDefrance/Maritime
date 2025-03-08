package gui.panel;

import config.GameConfiguration;
import config.MapBuilder;
import engine.process.FactionManager;
import gui.process.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author @Kenan Ammad
 * @version 0.1
 */
public class MainGameMenu extends SimpleMenu implements Runnable {

    private JPanel dashboardJPanel = new JPanel();
    private JPanel jPanelATH = new JPanel();
    private JPanel jNorthPanel = new JPanel();
    private JPanel jSouthPanel = new JPanel();
    private JPanel jEastPanel = new JPanel();

    private JButton showLeftMenuButton;
    private JButton hideLeftMenuButton;

    private GameDisplay dashboard;
    private MapBuilder map = new MapBuilder(0);
    private FactionManager factionManager = new FactionManager(map);

    /**
     * Typical constructor to make the startMenu appear
     */
    public MainGameMenu(Container window,MapBuilder map) {
        super(window);
        this.map = map;
        init();
    }
    public void init() {
        this.setLayout(new BorderLayout());
        jPanelATH = JComponentBuilder.borderMenuPanel();
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        dashboard = new GameDisplay(map);
        hideLeftMenuButton = JComponentBuilder.menuButton(">", new hideLeftMenu());
        showLeftMenuButton = JComponentBuilder.menuButton("<", new showLeftMenu());

        this.addKeyListener(new KeyControls());
        this.getWindow().addComponentListener(new ComponentControls());

        jNorthPanel.setBackground(Color.red);
        jSouthPanel.setBackground(Color.black);
        jEastPanel.setBackground(Color.DARK_GRAY);
        dashboard.setBackground(new Color(78, 172, 233));

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        JPanel jEastWestPanel;
        JPanel jEastCenterPanel;
        JPanel jEastCenterNorthPanel;

        jEastPanel = JComponentBuilder.borderMenuPanel();
        jEastCenterPanel = JComponentBuilder.borderMenuPanel();
        jEastWestPanel = JComponentBuilder.borderMenuPanel();
        jEastCenterNorthPanel = JComponentBuilder.gridMenuPanel(1,4,0,0);

        jEastCenterPanel.setBackground(Color.DARK_GRAY);

        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);
        jEastCenterNorthPanel.add(new JButton("0"));
        jEastCenterNorthPanel.add(new JButton("1"));
        jEastCenterNorthPanel.add(new JButton("2"));
        jEastCenterNorthPanel.add(new JButton("3"));

        jPanelATH.setOpaque(false);
        jEastWestPanel.setOpaque(false);
        jEastPanel.setOpaque(false);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);

        jEastPanel.add(jEastCenterPanel,BorderLayout.CENTER);
        jEastPanel.add(jEastWestPanel,BorderLayout.WEST);

        jPanelATH.add(jNorthPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthPanel,BorderLayout.SOUTH);
        jPanelATH.add(jEastPanel,BorderLayout.EAST);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        this.add(jLayeredPane);

        Thread gameThread = new Thread(this);
        gameThread.start();
        sizeUpdate();
    }

    public void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jNorthPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.05)));
        jSouthPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jEastPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));
        showLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        hideLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));

        getWindow().revalidate();
        getWindow().repaint();
    }

    public class showLeftMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            jPanelATH.removeAll();
            jPanelATH.add(jNorthPanel,BorderLayout.NORTH);
            jPanelATH.add(jSouthPanel,BorderLayout.SOUTH);
            jPanelATH.add(jEastPanel,BorderLayout.EAST);
            getWindow().revalidate();
            getWindow().repaint();
        }
    }

    public class hideLeftMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel jEastButtonPanel = JComponentBuilder.borderMenuPanel();
            jEastButtonPanel.setOpaque(false);
            jEastButtonPanel.add(showLeftMenuButton,BorderLayout.NORTH);
            jPanelATH.remove(jEastPanel);
            jPanelATH.add(jEastButtonPanel,BorderLayout.EAST);
            getWindow().revalidate();
            getWindow().repaint();
        }
    }

    private class ComponentControls implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            sizeUpdate();
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
                GUILoader.loadPauseMenu(4,getWindow(), map);
            }
            if(event.getKeyCode() == KeyEvent.VK_SPACE){
                map.setTimeStop(!map.isTimeStop());
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
