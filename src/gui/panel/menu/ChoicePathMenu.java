package gui.panel.menu;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;
import engine.data.graph.GraphSegment;
import engine.process.manager.FactionManager;
import engine.data.trading.TradeOffer;
import engine.utilities.SearchInGraph;
import gui.PopUp;
import gui.panel.display.ChoiceDisplay;
import gui.process.ListenerBehaviorManager;
import gui.utilities.GUILoader;
import gui.process.JComponentFactory;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import static config.GameConfiguration.WAR_THRESHOLD;
import static gui.MainGUI.getWindow;

/**
 * Thread based Menu allowing to select a Path or a couple of Harbor based on it's state
 * @author Kenan Ammad
 * @version 0.2
 */
public class ChoicePathMenu extends JPanel implements Runnable {

    private static final Logger log = Logger.getLogger(ChoicePathMenu.class);
    private final int token;
    private final int state;
    private final Faction faction;
    private Harbor harbor1;
    private Harbor harbor2;
    private ArrayList<GraphPoint> path;

    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jSouthATHPanel;

    private JButton confirm;
    private JButton cancel;
    private JButton reset;
    private JButton automatic;

    private FactionManager factionManager;
    private ChoiceDisplay dashboard;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public ChoicePathMenu( Faction faction, int token) {
        super();
        this.token = token;
        this.state = 1;
        this.harbor1 = null;
        this.harbor2 = null;
        this.faction = faction;
        init();
    }

    /**
     * Typical constructor to make the startMenu appear
     */
    public ChoicePathMenu(Harbor harbor1 , Harbor harbor2, int token) {
        super();
        this.token = token;
        this.state = 0;
        this.harbor1 = harbor1;
        this.harbor2 = harbor2;
        this.faction = null;
        init();
    }

    public void init() {
        this.setLayout(new BorderLayout());
        dashboardJPanel = JComponentFactory.borderMenuPanel();
        jPanelATH = JComponentFactory.borderMenuPanel();
        jSouthATHPanel = JComponentFactory.flowMenuPanel();
        confirm = JComponentFactory.menuButton("Confirm",new confirmListener());
        cancel = JComponentFactory.menuButton("Cancel",new cancelListener());
        reset = JComponentFactory.menuButton("reset",new resetListener());
        automatic = JComponentFactory.menuButton("automatic",new automaticListener());

        factionManager = FactionManager.getInstance();
        dashboard = new ChoiceDisplay(state);

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        jPanelATH.setOpaque(false);
        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        if(state == 0)jSouthATHPanel.add(reset);
        jSouthATHPanel.add(cancel);
        if(state == 0)jSouthATHPanel.add(automatic);
        jSouthATHPanel.add(confirm);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);

        if (state == 0){
            path = new ArrayList<>();
            dashboard.setHarbor1(harbor1);
            dashboard.setHarbor2(harbor2);
            dashboard.setPath(path);
            path.add(harbor1.getGraphPosition());
        }
        this.add(jLayeredPane);
        this.addMouseListener(new MouseListener());
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jPanelATH.setBounds(getWindow().getBounds());
        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.10)));
        confirm.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        cancel.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        reset.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        automatic.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.25), (int) Math.max(26,getWindow().getHeight()*0.08)));
        getWindow().revalidate();
        getWindow().repaint();
    }

    public class confirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (state){
                case 0:
                    if (path.get(path.size()-1).equals(harbor2.getGraphPosition())){
                        //WIP
                    } else JOptionPane.showMessageDialog(ChoicePathMenu.this,"where do you want to go ??");
                    break;
                case 1:
                    if (faction == null){
                        log.error("faction is null, cannot proceed");
                        return;
                    }
                    if(harbor1 != null && harbor2 != null){
                        if (faction.getRelationship() == WAR_THRESHOLD) {
                            // /!\ must lead to a choice between picking or building a fleet instead
                            GUILoader.loadFleetManagingMenu();
                            return;
                        } GUILoader.loadTradeMenu(new TradeOffer(harbor1, harbor2));
                    } else JOptionPane.showMessageDialog(ChoicePathMenu.this,"No proper selection");
                    break;
                default:
                    log.error("ChoicePathMenu : Unknown state --> could not proceed with confirmation");
                    break;
            }
        }
    }

    public class resetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                GraphPoint graphPoint = path.get(0);
                path.clear();
                path.add(graphPoint);
                dashboard.setPath(path);
            }
        }
    }

    public class cancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                if(!path.isEmpty()&&path.size()>1)path.remove(path.size()-1);
                dashboard.setPath(path);
            }
            else if(state == 1){
                harbor2 = null;
                harbor1 = null;
                dashboard.setHarbor2(null);
                dashboard.setHarbor1(null);

            }
        }
    }

    public class automaticListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                path = SearchInGraph.findPath(harbor1.getGraphPosition(),harbor2.getGraphPosition());
                dashboard.setPath(path);
            }
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

    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Point point = ListenerBehaviorManager.create().clickLogic(ChoicePathMenu.this, e.getPoint());

            switch (state){
                case 0: //We're tracing a Path

                    GraphPoint graphPoint = SearchInGraph.pointCollisionToMapGraphPoint(point);
                    if (graphPoint != null && !path.isEmpty()) {
                        if (path.get(path.size()-1).equals(graphPoint)&&path.size()>1){
                            path.remove(graphPoint);
                            dashboard.setPath(path);
                        }

                        else if (!path.get(path.size()-1).equals(harbor2.getGraphPosition())){
                            for(Map.Entry<String, GraphSegment> entry : path.get(path.size()-1).getSegmentHashMap().entrySet()){
                                if(entry.getValue().getGraphPoint().equals(graphPoint)&& !path.contains(entry.getValue().getGraphPoint())){
                                    path.add(graphPoint);
                                    dashboard.setPath(path);
                                }
                            }
                        }
                    } break;

                case 1: //We're designating Harbor Object

                    if (faction == null) {
                        log.error("Could not proceed --> faction is null");
                        break;
                    }
                    Harbor clickedHarbor = factionManager.getHarborManager().pointCollisionToMapHarbor(point);
                    if (clickedHarbor == null) {
                        break;
                    }

                    if (e.getButton() == MouseEvent.BUTTON1) /*Left Click*/ {
                        if (MapGame.getInstance().getPlayer().getLstHarbor().contains(clickedHarbor) && harbor1 == null) {
                            harbor1 = clickedHarbor;
                            dashboard.setHarbor1(harbor1);
                        } else if (faction.getLstHarbor().contains(clickedHarbor) && harbor2 == null) {
                            harbor2 = clickedHarbor;
                            dashboard.setHarbor2(harbor2);
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3)  /*Right Click*/ {
                        if (MapGame.getInstance().getPlayer().getLstHarbor().contains(harbor1)) {
                            harbor1 = null;
                            dashboard.setHarbor1(null);
                        } else if (faction.getLstHarbor().contains(harbor2)) {
                            harbor2 = null;
                            dashboard.setHarbor2(null);
                        }
                    } break;
                default:
                        log.error("ChoicePathMenu : Unknown state --> could not proceed with logic");
            }
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ThreadStop = true;
                ListenerBehaviorManager.create().goBack(token,faction);
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
            dashboard.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
