package gui.panel.menu;

import config.GameConfiguration;
import config.GameOptions;
import engine.data.MapGame;
import engine.battleengine.data.Battle;
import engine.battleengine.process.BattleManager;
import engine.data.entity.Entity;
import engine.data.Fleet;
import engine.data.entity.Harbor;
import engine.data.entity.boats.*;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;
import engine.data.trading.Resource;
import engine.process.manager.FactionManager;
import engine.data.trading.SeaRoad;
import engine.utilities.SearchInGraph;
import gui.PopUp;
import gui.panel.display.GameDisplay;
import gui.process.ListenerBehaviorManager;
import gui.utilities.GUILoader;
import gui.process.JComponentFactory;
import log.LoggerUtility;
import music.MusicManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static gui.MainGUI.getWindow;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author Kenan Ammad
 * @version 1.0
 */
public class MainGameMenu extends JPanel implements Runnable {

    private static Logger logger = LoggerUtility.getLogger(MainGameMenu.class);
    private boolean endFlag;
    private boolean fastPathMode;
    private HashMap<Object, JButton> mapObject;
    private Object currentObject;

    private JLayeredPane jLayeredPane;
    private JPopupMenu jPopupMenu;

    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jNorthATHPanel;
    private JPanel jNorthEastPanel;

    private JPanel jSouthATHPanel;
    private JPanel jSouthATHPanelCenter;
    private JPanel jSouthEastPanel;
    private JPanel jEastATHPanel;
    private JPanel jEastCenterChoice1CenterPanel;
    private JPanel jEastCenterChoice2CenterPanel;
    private JPanel jEastCenterChoice3CenterPanel1;
    private JPanel jEastCenterChoice3CenterPanel2;

    private JPanel jEastCenterCenterPanel;
    private JPanel jEastCenterPanelChoice1;
    private JPanel jEastCenterPanelChoice2;
    private JPanel jEastCenterPanelChoice3;


    private JButton showLeftMenuButton;
    private JButton hideLeftMenuButton;

    private JButton jButtonLeftMenu1;
    private JButton jButtonLeftMenu2;
    private JButton jButtonLeftMenu3;

    private JButton jButtonNorthMenu1;
    private JButton jButtonNorthMenu2;
    private JButton jButtonNorthMenu3;
    private JButton jButtonNorthMenu4;

    private JLabel jNorthATHLabel;

    private GameDisplay dashboard;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public MainGameMenu() {
        super();
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        jPopupMenu = JComponentFactory.voidPopupMenu();
        jPopupMenu.setLayout(new GridLayout(1, 0));
        jLayeredPane = JComponentFactory.JLayeredPane();
        jPanelATH = JComponentFactory.borderMenuPanel();
        jEastATHPanel = JComponentFactory.borderMenuPanel();
        dashboardJPanel = JComponentFactory.borderMenuPanel();
        jSouthATHPanel = JComponentFactory.borderMenuPanel();
        jSouthEastPanel = JComponentFactory.gridMenuPanel(2,2,0,GameConfiguration.BUTTON_SEPARATOR);
        jNorthATHPanel = JComponentFactory.borderMenuPanel();
        jNorthEastPanel = JComponentFactory.gridMenuPanel(1,4,0,0);
        jSouthATHPanelCenter = JComponentFactory.gridMenuPanel(2,0);
        jEastCenterChoice1CenterPanel = JComponentFactory.gridMenuPanel(0,2);
        jEastCenterChoice2CenterPanel = JComponentFactory.gridMenuPanel(0,2);
        jEastCenterChoice3CenterPanel1 = JComponentFactory.gridMenuPanel(0,2);
        jEastCenterChoice3CenterPanel2 = JComponentFactory.gridMenuPanel(0,2);
        jEastCenterCenterPanel = JComponentFactory.borderMenuPanel();
        jEastCenterPanelChoice1 = JComponentFactory.borderMenuPanel();
        jEastCenterPanelChoice2 = JComponentFactory.borderMenuPanel();
        jEastCenterPanelChoice3 = JComponentFactory.borderMenuPanel();
        jButtonLeftMenu1 = JComponentFactory.menuButton("Boat",new ShowMenu(jEastCenterPanelChoice1,jEastCenterCenterPanel));
        jButtonLeftMenu2 = JComponentFactory.menuButton("Harbor",new ShowMenu(jEastCenterPanelChoice2,jEastCenterCenterPanel));
        jButtonLeftMenu3 = JComponentFactory.menuButton("Fleet",new ShowMenu(jEastCenterPanelChoice3,jEastCenterCenterPanel));
        jNorthATHLabel = JComponentFactory.title("    "+MapGame.getInstance().getPlayer().getCurrency().getName()+" : "+MapGame.getInstance().getPlayer().getAmountCurrency()+"   Harbor : "+MapGame.getInstance().getPlayer().getLstHarbor().size()+"/"+MapGame.getInstance().getLstHarbor().size());
        dashboard = new GameDisplay();
        mapObject = new HashMap<>();
        fastPathMode = false;
        endFlag = false;

        //Window arrangement
        JPanel jEastPanel = JComponentFactory.borderMenuPanel();
        JPanel jEastWestPanel = JComponentFactory.borderMenuPanel();
        JPanel jEastButtonPanel = JComponentFactory.borderMenuPanel();
        JPanel jEastCenterPanel = JComponentFactory.borderMenuPanel();
        JPanel jEastCenterNorthPanel = JComponentFactory.gridMenuPanel(1,0,0,0,jButtonLeftMenu1,jButtonLeftMenu2,jButtonLeftMenu3);

        jButtonNorthMenu1 = JComponentFactory.menuButton("⏯",new FlipTimeListener());
        jButtonNorthMenu2 = JComponentFactory.menuButton(">",new SetSpeedBoostListener(1));
        jButtonNorthMenu3 = JComponentFactory.menuButton(">>",new SetSpeedBoostListener(4));
        jButtonNorthMenu4 = JComponentFactory.menuButton(">>>",new SetSpeedBoostListener(8));
        switch (GameOptions.getInstance().getSpeedBoost()) {
            case 1 :{
                jButtonNorthMenu2.setEnabled(false);
                break;
            }
            case 4 :{
                jButtonNorthMenu3.setEnabled(false);
                break;
            }
            case 8 :{
                jButtonNorthMenu4.setEnabled(false);
                break;
            }
            default : {
            }
        }
        jSouthEastPanel.add(JComponentFactory.menuButton("Harbor Menu",new LoadHarborMenuListener()));
        jSouthEastPanel.add(JComponentFactory.menuButton("Fleet Menu",new LoadFleetMenuListener()));
        jSouthEastPanel.add(JComponentFactory.menuButton("Faction Menu",new LoadFactionMenuListener()));
        jSouthEastPanel.add(JComponentFactory.menuButton("Pause Menu",new LoadPauseMenuListener()));

        JScrollPane jScrollPane1 = JComponentFactory.ScrollPaneMenuPanel(jEastCenterChoice1CenterPanel);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice1.add(jScrollPane1);

        JScrollPane jScrollPane2 = JComponentFactory.ScrollPaneMenuPanel(jEastCenterChoice2CenterPanel);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice2.add(jScrollPane2);

        JPanel jEastCenterChoice3CenterPanel = JComponentFactory.boxMenuPanel(BoxLayout.Y_AXIS);
        jEastCenterChoice3CenterPanel.add(jEastCenterChoice3CenterPanel1);
        jEastCenterChoice3CenterPanel.add(jEastCenterChoice3CenterPanel2);
        JScrollPane jScrollPane3 = JComponentFactory.ScrollPaneMenuPanel(jEastCenterChoice3CenterPanel);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jEastCenterPanelChoice3.add(jScrollPane3);

        jNorthEastPanel.add(jButtonNorthMenu1);
        jNorthEastPanel.add(jButtonNorthMenu2);
        jNorthEastPanel.add(jButtonNorthMenu3);
        jNorthEastPanel.add(jButtonNorthMenu4);

        showLeftMenuButton = JComponentFactory.menuButton("<", new ShowMenu(jEastPanel,jEastATHPanel));
        jEastButtonPanel.add(showLeftMenuButton,BorderLayout.NORTH);
        hideLeftMenuButton = JComponentFactory.menuButton(">", new ShowMenu(jEastButtonPanel,jEastATHPanel));

        jEastATHPanel.setOpaque(false);
        jPanelATH.setOpaque(false);
        jEastPanel.setOpaque(false);
        jEastButtonPanel.setOpaque(false);
        jEastWestPanel.setOpaque(false);
        jSouthATHPanel.setOpaque(false);

        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastCenterPanel.add(jEastCenterCenterPanel,BorderLayout.CENTER);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);

        dashboardJPanel.add(dashboard,BorderLayout.CENTER);

        jNorthATHPanel.add(jNorthEastPanel,BorderLayout.EAST);
        jNorthATHPanel.add(jNorthATHLabel,BorderLayout.CENTER);

        jEastPanel.add(jEastCenterPanel,BorderLayout.CENTER);
        jEastPanel.add(jEastWestPanel,BorderLayout.WEST);
        jEastCenterPanel.add(jEastCenterNorthPanel,BorderLayout.NORTH);
        jEastCenterPanel.add(jEastCenterCenterPanel,BorderLayout.CENTER);
        jEastCenterCenterPanel.add(jEastCenterPanelChoice1,BorderLayout.CENTER);
        jEastWestPanel.add(hideLeftMenuButton,BorderLayout.NORTH);
        jEastATHPanel.add(jEastButtonPanel,BorderLayout.CENTER);

        jSouthATHPanel.add(jSouthEastPanel,BorderLayout.EAST);
        jSouthATHPanel.add(jSouthATHPanelCenter,BorderLayout.CENTER);

        jPanelATH.add(jNorthATHPanel,BorderLayout.NORTH);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);
        jPanelATH.add(jEastATHPanel,BorderLayout.EAST);
        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        //color
        jEastCenterPanel.setBackground(Color.DARK_GRAY);
        jSouthEastPanel.setBackground(Color.GRAY);
        jSouthATHPanelCenter.setBackground(new Color(64, 64, 64,100));
        jNorthATHPanel.setBackground(new Color(64, 64, 64,100));
        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);
        jEastCenterChoice1CenterPanel.setBackground(Color.GRAY);
        jEastCenterChoice2CenterPanel.setBackground(Color.GRAY);
        jEastCenterChoice3CenterPanel1.setBackground(Color.GRAY);
        jEastCenterChoice3CenterPanel2.setBackground(Color.GRAY);
        jNorthATHLabel.setForeground(Color.lightGray);
        jNorthATHLabel.setFont(new Font( "Noto Sans Display", Font.BOLD, 40));

        this.add(jLayeredPane);
        this.addMouseListener(new MouseListener());
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        elementInPanelUpdate();
        sizeUpdate();
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

        jPopupMenu.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.08), (int) (getWindow().getHeight()*0.03)));
        jEastATHPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.20),getWindow().getHeight()));
        if (jEastATHPanel.isAncestorOf(showLeftMenuButton))jEastATHPanel.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        showLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        hideLeftMenuButton.setPreferredSize(new Dimension((int) Math.max(26,getWindow().getHeight()*0.04), (int) Math.max(26,getWindow().getHeight()*0.04)));
        jEastCenterChoice1CenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08* MapGame.getInstance().getPlayer().getLstBoat().size()))));
        jEastCenterChoice2CenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.08* MapGame.getInstance().getPlayer().getLstHarbor().size()))));
        jEastCenterChoice3CenterPanel1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.1* MapGame.getInstance().getPlayer().getLstFleet().size()))));
        jEastCenterChoice3CenterPanel2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*(0.1* MapGame.getInstance().getPlayer().getLstSeaRouts().size()))));
        jEastCenterChoice3CenterPanel1.setBorder(new EmptyBorder(0,0, (int) (getWindow().getHeight()*0.01),0));
        jLayeredPane.revalidate();
        jLayeredPane.repaint();
    }

    private void elementInPanelUpdate() {
        jEastCenterChoice1CenterPanel.removeAll();
        jEastCenterChoice2CenterPanel.removeAll();
        jEastCenterChoice3CenterPanel1.removeAll();
        jEastCenterChoice3CenterPanel2.removeAll();
        JButton tmp;
        for (Boat boat : MapGame.getInstance().getPlayer().getLstBoat()){
            tmp = JComponentFactory.menuButton(boat,new ButtonObjectListener(boat));
            mapObject.put(boat,tmp);
            jEastCenterChoice1CenterPanel.add(tmp);

        }
        for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
            tmp = JComponentFactory.menuButton(harbor,new ButtonObjectListener(harbor));
            mapObject.put(harbor,tmp);
            jEastCenterChoice2CenterPanel.add(tmp);
        }
        for (Fleet fleet : MapGame.getInstance().getPlayer().getLstFleet()){
            tmp = JComponentFactory.menuButton(fleet,new ButtonObjectListener(fleet));
            mapObject.put(fleet,tmp);
            jEastCenterChoice3CenterPanel1.add(tmp);
        }
        for (SeaRoad seaRoad : MapGame.getInstance().getPlayer().getLstSeaRouts()){
            tmp = JComponentFactory.menuButton(seaRoad,new ButtonObjectListener(seaRoad));
            mapObject.put(seaRoad,tmp);
            jEastCenterChoice3CenterPanel2.add(tmp);
        }
        if(currentObject!= null) changeCurrentJButton(currentObject);
        sizeUpdate();
    }

    private void needUpdate(){
        if(currentObject!= null){
            if (currentObject instanceof Harbor && !MapGame.getInstance().getPlayer().getLstHarbor().contains((Harbor)currentObject)){
                changeCurrentJButton(null);
            }
            else if (currentObject instanceof SeaRoad && !MapGame.getInstance().getPlayer().getLstSeaRouts().contains((SeaRoad) currentObject)){
                changeCurrentJButton(null);
            }
        }
        elementInPanelUpdate();
    }

    private void changeCurrentJButton(Object object){
        JButton currentJButton = mapObject.get(currentObject);
        if(currentJButton!=null) currentJButton.setBackground(Color.DARK_GRAY);
        currentJButton = mapObject.get(object);
        if(currentJButton!=null) currentJButton.setBackground(new Color(125, 130, 200));
        currentObject = object;
        fastPathMode = false;
        dashboard.setFastPathMode(false);
        dashboard.setCurrentObject(currentObject);
        repaintUpdate();
    }

    private void repaintUpdate(){
        jSouthATHPanelCenter.removeAll();
        jSouthATHPanelCenter.setLayout(new GridLayout(2,0));
        jNorthATHLabel.setText("    "+MapGame.getInstance().getPlayer().getCurrency().getName()+" : "+MapGame.getInstance().getPlayer().getAmountCurrency()+"   Harbor : "+MapGame.getInstance().getPlayer().getLstHarbor().size()+"/"+MapGame.getInstance().getLstHarbor().size());
        if(currentObject!= null){
            if (currentObject instanceof Boat){
                Boat currentBoat = (Boat) currentObject;
                JButton fastPath = JComponentFactory.menuButton("Fast path",new FastPathListenerListener());
                JButton cancelChase = JComponentFactory.menuButton("Cancel the Chase",new CancelChaseListener(currentBoat));
                cancelChase.setEnabled(false);
                if(MapGame.getInstance().getHunterPreyHashMap().containsKey(currentBoat))cancelChase.setEnabled(true);
                JPanel tmpFastPath = JComponentFactory.borderMenuPanel();
                tmpFastPath.setOpaque(false);
                JPanel tmpCancelChase = JComponentFactory.borderMenuPanel();
                tmpCancelChase.setOpaque(false);


                Font font = new Font( "Noto Sans Display", Font.BOLD, 30);
                JLabel tmpLabel1 = JComponentFactory.menuLabel("Level : " +currentBoat.getLevel());
                tmpLabel1.setHorizontalAlignment(SwingConstants.CENTER);
                tmpLabel1.setFont(font);
                tmpLabel1.setForeground(Color.white);
                JLabel tmpLabel2 = JComponentFactory.menuLabel("Health : "+currentBoat.getCurrentHp()+"/"+currentBoat.getMaxHp());
                tmpLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                tmpLabel2.setFont(font);
                tmpLabel2.setForeground(Color.white);

                tmpFastPath.add(fastPath,BorderLayout.CENTER);
                tmpFastPath.setBorder(new EmptyBorder((int)(getWindow().getHeight()*0.01),0,GameConfiguration.BUTTON_SEPARATOR/2,(int)(getWindow().getWidth()*0.22)));
                tmpCancelChase.add(cancelChase,BorderLayout.CENTER);
                tmpCancelChase.setBorder(new EmptyBorder(GameConfiguration.BUTTON_SEPARATOR/2,0,(int)(getWindow().getHeight()*0.01),(int)(getWindow().getWidth()*0.22)));

                jSouthATHPanelCenter.add(tmpLabel1);
                jSouthATHPanelCenter.add(tmpFastPath);
                jSouthATHPanelCenter.add(tmpLabel2);
                jSouthATHPanelCenter.add(tmpCancelChase);
            }
            else if (currentObject instanceof Harbor){
                Harbor currentHarbor = (Harbor) currentObject;
                JLabel tmpLabel;
                Font font = new Font( "Noto Sans Display", Font.BOLD, 30);
                int nb = 0;
                for (Resource resource : currentHarbor.getGenerator().keySet())nb+=currentHarbor.getGenerator().get(resource)[1];
                String[] lstString = {"Level : " +currentHarbor.getLevel(),"Health : "+currentHarbor.getCurrentHp()+"/"+currentHarbor.getMaxHp(),"Harbor Generator : "+nb+"/"+currentHarbor.getLevel(),"Harbor Resource : "+currentHarbor.getInventory().toString()};

                for (String string : lstString){
                    tmpLabel = JComponentFactory.menuLabel(string);
                    tmpLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    tmpLabel.setFont(font);
                    tmpLabel.setForeground(Color.white);
                    jSouthATHPanelCenter.add(tmpLabel);
                }
            }
            else if (currentObject instanceof Fleet){
                Fleet currentFleet = (Fleet) currentObject;
                jSouthATHPanelCenter.setLayout(new GridLayout(1,1));
                JPanel tmpBorderMenuPanel = JComponentFactory.gridMenuPanel(1,2);
                JPanel tmpFastPath = JComponentFactory.borderMenuPanel();
                tmpFastPath.setOpaque(false);
                tmpBorderMenuPanel.setOpaque(false);


                JLabel tmpLabel;
                Font font = new Font( "Noto Sans Display", Font.BOLD, 30);
                tmpLabel = JComponentFactory.menuLabel("Number of Boats : " +currentFleet.getArrayListBoat().size());
                tmpLabel.setHorizontalAlignment(SwingConstants.CENTER);
                tmpLabel.setFont(font);
                tmpLabel.setForeground(Color.white);

                JButton fastPath = JComponentFactory.menuButton("Fast path",new FastPathListenerListener());
                fastPath.setEnabled(true);
                if(FactionManager.getInstance().getMySeaRoad(currentFleet) != null){
                    fastPath.setEnabled(false);
                    fastPath.setText("Fast path ( Is Combine with Sea-Road )");
                }
                tmpFastPath.add(fastPath,BorderLayout.CENTER);
                tmpFastPath.setBorder(new EmptyBorder((int)(getWindow().getHeight()*0.03),0,(int)(getWindow().getHeight()*0.03),(int)(getWindow().getWidth()*0.22)));

                tmpBorderMenuPanel.add(tmpLabel);
                tmpBorderMenuPanel.add(tmpFastPath);
                jSouthATHPanelCenter.add(tmpBorderMenuPanel);
            }
            else if (currentObject instanceof SeaRoad){
                SeaRoad currentSeaRoad = (SeaRoad) currentObject;
                JLabel tmpLabel;
                Font font = new Font( "Noto Sans Display", Font.BOLD, 30);
                String[] lstString = {"Remaining Time : "+currentSeaRoad.getStringTimer(),currentSeaRoad.getSelection().getKey().getName()+" ( "+currentSeaRoad.getSelection().getValue()+" ) / "+currentSeaRoad.getDemand().getKey().getName()+" ( "+currentSeaRoad.getDemand().getValue()+" )"};

                for (String string : lstString){
                    tmpLabel = JComponentFactory.menuLabel(string);
                    tmpLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    tmpLabel.setFont(font);
                    tmpLabel.setForeground(Color.white);
                    jSouthATHPanelCenter.add(tmpLabel);
                }
            }

        }
        sizeUpdate();
    }

    private void showPopupMenu(int x, int y, Entity entity){
        jPopupMenu.removeAll();
        JButton tmp;
        if(entity instanceof Boat) {
            tmp = JComponentFactory.menuButton("attack", new SetChaseListener(entity));
            jPopupMenu.add(tmp);
            if(!(currentObject != null && currentObject instanceof Boat)||((Boat)currentObject).getVisionRadius() < ((Boat)currentObject).getPosition().distance(entity.getPosition())){
                tmp.setEnabled(false);
            }
            tmp = JComponentFactory.menuButton("faction", new RelationListener(entity));
            jPopupMenu.add(tmp);
            jPopupMenu.show(jPanelATH,x,y);
        }
        else if (entity instanceof Harbor && !entity.getColor().isEmpty() && MapGame.getInstance().getPlayer().getLstHarbor().contains((Harbor) entity)) {
            tmp = JComponentFactory.menuButton("Go in the Harbor", new ReturnInHarborListener((Harbor) entity));
            if(currentObject != null && currentObject instanceof Boat && FactionManager.getInstance().doIHaveFleet((Boat) currentObject))tmp.setEnabled(false);
            jPopupMenu.add(tmp);
            jPopupMenu.show(jPanelATH,x,y);
        }
        else if (entity instanceof Harbor && !entity.getColor().isEmpty()) {
            tmp = JComponentFactory.menuButton("faction", new RelationListener(entity));
            jPopupMenu.add(tmp);

            tmp = JComponentFactory.menuButton("attack", new SetChaseListener(entity));
            tmp.setEnabled(false);
            if(currentObject != null && currentObject instanceof Boat && !FactionManager.getInstance().doIHaveFleet((Boat)currentObject)){
                tmp.setEnabled(true);
            }
            else if(currentObject != null && currentObject instanceof Fleet){
                tmp.setEnabled(true);
            }
            jPopupMenu.add(tmp);
            jPopupMenu.show(jPanelATH,x,y);
        }
    }

    private void wantFight(Battle battle,boolean ConfirmDialog ,boolean isFakeBattle){
        MapGame.getInstance().setTimeStop(true);
        if(!battle.getFactionA().equals(MapGame.getInstance().getPlayer())&&!battle.getFactionB().equals(MapGame.getInstance().getPlayer())){
            if(!isFakeBattle){
                BattleManager.fakeBattle(battle);
                MapGame.getInstance().setTimeStop(false);
            }
            else if(JOptionPane.showConfirmDialog(MainGameMenu.this,"Do you want to watch the battle of "+battle.getFactionA().getName()+" VS "+battle.getFactionB().getName(),"confirmation Battle",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                FactionManager.getInstance().modifyRelationship(MapGame.getInstance().getPlayer(),battle.getFactionB(),-15);
                ThreadStop = true;
                MusicManager.getInstance().getMusicPlayer(4).getClip().setFramePosition(0);
                GUILoader.loadCombat(battle);
            }
            else {
                BattleManager.fakeBattle(battle);
                MapGame.getInstance().setTimeStop(false);
            }
        }
        else if(!ConfirmDialog){
            if(MapGame.getInstance().isGodMode()){
                if(JOptionPane.showConfirmDialog(MainGameMenu.this,"it's battle time ! (GodMode) ","confirmation Battle",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                    FactionManager.getInstance().modifyRelationship(MapGame.getInstance().getPlayer(),battle.getFactionB(),-15);
                    ThreadStop = true;
                    MusicManager.getInstance().getMusicPlayer(4).getClip().setFramePosition(0);
                    GUILoader.loadCombat(battle);
                }
                else {
                    BattleManager.fakeBattle(battle);
                    MapGame.getInstance().setTimeStop(false);
                    elementInPanelUpdate();
                    if(currentObject != null && currentObject instanceof Boat && !MapGame.getInstance().getPlayer().getLstBoat().contains((Boat) currentObject)){
                        changeCurrentJButton(null);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(MainGameMenu.this, "it's battle time !", "Battle", JOptionPane.PLAIN_MESSAGE);
                ThreadStop = true;
                MusicManager.getInstance().getMusicPlayer(4).getClip().setFramePosition(0);
                GUILoader.loadCombat(battle);
            }
        }
        else if(JOptionPane.showConfirmDialog(MainGameMenu.this,"Do you want to start a battle ?","confirmation Battle",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
            FactionManager.getInstance().modifyRelationship(MapGame.getInstance().getPlayer(),battle.getFactionB(),-15);
            ThreadStop = true;
            MusicManager.getInstance().getMusicPlayer(4).getClip().setFramePosition(0);
            GUILoader.loadCombat(battle);
        }
        else MapGame.getInstance().setTimeStop(false);
    }

    private class ShowMenu implements ActionListener {
        private final JPanel jPanel1;
        private final JPanel jPanel2;

        public ShowMenu(JPanel jPanel1, JPanel jPanel2){
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

    private class RelationListener implements ActionListener {
        private final Entity entity;

        public RelationListener(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            jPopupMenu.setVisible(false);
            GUILoader.loadRelationMenu(FactionManager.getInstance().getMyFaction(entity.getColor()));
        }
    }

    private class SetChaseListener implements ActionListener {
        private final Object object;

        public SetChaseListener(Object object) {
            this.object = object;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            jPopupMenu.setVisible(false);
            if(object instanceof Boat)FactionManager.getInstance().chaseBoat((Boat) currentObject, (Boat) object);
            else if(object instanceof Harbor){
                if((FactionManager.getInstance().getMyFaction(((Harbor) object).getColor()).getRelationship(MapGame.getInstance().getPlayer()) <= -100) || JOptionPane.showConfirmDialog(MainGameMenu.this,"Do you want to declare war to "+FactionManager.getInstance().getMyFaction(((Harbor) object).getName()).getName()+"? This decision cannot be reversed","confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    FactionManager.getInstance().modifyRelationship(MapGame.getInstance().getPlayer(),FactionManager.getInstance().getMyFaction(((Harbor) object).getColor()),-100);
                    if (currentObject instanceof Boat) {
                        Boat boat = (Boat) currentObject;
                        Harbor harbor = FactionManager.getInstance().getMyHarbor(boat);
                        if(harbor != null && harbor.getHashMapBoat().get(boat)){
                            FactionManager.getInstance().getHarborManager().removeBoatInHarbor(harbor,boat);
                        }
                        else boat.setContinuePath(false);
                        boat.setPath(SearchInGraph.findPath(boat, ((Harbor) object).getGraphPosition()));
                    } else if (currentObject instanceof Fleet) {
                        SeaRoad seaRoad = FactionManager.getInstance().getMySeaRoad((Fleet) currentObject);
                        if (seaRoad != null)
                            FactionManager.getInstance().getSeaRoadManager().setNewFleet(seaRoad, new Fleet());
                        FactionManager.getInstance().getFleetManager().setNewPath((Fleet) currentObject, SearchInGraph.findPath(((Harbor) object).getGraphPosition(), ((Harbor) object).getGraphPosition()), false);
                    }
                }
            }
            repaintUpdate();
        }
    }

    private class SetSpeedBoostListener implements ActionListener {
        private final int value;

        public SetSpeedBoostListener(int value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameOptions.getInstance().setSpeedBoost(value);
            jButtonNorthMenu1.setEnabled(true);
            jButtonNorthMenu2.setEnabled(true);
            jButtonNorthMenu3.setEnabled(true);
            jButtonNorthMenu4.setEnabled(true);
            ((JButton)e.getSource()).setEnabled(false);
        }
    }

    private class ReturnInHarborListener implements ActionListener {
        private final Harbor harbor;

        private ReturnInHarborListener(Harbor harbor) {
            this.harbor = harbor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentObject != null && currentObject instanceof Boat){
                FactionManager.getInstance().getHarborManager().addBoatInHarbor(harbor, (Boat) currentObject);
            }
            if(currentObject != null && currentObject instanceof Fleet){
                FactionManager.getInstance().getHarborManager().addFleetInHarbor(harbor, (Fleet) currentObject);
            }
        }

    }

    private class LoadPauseMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            GUILoader.loadPauseMenu(GameConfiguration.ROOT_MAIN_GAME,null);
        }
    }

    private class LoadFactionMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Faction faction : MapGame.getInstance().getLstBotFaction()){
                if((!faction.getLstHarbor().isEmpty() || !faction.getLstBoat().isEmpty())&&!MapGame.getInstance().getLstBotFaction().get(0).equals(MapGame.getInstance().getPirate())){
                    ThreadStop = true;
                    GUILoader.loadRelationMenu(MapGame.getInstance().getLstBotFaction().get(0));
                    break;
                }
            }
        }
    }

    private class LoadFleetMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            if(currentObject != null && currentObject instanceof Fleet)GUILoader.loadFleetMenu((Fleet) currentObject);
            else if(currentObject != null && currentObject instanceof SeaRoad)GUILoader.loadFleetMenu((SeaRoad) currentObject);
            else GUILoader.loadFleetMenu((Fleet) null);
        }
    }

    private class LoadHarborMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ThreadStop = true;
            if(currentObject != null && currentObject instanceof Harbor){
                GUILoader.loadHarborMenu((Harbor) currentObject);
            }
            else if(!MapGame.getInstance().getPlayer().getLstHarbor().isEmpty())GUILoader.loadHarborMenu(MapGame.getInstance().getPlayer().getLstHarbor().get(0));
        }
    }

    private class FastPathListenerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fastPathMode = !fastPathMode;
            dashboard.setFastPathMode(fastPathMode);
        }
    }

    private class FlipTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setTimeStop(!MapGame.getInstance().isTimeStop());
        }
    }

    private class CancelChaseListener implements ActionListener {
        private Boat boat;

        private CancelChaseListener(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(boat!=null){
                MapGame.getInstance().getHunterPreyHashMap().remove(boat);
                ((JButton)e.getSource()).setEnabled(false);
            }
        }
    }

    private class ButtonObjectListener implements ActionListener {
        private final Object object;

        public ButtonObjectListener(Object object) {
            this.object = object;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            changeCurrentJButton(object);
            sizeUpdate();
        }
    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Point point = ListenerBehaviorManager.create().clickLogic(MainGameMenu.this, e.getPoint());
            Boat boat = FactionManager.getInstance().getBoatManager().pointCollisionToMapBoat(point);
            Harbor harbor = FactionManager.getInstance().getHarborManager().pointCollisionToMapHarbor(point);
            if(fastPathMode){
                fastPathMode = false;
                dashboard.setFastPathMode(false);
                GraphPoint graphPoint = SearchInGraph.pointCollisionToMapGraphPoint(point);
                if (e.getButton() == MouseEvent.BUTTON1 && graphPoint != null) {
                    if(currentObject != null && currentObject instanceof Boat){
                        Harbor tmpHarbor = FactionManager.getInstance().getMyHarbor((Boat) currentObject);
                        if(tmpHarbor != null && tmpHarbor.getHashMapBoat().get((Boat) currentObject)){
                            FactionManager.getInstance().getHarborManager().removeBoatInHarbor(tmpHarbor,(Boat) currentObject);
                        }
                        ((Boat)currentObject).setContinuePath(false);
                        ((Boat)currentObject).setPath(SearchInGraph.findPath(((Boat)currentObject), graphPoint));
                    }
                    if(currentObject != null && currentObject instanceof Fleet){
                        FactionManager.getInstance().getFleetManager().setNewPath(((Fleet)currentObject),new ArrayList<>(Collections.singleton(graphPoint)),false);
                    }
                }
            }
            else if(harbor != null){
                if(MapGame.getInstance().getPlayer().getLstHarbor().contains(harbor)){
                    if(e.getButton() == MouseEvent.BUTTON3  && currentObject != null && currentObject instanceof Boat && !harbor.getHashMapBoat().containsKey((Boat) currentObject)){
                        showPopupMenu(e.getX(),e.getY(),harbor);
                    }
                    else if(e.getButton() == MouseEvent.BUTTON3  && currentObject != null && currentObject instanceof Fleet){
                        showPopupMenu(e.getX(),e.getY(),harbor);
                    }
                    else {
                        jEastCenterCenterPanel.removeAll();
                        jEastCenterCenterPanel.add(jEastCenterPanelChoice2);
                        changeCurrentJButton(harbor);
                    }
                }
                else if(e.getButton() == MouseEvent.BUTTON1){
                    showPopupMenu(e.getX(),e.getY(),harbor);
                }
            }
            else if(boat != null){
                if(MapGame.getInstance().getPlayer().getLstBoat().contains(boat)){
                    jEastCenterCenterPanel.removeAll();
                    jEastCenterCenterPanel.add(jEastCenterPanelChoice1);
                    changeCurrentJButton(boat);
                }
                else if(e.getButton() == MouseEvent.BUTTON3 && MapGame.getInstance().getPlayer().getVision().contains(boat)){
                    showPopupMenu(e.getX(),e.getY(),boat);
                }
            }
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
                ThreadStop = true;
                if(jPopupMenu != null)jPopupMenu.setVisible(false);
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_MAIN_GAME,null);
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
            AbstractMap.SimpleEntry<Battle, Boolean[]> tmp;
            try {
                Thread.sleep((long) GameConfiguration.GAME_SPEED/ GameOptions.getInstance().getSpeedBoost());

            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            if (!MapGame.getInstance().isTimeStop()){
                FactionManager.getInstance().nextRound();
                if(FactionManager.getInstance().needUpdate())needUpdate();
                else if((int)(MapGame.getInstance().getTime()*10)%10 == 1)repaintUpdate();
                tmp = FactionManager.getInstance().needBattle();
                if(tmp != null)wantFight(tmp.getKey(),tmp.getValue()[0],tmp.getValue()[1]);
                if(!endFlag&&FactionManager.getInstance().playerWin()){
                    endFlag = true;
                    JOptionPane.showMessageDialog(MainGameMenu.this,"You Win GG");
                }
                else if(!endFlag&&FactionManager.getInstance().playerLose()){
                    JOptionPane.showMessageDialog(MainGameMenu.this,"You Lose (╯°□°)╯︵ ┻━┻");
                    endFlag = true;
                }
                MapGame.getInstance().addTime(((double)GameConfiguration.GAME_SPEED)/1000);
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
