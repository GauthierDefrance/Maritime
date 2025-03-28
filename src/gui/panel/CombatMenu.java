package gui.panel;

import engine.battleengine.data.Battle;
import engine.battleengine.process.BattleManager;
import config.GameConfiguration;
import engine.MapGame;
import engine.battleengine.utilities.AngleCalculator;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.process.BoatManager;
import gui.MainGUI;
import gui.PopUp;
import gui.panel.Display.BattleDisplay;
import gui.process.ImageStock;
import gui.process.ListenerBehaviorManager;
import gui.utilities.GUILoader;
import gui.process.JComponentBuilder;
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
    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel jNorthEastPanel;
    private JPanel jSouthATHPanel;
    private JPanel jWestATHPanel;
    private JPanel jWestCenterPanel;
    private JPanel jWestSouthPanel;

    private JButton confirmBattle;

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
    public void init() {
        this.setLayout(new BorderLayout());
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        jPanelATH = JComponentBuilder.borderMenuPanel();
        jWestATHPanel = JComponentBuilder.borderMenuPanel();
        jSouthATHPanel = JComponentBuilder.flowMenuPanel();
        jNorthATHPanel = JComponentBuilder.borderMenuPanel();
        jNorthEastPanel = JComponentBuilder.gridMenuPanel(1,4,0,0);
        jWestCenterPanel = JComponentBuilder.gridMenuPanel(0,2);
        jWestSouthPanel = JComponentBuilder.gridMenuPanel(1,0,0,0);
        confirmBattle = JComponentBuilder.menuButton("Battle",new confirmContinueBattleListener());

        battle.setPlacingMode(true);
        dashboard = new BattleDisplay(battle);
        battleManager = new BattleManager(battle);
        speedBoost = 1;

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        JPanel jWestPanel = JComponentBuilder.borderMenuPanel();

        JScrollPane jScrollPane = new JScrollPane(jWestCenterPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jWestPanel.add(jScrollPane);

        jWestSouthPanel.add(JComponentBuilder.menuButton("Cancel",new cancelPlacingListener()));

        JButton jButtonNorthMenu1 = JComponentBuilder.menuButton("⏯",new flipTimeListener());
        JButton jButtonNorthMenu2 = JComponentBuilder.menuButton(">",new setSpeedBoostListener(1));
        JButton jButtonNorthMenu3 = JComponentBuilder.menuButton(">>",new setSpeedBoostListener(4));
        JButton jButtonNorthMenu4 = JComponentBuilder.menuButton(">>>",new setSpeedBoostListener(8));

        jNorthEastPanel.add(jButtonNorthMenu1);
        jNorthEastPanel.add(jButtonNorthMenu2);
        jNorthEastPanel.add(jButtonNorthMenu3);
        jNorthEastPanel.add(jButtonNorthMenu4);

        jWestATHPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jWestPanel.setOpaque(false);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        JPanel jNorthPanel = JComponentBuilder.borderMenuPanel();
        jNorthPanel.add(jNorthEastPanel,BorderLayout.NORTH);
        jNorthATHPanel.add(jNorthPanel,BorderLayout.EAST);

        jWestATHPanel.add(jWestPanel,BorderLayout.CENTER);
        jWestATHPanel.add(jWestSouthPanel,BorderLayout.SOUTH);

        jSouthATHPanel.add(confirmBattle);

        jPanelATH.add(jNorthATHPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);
        jPanelATH.add(jWestATHPanel,BorderLayout.WEST);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        jNorthPanel.setBackground(Color.BLACK);
        jNorthATHPanel.setBackground(Color.BLACK);
        jSouthATHPanel.setBackground(Color.BLACK);
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);
        jWestCenterPanel.setBackground(Color.GRAY);

        this.add(jLayeredPane);
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
        elementInPanelUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jPanelATH.setBounds(getWindow().getBounds());
        jNorthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jNorthEastPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2),(int) (getWindow().getHeight()*0.05)));

        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        jWestSouthPanel.setPreferredSize(new Dimension(getWindow().getHeight(),(int) Math.max(26,getWindow().getHeight()*0.04)));
        jWestATHPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));
        jWestCenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08* MapGame.getInstance().getPlayer().getLstBoat().size()))));
        confirmBattle.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        getWindow().revalidate();
        getWindow().repaint();
    }

    public void elementInPanelUpdate() {
        jWestCenterPanel.removeAll();
        for (Boat boat : battle.getLstBoatsToPlace()){
            JButton tmp = JComponentBuilder.menuButton(boat, new buttonMouseListener(boat));
            tmp.addMouseMotionListener(new buttonMouseListener(boat));
            jWestCenterPanel.add(tmp);
        }
        sizeUpdate();
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
            if(MapGame.getInstance().isTimeStop()){
            jWestATHPanel.setVisible(true);
            confirmBattle.setVisible(true);
            battle.setPlacingMode(true);
            }
        }
    }

    public class cancelPlacingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            battleManager.getPlacingManager().cancelPlacing();
            elementInPanelUpdate();
            sizeUpdate();
        }
    }

    public class confirmContinueBattleListener implements ActionListener {
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
                    battleManager.getPlacingManager().tryPlaceBoat(battle.getCurrentBoat(), point);
                }
                battle.setCurrentBoat(null);
                battle.setCurrentBoatPoint(new Point(-100,-100));
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
            }
            dashboard.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }

            if(battleManager.battleEnded()){
                ArrayList<String> lstText = battleManager.battleEnd(); //Cette ligne est supposé actualiser l'inventaire des bateaux.
                JPanel jPanel = JComponentBuilder.gridMenuPanel(3,2,0,20);
                jPanel.add(JComponentBuilder.ImageLabel(new ImageIcon(ImageStock.getTbSprite(3, ImageStock.getColorInt(battle.getFactionB().getColor())))));
                jPanel.add(JComponentBuilder.menuLabel(lstText.get(0)));
                jPanel.add(JComponentBuilder.ImageLabel(new ImageIcon(ImageStock.getTbSprite(3,ImageStock.getColorInt(battle.getFactionA().getColor())))));
                jPanel.add(JComponentBuilder.menuLabel(lstText.get(1)));
                jPanel.add(JComponentBuilder.ImageLabel(new ImageIcon(ImageStock.getImages(2))));
                jPanel.add(JComponentBuilder.menuLabel(lstText.get(2)));
                JOptionPane.showMessageDialog(CombatMenu.this,jPanel);
                ThreadStop = true;
                MapGame.getInstance().setTimeStop(true);
                GUILoader.loadMainGame();
            }
        }
    }
}
