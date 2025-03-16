package gui.panel;

import config.GameConfiguration;
import gui.utilities.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static config.GameConfiguration.BUTTON_SEPARATOR;
//import static gui.utilities.LoreBuilder.buildLoreTextPane;

public class RelationMenu extends JPanel {
    private JLabel factionLogo;
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

        this.setLayout(new BorderLayout());

        proposeTrade = JComponentBuilder.menuButton("Propose Trade");
        declareWar = JComponentBuilder.menuButton("Declare War");
        JPanel basicInteraction = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, proposeTrade, declareWar);
        factionLogo = JComponentBuilder.ImageLabel(new ImageIcon(GameConfiguration.IMG_FILE_PATH + "/boat/standard.png"));
//        factionLore = buildLoreTextPane();
//        factionStats = buildLoreTextPane();

        OtherRelationButtons = new ArrayList<>();
        JPanel otherRelationsPanel = new JPanel();
        otherRelationsPanel.setLayout(new GridLayout(0, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR));

        for (int i = 1; i <= 10; i++) {
            JButton relationButton = JComponentBuilder.ImageButton(new ImageIcon(GameConfiguration.IMG_FILE_PATH + "/boat/standard.png")); // Replace with your actual faction names
            OtherRelationButtons.add(relationButton);
            otherRelationsPanel.add(relationButton);
        }

        // Wrap the panel with buttons in a JScrollPane
        OtherRelation = new JScrollPane(otherRelationsPanel);

        // Combine panels into layout
        JPanel centralCol = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, factionLogo, factionLore);
        JPanel rightCol = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, factionStats, basicInteraction);
        JPanel totalDisplay = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, OtherRelation, centralCol, rightCol);

        this.add(totalDisplay, BorderLayout.CENTER);

        for (String position : new String[] {BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
            this.add(JComponentBuilder.voidPanel(), position);
        }
    }

}
