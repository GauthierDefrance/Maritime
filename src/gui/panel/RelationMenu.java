package gui.panel;

import gui.utilities.JComponentBuilder;

import javax.swing.*;
import java.util.ArrayList;

import static config.GameConfiguration.BUTTON_SEPARATOR;

public class RelationMenu extends JPanel {
    private JButton factionLogo;
    private JTextPane factionLore;
    private JTextPane factionStats;
    private JButton proposeTrade;
    private JButton declareWar;
    private JScrollPane OtherRelation;
    private ArrayList<JButton> OtherRelationButtons;

    public RelationMenu() {
        super();
        init();
    }

    private void init() {
        proposeTrade = JComponentBuilder.menuButton("Propose Trade");
        declareWar = JComponentBuilder.menuButton("Declare War");
        JPanel basicInteraction =  JComponentBuilder.gridMenuPanel(2,1,BUTTON_SEPARATOR, BUTTON_SEPARATOR, proposeTrade,declareWar);
    }
}
