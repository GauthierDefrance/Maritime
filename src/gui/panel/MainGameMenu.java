package gui.panel;

import config.GameConfiguration;
import config.MapBuilder;
import engine.entity.Harbor;
import engine.entity.boats.*;
import engine.process.FactionManager;
import gui.MainGUI;
import gui.process.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author Kenan Ammad
 * @version 0.2
 */
public class MainGameMenu extends JPanel implements Runnable {

    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel jSouthATHPanel;
    private JPanel jEastATHPanel;
    private JPanel jEastCenterChoice1CenterPanel;
    private JPanel jEastCenterChoice2CenterPanel;

    private JButton showLeftMenuButton;
    private JButton hideLeftMenuButton;

    private GameDisplay dashboard;
    private MapBuilder map;
    private FactionManager factionManager;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public MainGameMenu(MapBuilder map) {
        super();
        this.map = map;
        init();
    }
    public void init() {
        this.setLayout(new BorderLayout());
        jPanelATH = JComponentBuilder.borderMenuPanel();
        jEastATHPanel = JComponentBuilder.borderMenuPanel();
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        jSouthATHPanel = JComponentBuilder.borderMenuPanel();
        jNorthATHPanel = JComponentBuilder.borderMenuPanel();
        jEastCenterChoice1CenterPanel = JComponentBuilder.gridMenuPanel(0,2);
        jEastCenterChoice2CenterPanel = JComponentBuilder.gridMenuPanel(0,2);

        dashboard = new GameDisplay(map);
        factionManager = new FactionManager(map);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        JPanel jEastPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastWestPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastButtonPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterCenterPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterPanelChoice1 = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterPanelChoice2 = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterPanelChoice3 = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterPanelChoice4 = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterNorthPanel = JComponentBuilder.gridMenuPanel(1,4,0,0);


        JScrollPane jScrollPane1 = new JScrollPane(jEastCenterChoice1CenterPanel);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice1.add(jScrollPane1);

        JScrollPane jScrollPane2 = new JScrollPane(jEastCenterChoice2CenterPanel);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice2.add(jScrollPane2);

        JButton jButtonLeftMenu1 = JComponentBuilder.menuButton("1",new showMenu(jEastCenterPanelChoice1,jEastCenterCenterPanel));
        JButton jButtonLeftMenu2 = JComponentBuilder.menuButton("2",new showMenu(jEastCenterPanelChoice2,jEastCenterCenterPanel));
        JButton jButtonLeftMenu3 = JComponentBuilder.menuButton("3",new showMenu(jEastCenterPanelChoice3,jEastCenterCenterPanel));
        JButton jButtonLeftMenu4 = JComponentBuilder.menuButton("4",new showMenu(jEastCenterPanelChoice4,jEastCenterCenterPanel));

        jEastCenterNorthPanel.add(jButtonLeftMenu1);
        jEastCenterNorthPanel.add(jButtonLeftMenu2);
        jEastCenterNorthPanel.add(jButtonLeftMenu3);
        jEastCenterNorthPanel.add(jButtonLeftMenu4);

        showLeftMenuButton = JComponentBuilder.menuButton("<", new showMenu(jEastPanel,jEastATHPanel));
        jEastButtonPanel.add(showLeftMenuButton,BorderLayout.NORTH);
        hideLeftMenuButton = JComponentBuilder.menuButton(">", new showMenu(jEastButtonPanel,jEastATHPanel));

        jEastATHPanel.setOpaque(false);
        jEastButtonPanel.setOpaque(false);
        jEastWestPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jEastPanel.setOpaque(false);

        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastCenterPanel.add(jEastCenterCenterPanel,BorderLayout.CENTER);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        jEastPanel.add(jEastCenterPanel,BorderLayout.CENTER);
        jEastPanel.add(jEastWestPanel,BorderLayout.WEST);
        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastCenterPanel.add(jEastCenterCenterPanel,BorderLayout.CENTER);
        jEastCenterCenterPanel.add(jEastCenterPanelChoice1,BorderLayout.CENTER);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);
        jEastATHPanel.add(jEastPanel,BorderLayout.CENTER);
        jPanelATH.add(jNorthATHPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);
        jPanelATH.add(jEastATHPanel,BorderLayout.EAST);
        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        jEastCenterPanel.setBackground(Color.DARK_GRAY);
        jNorthATHPanel.setBackground(Color.red);
        jSouthATHPanel.setBackground(Color.black);
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);
        jEastCenterChoice1CenterPanel.setBackground(Color.GRAY);
        jEastCenterChoice2CenterPanel.setBackground(Color.GRAY);

        this.add(jLayeredPane);

        sizeUpdate();
        elementInPanelUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jNorthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.05)));
        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jEastATHPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));

        if (jEastATHPanel.isAncestorOf(showLeftMenuButton))jEastATHPanel.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));

        showLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        hideLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        jEastCenterChoice1CenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08*map.getPlayer().getLstBoat().size()))));
        jEastCenterChoice2CenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08*map.getPlayer().getLstHarbor().size()))));
        getWindow().revalidate();
        getWindow().repaint();
    }

    public void elementInPanelUpdate() {
        jEastCenterChoice1CenterPanel.removeAll();
        jEastCenterChoice2CenterPanel.removeAll();
        for (Boat boat : map.getPlayer().getLstBoat()){
            jEastCenterChoice1CenterPanel.add(JComponentBuilder.menuButton(boat));
        }
        for (Harbor harbor : map.getPlayer().getLstHarbor()){
            jEastCenterChoice2CenterPanel.add(JComponentBuilder.menuButton(harbor));
        }
    }

    public class showMenu implements ActionListener {
        private JPanel jPanel1;
        private JPanel jPanel2;

        public showMenu(JPanel jPanel1, JPanel jPanel2){
            this.jPanel1 = jPanel1;
            this.jPanel2 = jPanel2;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            jPanel2.removeAll();
            jPanel2.add(jPanel1);
            sizeUpdate();
        }
    }

    private class ComponentControls implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            sizeUpdate();
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
                MainGUI.setMap(map);
                ThreadStop = true;
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_MAIN_GAME);
            }
            else if(event.getKeyCode() == KeyEvent.VK_SPACE){
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
        while (!ThreadStop) {
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
