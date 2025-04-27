package gui.panel.menu;

import config.GameConfiguration;
import engine.data.MapGame;
import engine.data.Fleet;
import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;
import engine.data.graph.GraphSegment;
import engine.data.trading.SeaRoad;
import engine.process.manager.FactionManager;
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

import static gui.MainGUI.getWindow;

/**
 * Thread based Menu allowing to select a Path or a couple of Harbor based on it's state
 * @author Kenan Ammad
 * @version 0.2
 */
public class ChoicePathMenu extends JPanel implements Runnable {

    private static final Logger logger = Logger.getLogger(ChoicePathMenu.class);
    private final int state;
    private final Faction faction;
    private Harbor harbor1;
    private Harbor harbor2;
    private SeaRoad seaRoad;
    private Fleet fleet;
    private ArrayList<GraphPoint> path;

    private JLayeredPane jLayeredPane;
    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jSouthATHPanel;

    private JButton confirm;
    private JButton cancel;
    private JButton reset;
    private JButton automatic;
    private JButton goBackButton;

    private FactionManager factionManager;
    private ChoiceDisplay dashboard;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public ChoicePathMenu(SeaRoad seaRoad) {
        super();
        this.state = 0;
        this.harbor1 = seaRoad.getSellerHarbor();
        this.harbor2 = seaRoad.getTargetHarbor();
        this.seaRoad = seaRoad;
        this.faction = null;
        init();
    }

    /**
     * Typical constructor to make the startMenu appear
     */
    public ChoicePathMenu(Fleet fleet) {
        super();
        this.state = 1;
        this.faction = null;
        this.fleet = fleet;
        init();
    }

    /**
     * Typical constructor to make the startMenu appear
     */
    public ChoicePathMenu(Faction faction) {
        super();
        this.state = 2;
        this.harbor1 = null;
        this.harbor2 = null;
        this.faction = faction;
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        jLayeredPane = JComponentFactory.JLayeredPane();
        dashboardJPanel = JComponentFactory.borderMenuPanel();
        jPanelATH = JComponentFactory.borderMenuPanel();
        jSouthATHPanel = JComponentFactory.flowMenuPanel();
        confirm = JComponentFactory.menuButton("Confirm",new ConfirmListener());
        cancel = JComponentFactory.menuButton("Cancel",new CancelListener());
        reset = JComponentFactory.menuButton("reset",new ResetListener());
        automatic = JComponentFactory.menuButton("automatic",new AutomaticListener());
        goBackButton = JComponentFactory.menuButton("Go back", new GoBackButtonListener());
        factionManager = FactionManager.getInstance();
        dashboard = new ChoiceDisplay(state);

        //Window arrangement
        jPanelATH.setOpaque(false);
        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        jSouthATHPanel.add(goBackButton);
        if(state == 0)jSouthATHPanel.add(reset);
        if(state == 1)jSouthATHPanel.add(reset);
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
        if (state == 1){
            path = new ArrayList<>(fleet.getPath());
            dashboard.setPath(path);
        }
        this.add(jLayeredPane);

        jSouthATHPanel.setOpaque(false);

        this.addMouseListener(new MouseListener());
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jPanelATH.setBounds(getWindow().getBounds());
        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.10)));
        goBackButton.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.10), (int) Math.max(26,getWindow().getHeight()*0.08)));
        confirm.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.10), (int) Math.max(26,getWindow().getHeight()*0.08)));
        cancel.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.10), (int) Math.max(26,getWindow().getHeight()*0.08)));
        reset.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.10), (int) Math.max(26,getWindow().getHeight()*0.08)));
        automatic.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.10), (int) Math.max(26,getWindow().getHeight()*0.08)));
        jLayeredPane.revalidate();
        jLayeredPane.repaint();
    }

    private class ConfirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (state){
                case 0:
                    if (path.get(path.size()-1).equals(harbor2.getGraphPosition())){
                        FactionManager.getInstance().getSeaRoadManager().setNewPath(seaRoad,path);
                        GUILoader.loadFleetMenu(seaRoad);
                    }
                    else JOptionPane.showMessageDialog(ChoicePathMenu.this,"where do you want to go ??");
                    break;
                case 1:
                    if(path!=null&&!path.isEmpty())FactionManager.getInstance().getFleetManager().setNewPath(fleet,path,false);
                    GUILoader.loadFleetMenu(fleet);
                    break;
                case 2:
                    if (faction == null){
                        logger.error("faction is null, cannot proceed");
                        return;
                    }
                    if(harbor1 != null && harbor2 != null){
                        boolean flag = true;
                        for (SeaRoad seaRoad : MapGame.getInstance().getPlayer().getLstSeaRouts()){
                            if (seaRoad.getTargetHarbor().equals(harbor2)) {
                                flag = false;
                                break;
                            }
                        }
                        if(flag)GUILoader.loadTradeMenu(harbor1, harbor2);
                        else JOptionPane.showMessageDialog(ChoicePathMenu.this,"already has an active Sea-Road");



                    } else JOptionPane.showMessageDialog(ChoicePathMenu.this,"No proper selection");
                    break;
                default:
                    logger.error("ChoicePathMenu : Unknown state --> could not proceed with confirmation");
                    break;
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                GraphPoint graphPoint = path.get(0);
                path.clear();
                path.add(graphPoint);
                dashboard.setPath(path);
            }
            if(state == 1) {
                path.clear();
                dashboard.setPath(path);
            }
        }
    }

    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                if(!path.isEmpty()&&path.size()>1)path.remove(path.size()-1);
                dashboard.setPath(path);
            }
            else if(state == 1){
                path.remove(path.size()-1);
                dashboard.setPath(path);
            }
            else if(state == 2){
                harbor2 = null;
                harbor1 = null;
                dashboard.setHarbor2(null);
                dashboard.setHarbor1(null);

            }
        }
    }

    private class AutomaticListener implements ActionListener {
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
                    GraphPoint graphPoint1 = SearchInGraph.pointCollisionToMapGraphPoint(point);
                    if (e.getButton() == MouseEvent.BUTTON1 && graphPoint1 != null && !path.isEmpty()) {
                        if (path.get(path.size()-1).equals(graphPoint1)&&path.size()>1){
                            path.remove(graphPoint1);
                            dashboard.setPath(path);
                        }

                        else if (!path.get(path.size()-1).equals(harbor2.getGraphPosition())){
                            for(Map.Entry<String, GraphSegment> entry : path.get(path.size()-1).getSegmentHashMap().entrySet()){
                                if(entry.getValue().getGraphPoint().equals(graphPoint1)&& !path.contains(entry.getValue().getGraphPoint())){
                                    path.add(graphPoint1);
                                    dashboard.setPath(path);
                                }
                            }
                        }
                    }
                    break;

                case 1: //We're tracing a Path
                    GraphPoint graphPoint2 = SearchInGraph.pointCollisionToMapGraphPoint(point);
                    if (e.getButton() == MouseEvent.BUTTON1 && graphPoint2 != null) {
                        if(path.isEmpty()){
                            path.add(graphPoint2);
                            dashboard.setPath(path);
                        }
                        else if (path.get(path.size()-1).equals(graphPoint2)){
                            path.remove(graphPoint2);
                            dashboard.setPath(path);
                        }
                        else {
                            for (Map.Entry<String, GraphSegment> entry : path.get(path.size() - 1).getSegmentHashMap().entrySet()) {
                                if (entry.getValue().getGraphPoint().equals(graphPoint2) && !path.contains(entry.getValue().getGraphPoint())) {
                                    path.add(graphPoint2);
                                    dashboard.setPath(path);
                                }
                            }
                        }

                    }
                    break;

                case 2: //We're designating Harbor Object

                    if (faction == null) {
                        logger.error("Could not proceed --> faction is null");
                        break;
                    }
                    Harbor clickedHarbor = factionManager.getHarborManager().pointCollisionToMapHarbor(point);
                    if (clickedHarbor == null) {
                        break;
                    }

                    if (e.getButton() == MouseEvent.BUTTON1) /*Left Click*/ {
                        if (MapGame.getInstance().getPlayer().equals(faction)&&MapGame.getInstance().getPlayer().getLstHarbor().contains(clickedHarbor)) {
                            if(harbor1 == null){
                                harbor1 = clickedHarbor;
                                dashboard.setHarbor1(harbor1);
                            }
                            else if(harbor2 == null){
                                harbor2 = clickedHarbor;
                                dashboard.setHarbor2(harbor2);
                            }
                            else {
                                harbor1 = null;
                                harbor2 = null;
                                dashboard.setHarbor1(null);
                                dashboard.setHarbor2(null);
                            }
                        }
                        else {
                            if (MapGame.getInstance().getPlayer().getLstHarbor().contains(clickedHarbor)) {
                                harbor1 = clickedHarbor;
                                dashboard.setHarbor1(harbor1);
                            } else if (faction.getLstHarbor().contains(clickedHarbor)) {
                                harbor2 = clickedHarbor;
                                dashboard.setHarbor2(harbor2);
                            }
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3)  /*Right Click*/ {
                        if (clickedHarbor.equals(harbor1)) {
                            harbor1 = null;
                            dashboard.setHarbor1(null);
                        } else if (clickedHarbor.equals(harbor2)) {
                            harbor2 = null;
                            dashboard.setHarbor2(null);
                        }
                    } break;
                default:
                        logger.error("ChoicePathMenu : Unknown state --> could not proceed with logic");
            }
        }
    }

    private class GoBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            if(MapGame.getInstance().getPlayer().equals(faction))GUILoader.loadHarborMenu(MapGame.getInstance().getPlayer().getLstHarbor().get(0));
            else if(state == 0)GUILoader.loadFleetMenu(seaRoad);
            else if(state == 1)GUILoader.loadFleetMenu(fleet);
            else if(state == 2)GUILoader.loadRelationMenu(faction);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ThreadStop = true;
                if(MapGame.getInstance().getPlayer().equals(faction))GUILoader.loadHarborMenu(MapGame.getInstance().getPlayer().getLstHarbor().get(0));
                else if(state == 0)GUILoader.loadFleetMenu(seaRoad);
                else if(state == 1)GUILoader.loadFleetMenu(fleet);
                else if(state == 2)GUILoader.loadRelationMenu(faction);
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
