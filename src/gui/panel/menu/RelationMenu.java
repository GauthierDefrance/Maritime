package gui.panel.menu;

import config.GameConfiguration;
import engine.data.MapGame;
import engine.data.faction.Faction;
import engine.process.manager.FactionManager;
import gui.utilities.ImageStock;
import gui.process.JComponentFactory;
import gui.utilities.GUILoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

/**
 * @author Kenan Ammad
 * @version 1.0
 */
public class RelationMenu extends JPanel {
    private Faction activeFaction;

    private JPanel factionStats;
    private JPanel jPanelCenter;
    private JPanel jButtonPanel;
    private JPanel gridPanel;

    private JScrollPane jScrollPane;

    private JButton goBackButton;
    private JButton declareWar;
    private JButton activeFactionButton;

    private JLabel picture;

    public RelationMenu( Faction activeFaction ) {
        super();
        this.activeFaction = activeFaction;
        init();
    }

    //Initialization

    private void init() {
        this.setLayout(new GridLayout(1, 3, GameConfiguration.BUTTON_SEPARATOR,GameConfiguration.BUTTON_SEPARATOR));

        factionStats = JComponentFactory.gridMenuPanel(0, 1);
        jPanelCenter = JComponentFactory.borderMenuPanel();
        goBackButton = JComponentFactory.menuButton("Go back", new GoBackButtonListener());
        declareWar = JComponentFactory.menuButton("Declare War", new ProvokeListener());
        jButtonPanel = JComponentFactory.borderMenuPanel();
        picture = JComponentFactory.title("");

        JPanel jPanelWest = JComponentFactory.borderMenuPanel();
        gridPanel = JComponentFactory.gridMenuPanel(0,1);
        JPanel gridPanelTmp = JComponentFactory.voidPanel();
        gridPanelTmp.add(gridPanel);

        jScrollPane = JComponentFactory.ScrollPaneMenuPanel(gridPanelTmp);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel jScrollPaneTmp = JComponentFactory.flowMenuPanel();
        jScrollPaneTmp.add(jScrollPane);
        jPanelWest.add(jScrollPaneTmp,BorderLayout.WEST);


        JButton jButtonTrade = JComponentFactory.menuButton("Propose Trade", new TradeListener());
        jButtonPanel.add(jButtonTrade,BorderLayout.CENTER);

        JPanel factionStatsTmp = JComponentFactory.voidPanel();
        factionStatsTmp.add(factionStats);

        JPanel jPanelEast = JComponentFactory.borderMenuPanel();
        JPanel declareWarTmp = JComponentFactory.voidPanel();

        declareWarTmp.add(declareWar);
        jPanelEast.add(factionStatsTmp, BorderLayout.CENTER);
        jPanelEast.add(declareWarTmp, BorderLayout.SOUTH);


        this.add(jPanelWest);
        this.add(jPanelCenter);
        this.add(jPanelEast);

        this.setBackground(Color.gray);
        factionStatsTmp.setBackground(Color.gray);
        declareWarTmp.setBackground(Color.gray);
        jScrollPaneTmp.setBackground(Color.gray);
        gridPanelTmp.setBackground(Color.gray);
        gridPanel.setBackground(Color.gray);
        jPanelCenter.setBackground(Color.gray);
        jPanelWest.setBackground(Color.gray);

        factionStats.setBackground(Color.lightGray);
        jButtonPanel.setBackground(Color.lightGray);
        jScrollPane.setBackground(Color.lightGray);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        factionUpdate();
    }

    private void sizeUpdate() {

        factionStats.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.3), (int) (getWindow().getHeight()*0.7)));
        factionStats.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.01),(int) (getWindow().getHeight()*0.015), (int) (getWindow().getHeight()*0.01),(int) (getWindow().getHeight()*0.015)));

        gridPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.03* (MapGame.getInstance().getLstFaction().size())))));
        jScrollPane.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));
        jScrollPane.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.015),(int) (getWindow().getHeight()*0.015), (int) (getWindow().getHeight()*0.015),(int) (getWindow().getHeight()*0.015)));

        goBackButton.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.15), (int) (getWindow().getHeight()*0.07)));
        declareWar.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.1), (int) (getWindow().getHeight()*0.08)));
        jButtonPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.08)));
        jButtonPanel.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.01),(int) (getWindow().getHeight()*0.015), (int) (getWindow().getHeight()*0.01),(int) (getWindow().getHeight()*0.015)));
        picture.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.1),0, 0,0));

        getWindow().revalidate();
        getWindow().repaint();
    }

    private void elementInPanelUpdate() {
        statDisplay();
        gridPanel.removeAll();
        JButton tmp;
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            if(!(faction.equals(MapGame.getInstance().getPlayer())||faction.equals(MapGame.getInstance().getPirate()))) {
                tmp = JComponentFactory.menuButton(faction.getName(),new buttonObjectListener(faction));
                tmp.setBackground(ImageStock.mixColor(ImageStock.colorChoice(faction.getColor(),255),Color.gray,0.7));
                tmp.setForeground(Color.white);
                tmp.setBorderPainted(false);
                gridPanel.add(tmp);
                if(activeFaction.equals(faction))ChangeCurrentJButton(tmp);
                if(faction.getLstHarbor().isEmpty()&&faction.getLstBoat().isEmpty()){
                    tmp.setEnabled(false);
                    tmp.setText("<html><strike>"+faction.getName()+"</strike></html>");
                }
            }

        }

    }

    private void factionUpdate(){
        jPanelCenter.removeAll();

        JPanel tmpGoBackButtonPanel = JComponentFactory.borderMenuPanel();
        JPanel goBackButtonPanel = JComponentFactory.voidPanel();
        goBackButtonPanel.add(goBackButton);
        goBackButtonPanel.setOpaque(false);
        tmpGoBackButtonPanel.setOpaque(false);
        tmpGoBackButtonPanel.add(goBackButtonPanel,BorderLayout.SOUTH);
        picture = JComponentFactory.title("");
        if(activeFaction != null){
            picture = JComponentFactory.title(activeFaction.getName());
            picture.setIcon( new ImageIcon(ImageStock.getImage(activeFaction)));
        }
        picture.setHorizontalAlignment(SwingConstants.CENTER);
        picture.setHorizontalTextPosition(SwingConstants.CENTER);
        picture.setVerticalTextPosition(SwingConstants.BOTTOM);

        JPanel tmpPanelCenter = JComponentFactory.borderMenuPanel();
        tmpPanelCenter.setOpaque(false);
        tmpPanelCenter.add(picture,BorderLayout.CENTER);
        jPanelCenter.add(tmpPanelCenter,BorderLayout.NORTH);
        jPanelCenter.add(tmpGoBackButtonPanel,BorderLayout.CENTER);
        if(activeFaction.getRelationship(MapGame.getInstance().getPlayer()) <= GameConfiguration.WAR_THRESHOLD){
            declareWar.setText("it's war");
            declareWar.setEnabled(false);
        }
        else {
            declareWar.setText("Declare War");
            declareWar.setEnabled(true);
            JPanel tmpPanel = JComponentFactory.flowMenuPanel();
            tmpPanel.setBackground(Color.gray);
            tmpPanel.add(jButtonPanel);
            tmpPanelCenter.add(tmpPanel,BorderLayout.SOUTH);
        }
        elementInPanelUpdate();
        sizeUpdate();
    }

    //Init Helper

    private void statDisplay(){
        JPanel colorDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Representative color : " + activeFaction.getColor()));
        JPanel boatDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Armada : "+ activeFaction.getLstBoat().size() + " Boat(s)"));
        JPanel harborDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Harbor possessed : "+ activeFaction.getLstHarbor().size() + " Harbor(s)"));
        JPanel comDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Commercial activity : "+ activeFaction.getLstSeaRouts().size() + " SeaRoad(s)"));
        JPanel currencyDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Capital : " + activeFaction.getAmountCurrency()*activeFaction.getCurrency().getValue() + " $"));
        JPanel relationDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Current relationship : " + relationshipInfo()));
        factionStats.removeAll();
        factionStats.add(colorDisplay);
        factionStats.add(boatDisplay);
        factionStats.add(harborDisplay);
        factionStats.add(comDisplay);
        factionStats.add(currencyDisplay);
        factionStats.add(relationDisplay);
    }

    private String relationshipInfo(){
        int relation = activeFaction.getRelationship(MapGame.getInstance().getPlayer());
        if (relation > 50)
            return "Friendly ("+ relation +")";
        if (relation > 25 && relation < 50)
            return "Favorable ("+ relation +")";
        if (relation < -25 && relation >= -50)
            return "Unfavorable ("+ relation +")";
        if (relation < -50 && relation > GameConfiguration.WAR_THRESHOLD)
            return "Dire ("+ relation +")";
        if (relation <= GameConfiguration.WAR_THRESHOLD)
            return "At war ("+ relation +")";
        return "Neutral ("+ relation +")";
    }

    private void ChangeCurrentJButton(JButton jButton){
        if(activeFactionButton!=null)activeFactionButton.setForeground(Color.white);
        activeFactionButton = jButton;
        if(jButton!=null)jButton.setForeground(Color.black);
    }

    private class buttonObjectListener implements ActionListener {
        private final Faction faction;

        public buttonObjectListener(Faction faction) {
            this.faction = faction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            activeFaction = faction;
            ChangeCurrentJButton((JButton) e.getSource());
            factionUpdate();
        }
    }

    private class ProvokeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(JOptionPane.showConfirmDialog(RelationMenu.this,"Do you want to declare war to "+activeFaction.getColor()+"? This decision cannot be reversed","confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                FactionManager.getInstance().modifyRelationship(MapGame.getInstance().getPlayer(),activeFaction, GameConfiguration.WAR_THRESHOLD);
                factionUpdate();
            }
        }
    }

    private class TradeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadChoicePathMenu(activeFaction);
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

    private class GoBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadMainGame();
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                GUILoader.loadMainGame();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }

}
