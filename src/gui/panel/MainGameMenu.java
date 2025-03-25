package gui.panel;

import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Entity;
import engine.entity.Harbor;
import engine.entity.boats.*;
import engine.process.FactionManager;
import engine.trading.SeaRoad;
import gui.PopUp;
import gui.panel.Display.GameDisplay;
import gui.process.ListenerBehaviorManager;
import gui.utilities.GUILoader;
import gui.process.JComponentBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import static gui.MainGUI.getWindow;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author Kenan Ammad
 * @version 0.3
 */
public class MainGameMenu extends JPanel implements Runnable {

    private int speedBoost;
    private HashMap<Entity, JButton> mapEntity;
    private JButton currentJButton;

    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel jNorthEastPanel;

    private JPanel jSouthATHPanel;
    private JPanel jSouthEastPanel;
    private JPanel jEastATHPanel;
    private JPanel jEastCenterChoice1CenterPanel;
    private JPanel jEastCenterChoice2CenterPanel;
    private JPanel jEastCenterChoice3CenterPanel1;
    private JPanel jEastCenterChoice3CenterPanel2;
    private JPanel jEastPanel;

    private JPanel jEastCenterCenterPanel;
    private JPanel jEastCenterPanelChoice1;
    private JPanel jEastCenterPanelChoice2;
    private JPanel jEastCenterPanelChoice3;
    private JPanel jEastCenterPanelChoice4;


    private JButton showLeftMenuButton;
    private JButton hideLeftMenuButton;

    private JButton jButtonLeftMenu1;
    private JButton jButtonLeftMenu2;
    private JButton jButtonLeftMenu3;
    private JButton jButtonLeftMenu4;

    private GameDisplay dashboard;
    private FactionManager factionManager;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public MainGameMenu() {
        super();
        init();
    }

    public void init() {
        this.setLayout(new BorderLayout());
        jPanelATH = JComponentBuilder.borderMenuPanel();
        jEastATHPanel = JComponentBuilder.borderMenuPanel();
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        jSouthATHPanel = JComponentBuilder.borderMenuPanel();
        jSouthEastPanel = JComponentBuilder.borderMenuPanel();
        jNorthATHPanel = JComponentBuilder.borderMenuPanel();
        jNorthEastPanel = JComponentBuilder.gridMenuPanel(1,4,0,0);
        jEastPanel = JComponentBuilder.borderMenuPanel();
        jEastCenterChoice1CenterPanel = JComponentBuilder.gridMenuPanel(0,2);
        jEastCenterChoice2CenterPanel = JComponentBuilder.gridMenuPanel(0,2);
        jEastCenterChoice3CenterPanel1 = JComponentBuilder.gridMenuPanel(0,2);
        jEastCenterChoice3CenterPanel2 = JComponentBuilder.gridMenuPanel(0,2);
        jEastCenterCenterPanel = JComponentBuilder.borderMenuPanel();
        jEastCenterPanelChoice1 = JComponentBuilder.borderMenuPanel();
        jEastCenterPanelChoice2 = JComponentBuilder.borderMenuPanel();
        jEastCenterPanelChoice3 = JComponentBuilder.borderMenuPanel();
        jEastCenterPanelChoice4 = JComponentBuilder.borderMenuPanel();
        jButtonLeftMenu1 = JComponentBuilder.menuButton("1",new showMenu(jEastCenterPanelChoice1,jEastCenterCenterPanel));
        jButtonLeftMenu2 = JComponentBuilder.menuButton("2",new showMenu(jEastCenterPanelChoice2,jEastCenterCenterPanel));
        jButtonLeftMenu3 = JComponentBuilder.menuButton("3",new showMenu(jEastCenterPanelChoice3,jEastCenterCenterPanel));
        jButtonLeftMenu4 = JComponentBuilder.menuButton("4",new showMenu(jEastCenterPanelChoice4,jEastCenterCenterPanel));

        dashboard = new GameDisplay();
        factionManager = new FactionManager();
        mapEntity = new HashMap<>();
        currentJButton = JComponentBuilder.menuButton("");
        speedBoost = 1;

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        JPanel jEastWestPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastButtonPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterPanel = JComponentBuilder.borderMenuPanel();
        JPanel jEastCenterNorthPanel = JComponentBuilder.gridMenuPanel(1,4,0,0);

        JButton jButtonNorthMenu1 = JComponentBuilder.menuButton("⏯",new flipTimeListener());
        JButton jButtonNorthMenu2 = JComponentBuilder.menuButton(">",new setSpeedBoostListener(1));
        JButton jButtonNorthMenu3 = JComponentBuilder.menuButton(">>",new setSpeedBoostListener(4));
        JButton jButtonNorthMenu4 = JComponentBuilder.menuButton(">>>",new setSpeedBoostListener(8));

        JScrollPane jScrollPane1 = JComponentBuilder.ScrollPaneMenuPanel(jEastCenterChoice1CenterPanel);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice1.add(jScrollPane1);

        JScrollPane jScrollPane2 = JComponentBuilder.ScrollPaneMenuPanel(jEastCenterChoice2CenterPanel);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice2.add(jScrollPane2);

        JPanel jEastCenterChoice3CenterPanel = JComponentBuilder.boxMenuPanel(BoxLayout.Y_AXIS);
        jEastCenterChoice3CenterPanel.add(jEastCenterChoice3CenterPanel1);
        jEastCenterChoice3CenterPanel.add(jEastCenterChoice3CenterPanel2);
        JScrollPane jScrollPane3 = JComponentBuilder.ScrollPaneMenuPanel(jEastCenterChoice3CenterPanel);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice3.add(jScrollPane3);

        jEastCenterNorthPanel.add(jButtonLeftMenu1);
        jEastCenterNorthPanel.add(jButtonLeftMenu2);
        jEastCenterNorthPanel.add(jButtonLeftMenu3);
        jEastCenterNorthPanel.add(jButtonLeftMenu4);

        jNorthEastPanel.add(jButtonNorthMenu1);
        jNorthEastPanel.add(jButtonNorthMenu2);
        jNorthEastPanel.add(jButtonNorthMenu3);
        jNorthEastPanel.add(jButtonNorthMenu4);

        showLeftMenuButton = JComponentBuilder.menuButton("<", new showMenu(jEastPanel,jEastATHPanel));
        jEastButtonPanel.add(showLeftMenuButton,BorderLayout.NORTH);
        hideLeftMenuButton = JComponentBuilder.menuButton(">", new showMenu(jEastButtonPanel,jEastATHPanel));

        jEastATHPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jEastPanel.setOpaque(false);
        jSouthATHPanel.setOpaque(false);
        jEastButtonPanel.setOpaque(false);
        jEastWestPanel.setOpaque(false);

        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastCenterPanel.add(jEastCenterCenterPanel,BorderLayout.CENTER);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);

        jNorthATHPanel.add(jNorthEastPanel,BorderLayout.EAST);

        jEastPanel.add(jEastCenterPanel,BorderLayout.CENTER);
        jEastPanel.add(jEastWestPanel,BorderLayout.WEST);
        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastCenterPanel.add(jEastCenterCenterPanel,BorderLayout.CENTER);
        jEastCenterCenterPanel.add(jEastCenterPanelChoice1,BorderLayout.CENTER);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);
        jEastATHPanel.add(jEastPanel,BorderLayout.CENTER);

        jSouthATHPanel.add(jSouthEastPanel,BorderLayout.EAST);

        jPanelATH.add(jNorthATHPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);
        jPanelATH.add(jEastATHPanel,BorderLayout.EAST);
        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        //color
        jEastCenterPanel.setBackground(Color.DARK_GRAY);
        jSouthEastPanel.setBackground(Color.GRAY);
        jNorthATHPanel.setBackground(new Color(64, 64, 64,100));
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);
        jEastCenterChoice1CenterPanel.setBackground(Color.GRAY);
        jEastCenterChoice2CenterPanel.setBackground(Color.GRAY);
        jEastCenterChoice3CenterPanel1.setBackground(Color.GRAY);
        jEastCenterChoice3CenterPanel2.setBackground(Color.GRAY);

        this.add(jLayeredPane);
        this.addMouseListener(new MouseListener());
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
        elementInPanelUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jNorthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.05)));
        jNorthEastPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2),getWindow().getHeight()));

        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jSouthEastPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20-Math.max(26,getWindow().getHeight()*0.04)),getWindow().getHeight()));

        jEastATHPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));
        if (jEastATHPanel.isAncestorOf(showLeftMenuButton))jEastATHPanel.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        showLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        hideLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        jEastCenterChoice1CenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08* MapGame.getInstance().getPlayer().getLstBoat().size()))));
        jEastCenterChoice2CenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08* MapGame.getInstance().getPlayer().getLstHarbor().size()))));
        jEastCenterChoice3CenterPanel1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.1* MapGame.getInstance().getPlayer().getLstFleet().size()))));
        jEastCenterChoice3CenterPanel2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.1* MapGame.getInstance().getPlayer().getLstSeaRouts().size()))));
        jEastCenterChoice3CenterPanel1.setBorder(new EmptyBorder(0,0, (int) (getWindow().getHeight()*0.01),0));
        getWindow().revalidate();
        getWindow().repaint();
    }

    private void elementInPanelUpdate() {
        jEastCenterChoice1CenterPanel.removeAll();
        jEastCenterChoice2CenterPanel.removeAll();
        JButton tmp;
        for (Boat boat : MapGame.getInstance().getPlayer().getLstBoat()){
            tmp = JComponentBuilder.menuButton(boat,new buttonEntityListener(boat));
            mapEntity.put(boat,tmp);
            jEastCenterChoice1CenterPanel.add(tmp);

        }
        for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
            tmp = JComponentBuilder.menuButton(harbor,new buttonEntityListener(harbor));
            mapEntity.put(harbor,tmp);
            jEastCenterChoice2CenterPanel.add(tmp);
        }
        for (Fleet fleet : MapGame.getInstance().getPlayer().getLstFleet()){
            tmp = JComponentBuilder.menuButton(fleet);
            jEastCenterChoice3CenterPanel1.add(tmp);
        }
        for (SeaRoad seaRoad : MapGame.getInstance().getPlayer().getLstSeaRouts()){
            tmp = JComponentBuilder.menuButton(seaRoad);
            jEastCenterChoice3CenterPanel2.add(tmp);
        }

    }

    private void ChangeCurrentJButton(JButton jButton){
        currentJButton.setBackground(Color.DARK_GRAY);
        currentJButton = jButton;
        currentJButton.setBackground(new Color(125, 130, 200));
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

    public class setSpeedBoostListener implements ActionListener {
        private int value;

        public setSpeedBoostListener(int value) {
            this.value = value;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            speedBoost = value;
        }
    }

    public class flipTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setTimeStop(!MapGame.getInstance().isTimeStop());
        }
    }

    public class buttonEntityListener implements ActionListener {
        private Entity entity;

        public buttonEntityListener(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ChangeCurrentJButton(mapEntity.get(entity));
        }
    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Point point = ListenerBehaviorManager.create().clickLogic(MainGameMenu.this, e.getPoint());
            Boat boat = factionManager.getBoatManager().pointCollisionToMapBoat(point);
            Harbor harbor = factionManager.getHarborManager().pointCollisionToMapHarbor(point);
            if(harbor != null){
                if(MapGame.getInstance().getPlayer().getLstHarbor().contains(harbor)){
                    jEastATHPanel.removeAll();
                    jEastATHPanel.add(jEastPanel);
                    jEastCenterCenterPanel.removeAll();
                    jEastCenterCenterPanel.add(jEastCenterPanelChoice2);
                    ChangeCurrentJButton(mapEntity.get(harbor));
                }
                //WIP
            }
            else if(boat != null){
                if(MapGame.getInstance().getPlayer().getLstBoat().contains(boat)){
                    jEastATHPanel.removeAll();
                    jEastATHPanel.add(jEastPanel);
                    jEastCenterCenterPanel.removeAll();
                    jEastCenterCenterPanel.add(jEastCenterPanelChoice1);
                    ChangeCurrentJButton(mapEntity.get(boat));
                }
                //WIP
            }
            sizeUpdate();
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ThreadStop = true;
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_MAIN_GAME);
            }
            else if(event.getKeyCode() == KeyEvent.VK_SPACE){
                MapGame.getInstance().setTimeStop(!MapGame.getInstance().isTimeStop());
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
                Thread.sleep((long) GameConfiguration.GAME_SPEED/speedBoost);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!MapGame.getInstance().isTimeStop()){
                factionManager.nextRound();
            }
            dashboard.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
