package battleengine_trash.TOREWORK;

import battleengine_trash.engine.SpawnZone;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import gui.PaintEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class BattlePlacingPanel extends JPanel {
    private Fleet Af, Bf;
    private Fleet BattleA, BattleB;
    private JPanel buttonPanel;
    private JPanel buttonSelectPanel;
    private HashMap<Boat,JButton> buttonHashMap;
    private DashBoardPlacing dashboard;
    private Boat currentBoat;
    private SpawnZone zoneA, zoneB;
    private JScrollPane scrollPane;
    private boolean hasFinished;

    public BattlePlacingPanel(Fleet Af, Fleet Bf, Fleet BattleA, Fleet BattleB, PaintEntity paintEntity, SpawnZone zoneA, SpawnZone zoneB) {
        super(new BorderLayout());
        hasFinished=false;
        buttonHashMap = new HashMap<>();
        this.zoneA=zoneA; this.zoneB=zoneB;
        this.Af = Af; this.Bf = Bf;
        this.BattleA = BattleA; this.BattleB = BattleB;
        buttonSelectPanel = new JPanel();
        buttonPanelSelection();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, GameConfiguration.NUMBER_COLUMN_BATTLE_PLACING));
        buttonPanelFleetAdding();

        dashboard = new DashBoardPlacing(paintEntity, BattleA, BattleB, this.zoneA, this.zoneB, this);

        scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        buttonSelectPanel.add(scrollPane, BorderLayout.CENTER);
        add(buttonSelectPanel, BorderLayout.WEST);
        add(dashboard, BorderLayout.CENTER);
    }

    /**
     * Méthode générant les boutons associés à chaque bateau
     */
    private void buttonPanelFleetAdding(){
        JButton tmpbutton;
        for(Boat boat : Af.getArrayListBoat()){
            String text = "";
            switch (boat.getClass().getName()) {
                case "engine.entity.boats.Standard": {
                    text = "standard";
                    break;
                }
                case "engine.entity.boats.Fodder": {
                    text = "fodder";
                    break;
                }
                case "engine.entity.boats.Merchant": {
                    text = "merchant";
                    break;
                }
                case "engine.entity.boats.Military": {
                    text = "military";
                    break;
                }
                default: {
                    text = "standard";
                }
            }
            tmpbutton = new JButton(text);
            tmpbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
            tmpbutton.setHorizontalTextPosition(SwingConstants.CENTER);
            tmpbutton.addActionListener((ActionListener) new ButtonSelected(boat));
            tmpbutton.setBackground(new Color(78, 172, 233));
            tmpbutton.setFocusPainted(false);
            tmpbutton.setBorderPainted(false);
            tmpbutton.setIcon(new ImageIcon(GameConfiguration.START_FILE_PATH + "/boat/"+text+".png"));
            buttonPanel.add(tmpbutton);
            buttonHashMap.put(boat,tmpbutton);
        }
    }

    private void buttonPanelSelection(){
        JButton accept = new JButton("Accept");
        JButton cancel = new JButton("Cancel");

        //accept.addActionListener();

        buttonSelectPanel.add(accept);
        buttonSelectPanel.add(cancel);

    }


    //Remplacer le système par un maintien de bouton
    public HashMap<Boat,JButton> getButtonHashMap() {return buttonHashMap;}
    public JPanel getButtonPanel() {return buttonPanel;}
    public JScrollPane getScrollPane() {return scrollPane;}

    /**
     * Classe de détection de cliques sur les JButton
     */
    private class ButtonSelected implements ActionListener {
        private Boat boat;
        public ButtonSelected(Boat boat) {
            this.boat = boat;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
                currentBoat=boat;
                dashboard.setCurrentBoat(currentBoat);
                repaint(); //A modifier

        }
    }

    private class AcceptAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            hasFinished=true;
        }
    }

    private class Cancel implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }

}
