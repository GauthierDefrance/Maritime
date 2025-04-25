package gui.panel.menu;

import engine.battleengine.data.Battle;
import engine.battleengine.process.BattleManager;
import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.boats.Boat;
import engine.data.graph.GraphPoint;
import engine.process.manager.BoatManager;
import gui.MainGUI;
import gui.PopUp;
import gui.panel.display.BattleDisplay;
import gui.process.ImageStock;
import gui.process.ListenerBehaviorManager;
import gui.utilities.GUILoader;
import gui.process.JComponentFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static gui.MainGUI.getWindow;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author Kenan Ammad
 * @version 0.2
 */
public class CombatMenu extends JPanel implements Runnable {

    private int speedBoost;
    private JLayeredPane jLayeredPane;
    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel jNorthEastPanel;
    private JPanel jSouthATHPanel;
    private JPanel jWestATHPanel;
    private JPanel jWestPanel;
    private JPanel jWestCenterPanel;

    private JButton jCancelButton;
    private JButton jButtonNorthMenu1;
    private JButton jButtonNorthMenu2;
    private JButton jButtonNorthMenu3;
    private JButton jButtonNorthMenu4;
    private JButton confirmBattle;
    private JButton showLeftMenuButton;
    private JButton hideLeftMenuButton;

    private BattleDisplay dashboard;
    private Battle battle;
    private BattleManager battleManager;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public CombatMenu(Battle battle) {
        super();
        this.battle = battle;
        init();
    }
    private void init() {
        this.setLayout(new BorderLayout());
        jLayeredPane = JComponentFactory.JLayeredPane();
        dashboardJPanel = JComponentFactory.borderMenuPanel();
        jPanelATH = JComponentFactory.borderMenuPanel();
        jWestATHPanel = JComponentFactory.borderMenuPanel();
        jWestPanel = JComponentFactory.borderMenuPanel();
        jSouthATHPanel = JComponentFactory.flowMenuPanel();
        jNorthATHPanel = JComponentFactory.borderMenuPanel();
        jNorthEastPanel = JComponentFactory.gridMenuPanel(1,4,0,0);
        jWestCenterPanel = JComponentFactory.gridMenuPanel(0,2);
        jCancelButton = JComponentFactory.menuButton("Cancel",new cancelPlacingListener());
        confirmBattle = JComponentFactory.menuButton("Battle",new confirmContinueBattleListener());

        battle.setPlacingMode(true);
        dashboard = new BattleDisplay(battle);
        battleManager = new BattleManager(battle);
        speedBoost = 1;

        //Window arrangement
        JPanel jWestButtonPanel = JComponentFactory.borderMenuPanel();
        JPanel hideLeftMenuPanel = JComponentFactory.borderMenuPanel();

        JScrollPane jScrollPane = new JScrollPane(jWestCenterPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        showLeftMenuButton = JComponentFactory.menuButton(">", new showMenu(jWestPanel,jWestATHPanel));
        jWestButtonPanel.add(showLeftMenuButton,BorderLayout.NORTH);
        hideLeftMenuButton = JComponentFactory.menuButton("<", new showMenu(jWestButtonPanel,jWestATHPanel));
        hideLeftMenuPanel.add(hideLeftMenuButton,BorderLayout.NORTH);

        JPanel jWestTmpPanel = JComponentFactory.borderMenuPanel();
        jWestTmpPanel.add(jScrollPane,BorderLayout.CENTER);
        jWestTmpPanel.add(jCancelButton,BorderLayout.SOUTH);
        jWestPanel.add(jWestTmpPanel,BorderLayout.CENTER);
        jWestPanel.add(hideLeftMenuPanel,BorderLayout.EAST);


        jButtonNorthMenu1 = JComponentFactory.menuButton("⏯",new flipTimeListener());
        jButtonNorthMenu2 = JComponentFactory.menuButton(">",new setSpeedBoostListener(1));
        jButtonNorthMenu3 = JComponentFactory.menuButton(">>",new setSpeedBoostListener(4));
        jButtonNorthMenu4 = JComponentFactory.menuButton(">>>",new setSpeedBoostListener(8));
        jButtonNorthMenu2.setEnabled(false);

        jNorthEastPanel.add(jButtonNorthMenu1);
        jNorthEastPanel.add(jButtonNorthMenu2);
        jNorthEastPanel.add(jButtonNorthMenu3);
        jNorthEastPanel.add(jButtonNorthMenu4);

        jWestATHPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jWestPanel.setOpaque(false);
        jWestButtonPanel.setOpaque(false);
        hideLeftMenuPanel.setOpaque(false);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        JPanel jNorthPanel = JComponentFactory.borderMenuPanel();
        jNorthPanel.add(jNorthEastPanel,BorderLayout.NORTH);
        jNorthATHPanel.add(jNorthPanel,BorderLayout.EAST);

        jWestATHPanel.add(jWestPanel,BorderLayout.CENTER);

        jSouthATHPanel.add(confirmBattle);

        jPanelATH.add(jNorthATHPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);
        if(battle.getFactionA().equals(MapGame.getInstance().getPlayer()))jPanelATH.add(jWestATHPanel,BorderLayout.WEST);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        jNorthPanel.setBackground(Color.BLACK);
        jNorthATHPanel.setBackground(Color.BLACK);
        jSouthATHPanel.setBackground(Color.BLACK);
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);
        jWestCenterPanel.setBackground(Color.GRAY);

        this.add(jLayeredPane);
        this.addKeyListener(new KeyControls());
        if(battle.getFactionA().equals(MapGame.getInstance().getPlayer()))this.addMouseListener(new MouseListener());
        if(battle.getFactionA().equals(MapGame.getInstance().getPlayer()))this.addMouseMotionListener(new MouseListener());
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

        jPanelATH.setBounds(getWindow().getBounds());
        jNorthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jNorthEastPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2),(int) (getWindow().getHeight()*0.05)));

        showLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        hideLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jWestPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));
        jWestCenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08* MapGame.getInstance().getPlayer().getLstBoat().size()))));
        confirmBattle.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        jLayeredPane.revalidate();
        jLayeredPane.repaint();
    }

    private void elementInPanelUpdate() {
        if(battle.getFactionA().equals(MapGame.getInstance().getPlayer())) {
            jWestCenterPanel.removeAll();
            for (Boat boat : battle.getLstBoatsToPlace()) {
                JButton tmp = JComponentFactory.menuButton(boat, new buttonMouseListener(boat));
                tmp.addMouseMotionListener(new buttonMouseListener(boat));
                jWestCenterPanel.add(tmp);
            }
            sizeUpdate();
        }
    }

    private class setSpeedBoostListener implements ActionListener {
        private int value;

        public setSpeedBoostListener(int value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            speedBoost = value;
            jButtonNorthMenu1.setEnabled(true);
            jButtonNorthMenu2.setEnabled(true);
            jButtonNorthMenu3.setEnabled(true);
            jButtonNorthMenu4.setEnabled(true);
            ((JButton)e.getSource()).setEnabled(false);
        }

    }

    private class flipTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setTimeStop(!MapGame.getInstance().isTimeStop());
            if(MapGame.getInstance().isTimeStop()){
                jWestATHPanel.setVisible(true);
                confirmBattle.setVisible(true);
                battle.setPlacingMode(true);
            }
        }
    }

    private class showMenu implements ActionListener {
        private final JPanel jPanel1;
        private final JPanel jPanel2;

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

    private class cancelPlacingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            battleManager.getPlacingManager().cancelPlacing();
            elementInPanelUpdate();
            sizeUpdate();
        }
    }

    private class confirmContinueBattleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!battleManager.getPlacingManager().confirmContinueBattle()){
                JOptionPane.showMessageDialog(CombatMenu.this,"you need to place a boat");
            }
            else {
                MapGame.getInstance().setTimeStop(false);
                jWestATHPanel.setVisible(false);
                confirmBattle.setVisible(false);
            }
            elementInPanelUpdate();
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

    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Point point = ListenerBehaviorManager.create().clickLogic(CombatMenu.this, e.getPoint());
            Boat boat = BoatManager.boatCollisionToPoint(point,battle.getBoatsInBattleA().getArrayListBoat());
            if(boat == null)boat = BoatManager.boatCollisionToPoint(point,battle.getLstBoatsCurrentlyBeingPlaced());
            if (e.getButton() == MouseEvent.BUTTON1 && boat != null) {
                battle.setCurrentBoat2(boat);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && battle.getCurrentBoat2()!=null) {
                Point point0 = SwingUtilities.convertPoint((Component) e.getSource(),e.getPoint(),dashboardJPanel);
                Point point = ListenerBehaviorManager.create().clickLogic(CombatMenu.this, point0);
                if(GameConfiguration.ZONE.contains(point)) {
                    battle.getCurrentBoat2().setNextGraphPoint(new GraphPoint(point,""));
                }
                battle.setCurrentBoat2(null);
                battle.setCurrentBoatPoint2(new Point(-1000,-1000));
                elementInPanelUpdate();

            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            battle.setCurrentBoatPoint2(SwingUtilities.convertPoint((Component) e.getSource(),e.getPoint(),MainGUI.getWindow()));
            dashboard.repaint();
        }
    }

    private class buttonMouseListener extends MouseAdapter {
        private Boat boat;

        public buttonMouseListener(Boat boat) {
            this.boat = boat;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                battle.setCurrentBoat(boat);
                jWestATHPanel.setVisible(false);
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && battle.getCurrentBoat()!=null) {
                Point point0 = SwingUtilities.convertPoint((Component) e.getSource(),e.getPoint(),dashboardJPanel);
                Point point = ListenerBehaviorManager.create().clickLogic(CombatMenu.this, point0);
                if(GameConfiguration.SPAWN_ZONE.contains(point)) {
                    battleManager.getPlacingManager().placeBoat(battle.getCurrentBoat(), point);
                }
                battle.setCurrentBoat(null);
                battle.setCurrentBoatPoint(new Point(-1000,-1000));
                elementInPanelUpdate();

            }
            jWestATHPanel.setVisible(true);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            battle.setCurrentBoatPoint(SwingUtilities.convertPoint((Component) e.getSource(),e.getPoint(),MainGUI.getWindow()));
            dashboard.repaint();
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ThreadStop = true;
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_COMBAT,battle);
            }
            else if(event.getKeyCode() == KeyEvent.VK_SPACE){
                if(MapGame.getInstance().isTimeStop()){
                    MapGame.getInstance().setTimeStop(false);
                    jWestATHPanel.setVisible(false);
                    confirmBattle.setVisible(false);
                    battle.setPlacingMode(false);
                }
                else {
                    MapGame.getInstance().setTimeStop(true);
                    jWestATHPanel.setVisible(true);
                    confirmBattle.setVisible(true);
                    battle.setPlacingMode(true);

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
                Thread.sleep((long) GameConfiguration.GAME_SPEED/speedBoost);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!MapGame.getInstance().isTimeStop()){
                battleManager.tick();
                MapGame.getInstance().addTime(((double)GameConfiguration.GAME_SPEED)/1000);
            }
            jLayeredPane.revalidate();
            jLayeredPane.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }

            if(battleManager.battleEnded()){
                ArrayList<String> lstText = battleManager.battleEnd();
                JPanel jPanel = JComponentFactory.gridMenuPanel(3,2,0,20);
                jPanel.add(JComponentFactory.ImageLabel(new ImageIcon(ImageStock.getTbSprite(3, ImageStock.getColorInt(battle.getFactionB().getColor())))));
                jPanel.add(JComponentFactory.menuLabel(lstText.get(0)));
                jPanel.add(JComponentFactory.ImageLabel(new ImageIcon(ImageStock.getTbSprite(3,ImageStock.getColorInt(battle.getFactionA().getColor())))));
                jPanel.add(JComponentFactory.menuLabel(lstText.get(1)));
                jPanel.add(JComponentFactory.ImageLabel(new ImageIcon(ImageStock.getImages(2))));
                jPanel.add(JComponentFactory.menuLabel(lstText.get(2)));
                JOptionPane.showMessageDialog(CombatMenu.this,jPanel);
                ThreadStop = true;
                GUILoader.loadMainGame();
            }
        }
    }
}
