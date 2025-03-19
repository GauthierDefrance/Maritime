package gui.panel;

import battleengine.entity.Battle;
import battleengine.process.BattleManager;
import config.GameConfiguration;
import engine.MapGame;
import engine.entity.boats.Boat;
import gui.MainGUI;
import gui.PopUp;
import gui.panel.Display.BattleDisplay;
import gui.process.ListenerBehaviorManager;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;
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
        jWestCenterPanel = JComponentBuilder.gridMenuPanel(0,2);
        jWestSouthPanel = JComponentBuilder.gridMenuPanel(1,0,0,0);
        confirmBattle = JComponentBuilder.menuButton("Battle",new confirmContinueBattleListener());

        battle.setPlacingMode(true);
        dashboard = new BattleDisplay(battle);
        battleManager = new BattleManager(battle);

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        JPanel jWestPanel = JComponentBuilder.borderMenuPanel();

        JScrollPane jScrollPane = new JScrollPane(jWestCenterPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jWestPanel.add(jScrollPane);

        jWestSouthPanel.add(JComponentBuilder.menuButton("Cancel",new cancelPlacingListener()));

        jWestATHPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jWestPanel.setOpaque(false);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        jWestATHPanel.add(jWestPanel,BorderLayout.CENTER);
        jWestATHPanel.add(jWestSouthPanel,BorderLayout.SOUTH);

        jSouthATHPanel.add(confirmBattle);

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
                battleManager.getPlacingManager().tryPlaceBoat(battle.getCurrentBoat(),point);
                battle.setCurrentBoat(null);
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
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_COMBAT);
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
                Thread.sleep(GameConfiguration.GAME_SPEED);

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
        }
    }
}
