package gui.panel;

import battleengine.entity.Battle;
import battleengine.process.BattleManager;
import config.GameConfiguration;
import config.MapBuilder;
import engine.entity.boats.Boat;
import engine.entity.boats.Standard;
import engine.graph.GraphPoint;
import engine.process.FactionManager;
import gui.MainGUI;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author Kenan Ammad
 * @version 0.2
 */
public class CombatMenu extends JPanel implements Runnable {

    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel jSouthATHPanel;
    private JPanel jWestATHPanel;
    private JPanel jWestCenterPanel;
    private JPanel jWestSouthPanel;

    private GameDisplay dashboard;
    private Battle battle;
    private BattleManager battleManager;
    private MapBuilder map;
    private FactionManager factionManager;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public CombatMenu(MapBuilder map, Battle battle) {
        super();
        this.map = map;
        this.battle = battle;
        init();
    }
    public void init() {
        this.setLayout(new BorderLayout());
        jPanelATH = JComponentBuilder.borderMenuPanel();
        jWestATHPanel = JComponentBuilder.borderMenuPanel();
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        jSouthATHPanel = JComponentBuilder.borderMenuPanel();
        jNorthATHPanel = JComponentBuilder.borderMenuPanel();
        jWestCenterPanel = JComponentBuilder.gridMenuPanel(0,2);
        jWestSouthPanel = JComponentBuilder.gridMenuPanel(1,0,0,0);

        dashboard = new GameDisplay(map);
        factionManager = new FactionManager(map);
        battleManager = new BattleManager(battle);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        JPanel jWestPanel = JComponentBuilder.borderMenuPanel();

        JScrollPane jScrollPane = new JScrollPane(jWestCenterPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jWestPanel.add(jScrollPane);

        jWestSouthPanel.add(JComponentBuilder.menuButton("Cancel"));

        jWestATHPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jWestPanel.setOpaque(false);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);

        jWestATHPanel.add(jWestPanel,BorderLayout.CENTER);
        jWestATHPanel.add(jWestSouthPanel,BorderLayout.SOUTH);

        jPanelATH.add(jNorthATHPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);
        jPanelATH.add(jWestATHPanel,BorderLayout.WEST);
        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        jNorthATHPanel.setBackground(Color.BLACK);
        jSouthATHPanel.setBackground(Color.BLACK);
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);
        jWestCenterPanel.setBackground(Color.GRAY);

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

        jNorthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jWestSouthPanel.setPreferredSize(new Dimension(getWindow().getHeight(),(int) Math.max(26,getWindow().getHeight()*0.04)));
        jWestATHPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));
        jWestCenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08*map.getPlayer().getLstBoat().size()))));
        getWindow().revalidate();
        getWindow().repaint();
    }

    public void elementInPanelUpdate() {
        jWestCenterPanel.removeAll();
        for (Boat boat : battle.getLstBoatsToPlace()){
            jWestCenterPanel.add(JComponentBuilder.menuButton(boat,new MouseListener(boat)));
        }
        sizeUpdate();
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

    private class MouseListener extends MouseAdapter {
        private Boat boat;

        private MouseListener(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
            } else if (e.getButton() == MouseEvent.BUTTON3) {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                battleManager.getPlacingManager().tryPlaceBoat(boat,e.getPoint());
            }
            elementInPanelUpdate();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                MainGUI.setMap(map);
                ThreadStop = true;
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_COMBAT);
            }
            else if(event.getKeyCode() == KeyEvent.VK_SPACE){
                if(map.isTimeStop()){
                    map.setTimeStop(false);
                    jWestATHPanel.setVisible(false);
                }
                else {
                    map.setTimeStop(true);
                    jWestATHPanel.setVisible(true);

                }
                sizeUpdate();
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
