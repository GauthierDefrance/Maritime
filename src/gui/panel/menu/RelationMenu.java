package gui.panel.menu;

import engine.MapGame;
import engine.data.faction.Faction;
import gui.process.ImageStock;
import gui.process.JComponentBuilder;
import gui.utilities.GUILoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static config.GameConfiguration.*;
import static gui.process.JComponentBuilder.*;

public class RelationMenu extends JPanel {
    private final Faction activeFaction;

    public RelationMenu( Faction activeFaction ) {
        super();
        this.activeFaction = activeFaction;
        init();
    }

    //Init Helper

    private String relationshipInfo(){
        int relation = activeFaction.getRelationship();
        if (relation > 50)
            return "Friendly ("+ relation +")";
        if (relation > 25 && relation < 50)
            return "Favorable ("+ relation +")";
        if (relation < -25 && relation >= -50)
            return "Unfavorable ("+ relation +")";
        if (relation < -50 && relation > WAR_THRESHOLD)
            return "Dire ("+ relation +")";
        if (relation <= WAR_THRESHOLD)
            return "At war ("+ relation +")";
        return "Neutral ("+ relation +")";
    }

    private JPanel statDisplay(){
        JPanel colorDisplay = flowMenuPanel(menuLabel("Representative color : " + activeFaction.getColor()));
        JPanel boatDisplay = flowMenuPanel(menuLabel("Armada : "+ activeFaction.getLstBoat().size() + " Boat(s)"));
        JPanel harborDisplay = flowMenuPanel(menuLabel("Harbor possessed : "+ activeFaction.getLstHarbor().size() + " Harbor(s)"));
        JPanel comDisplay = flowMenuPanel(menuLabel("Commercial activity : "+ activeFaction.getLstSeaRouts().size() + " SeaRoad(s)"));
        JPanel currencyDisplay = flowMenuPanel(menuLabel("Capital : " + activeFaction.getCurrency().getAmount()*activeFaction.getCurrency().getValue() + " $"));
        JPanel relationDisplay = flowMenuPanel(menuLabel("Current relationship : " + relationshipInfo()));
        return gridMenuPanel(8, 1, voidPanel(),colorDisplay, boatDisplay, harborDisplay, comDisplay, currencyDisplay, relationDisplay, voidPanel());
    }

    private JPanel mainCol(){
        JLabel picture = new JLabel("Image Placeholder", SwingConstants.CENTER);
        picture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JButton declareWar = menuButton("Declare War", new ProvokeListener());
        JButton interact;
        if (activeFaction.getRelationship() == WAR_THRESHOLD) {
            interact = menuButton("Engage", new TradeListener());
            interact.setBackground(Color.RED);
            declareWar.setEnabled(false);
        } else {
            interact = menuButton("Propose Trade", new TradeListener());
        }
        JPanel buttonPanel = gridMenuPanel(4,1,BUTTON_SEPARATOR, BUTTON_SEPARATOR,voidPanel(),declareWar, interact,voidPanel());
        return gridMenuPanel(2,1,BUTTON_SEPARATOR,BUTTON_SEPARATOR,picture,buttonPanel);
    }

    //Initialization

    private void init() {

        this.setLayout(new BorderLayout());

        JPanel factionStats = statDisplay();

        JPanel switchPanel = new JPanel(new BorderLayout(BUTTON_SEPARATOR, BUTTON_SEPARATOR));
        JLabel switchInfo = menuLabel("<html><s>Targets</s> Competitors</html>");
        switchInfo.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel switchGrid = gridMenuPanel(2,2, BUTTON_SEPARATOR,BUTTON_SEPARATOR);

        int i = 0;
        while (i < 4) {
            ArrayList<Faction> lstFaction = MapGame.getInstance().getLstBotFaction();
            JButton relationButton;
            if (i < lstFaction.size()){
                relationButton = ImageButton(new ImageIcon(ImageStock.getTbSprite(0,0)), new SwitchListener(lstFaction.get(i)));
            } else {
                relationButton = menuButton("Wasted");
                relationButton.setEnabled(false);
            }
            switchGrid.add(relationButton);
            i++;
        }
        switchPanel.add(switchInfo, BorderLayout.NORTH);
        switchPanel.add(switchGrid, BorderLayout.CENTER);


        JPanel midCol = mainCol();
        JPanel rightCol = gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR,factionStats, switchPanel);
        JPanel totalDisplay = gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, JComponentBuilder.voidPanel(), midCol, rightCol);

        this.addKeyListener(new KeyControls());
        this.add(totalDisplay, BorderLayout.CENTER);

        for (String position : new String[] {BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
            this.add(voidPanel(), position);
        }
    }

    private class ProvokeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(JOptionPane.showConfirmDialog(RelationMenu.this,"Do you want to declare war to "+activeFaction.getColor()+"? This decision cannot be reversed","confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                activeFaction.setRelationship(WAR_THRESHOLD);
                GUILoader.loadRelationMenu(activeFaction);
            }
        }
    }

    private class SwitchListener implements ActionListener {
        Faction target;

        public SwitchListener(Faction targetFaction) {
            super();
            this.target = targetFaction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadRelationMenu(target);
        }
    }

    private class TradeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadChoicePathMenu(activeFaction, ROOT_RELATION_MENU);
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
