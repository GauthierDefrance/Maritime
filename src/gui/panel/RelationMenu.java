package gui.panel;

import engine.MapGame;
import engine.faction.Faction;
import engine.trading.TradeOffer;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static config.GameConfiguration.*;
import static gui.utilities.LoreBuilder.buildLoreTextPane;

public class RelationMenu extends JPanel {
    private final Faction activeFaction;
    private JLabel factionLogo;
    private JTextPane factionLore;
    private JTextPane factionStats;
    private JButton proposeTrade;
    private JScrollPane OtherRelation;
    private ArrayList<JButton> OtherRelationButtons;

    public RelationMenu( Faction activeFaction ) {
        super();
        this.activeFaction = activeFaction;
        init();
    }

    private void init() {

        this.setLayout(new BorderLayout());

        proposeTrade = JComponentBuilder.menuButton("Propose Trade", new TradeListener());
        JButton declareWar = JComponentBuilder.menuButton("Declare War", new WarListener());
        JPanel basicInteraction = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, proposeTrade, declareWar);
        factionLogo = JComponentBuilder.menuLabel(activeFaction.getColor());
        factionLore = buildLoreTextPane();
        factionStats = buildLoreTextPane();

        OtherRelationButtons = new ArrayList<>();
        JPanel otherRelationsPanel = new JPanel();
        otherRelationsPanel.setLayout(new GridLayout(0, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR));

        for (Faction elm : MapGame.getInstance().getLstBotFaction()) {
            JButton relationButton = JComponentBuilder.ImageButton(new ImageIcon(IMG_FILE_PATH + "/boat/standard.png"));
            OtherRelationButtons.add(relationButton);
            otherRelationsPanel.add(relationButton);
        }

        OtherRelation = new JScrollPane(otherRelationsPanel);

        JPanel centralCol = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, factionLogo, factionLore);
        JPanel rightCol = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, factionStats, basicInteraction);
        JPanel totalDisplay = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, OtherRelation, centralCol, rightCol);

        this.add(totalDisplay, BorderLayout.CENTER);

        for (String position : new String[] {BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
            this.add(JComponentBuilder.voidPanel(), position);
        }
    }

    private class WarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            activeFaction.setRelationship(-100);
            //WiP
            GUILoader.loadMainGame();
        }
    }

    private class TradeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadChoicePathMenu(activeFaction);
            //GUILoader.loadTradeMenu(TradeOffer.create(MapGame.getInstance().getPlayer().getLstHarbor().get(0), activeFaction.getLstHarbor().get(0)));
        }
    }
}
