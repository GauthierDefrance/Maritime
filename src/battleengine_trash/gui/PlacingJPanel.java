package battleengine_trash.gui;

import battleengine_trash.process.FightManager;
import battleengine_trash.process.PlacingManager;
import config.GameConfiguration;
import engine.entity.boats.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 *
 * @author Gauthier Defrance
 * @version 0.1
 */
public class PlacingJPanel extends JPanel {

    private PlacingManager placingManager;
    private FightManager fightManager;
    private HashMap<Boat,JButton> buttonHashMap;
    private DashBoardPlacingPanel dashBoardPlacingPanel;
    private JScrollPane scrollPane;

    private JPanel buttonPanel;

    public PlacingJPanel(PlacingManager placingManager, FightManager fightManager) {
        super(new BorderLayout());
        this.placingManager = placingManager;
        this.fightManager = fightManager;

        buttonHashMap = new HashMap<Boat,JButton>();

        dashBoardPlacingPanel = new DashBoardPlacingPanel(fightManager, placingManager);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, GameConfiguration.NUMBER_COLUMN_BATTLE_PLACING));
        buttonPanelFleetAdding();

        scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(dashBoardPlacingPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.WEST);

    }


    /**
     * Méthode générant les boutons associés à chaque bateau
     */
    private void buttonPanelFleetAdding(){
        JButton tmpbutton;
        for(Boat boat : placingManager.getBoatToPlace().getArrayListBoat()){
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
            tmpbutton.addActionListener(new ButtonSelected(boat));
            tmpbutton.setBackground(GameConfiguration.DEFAULT_BACKGROUND_COLOR);
            tmpbutton.setFocusPainted(false);
            tmpbutton.setBorderPainted(false);
            tmpbutton.setIcon(new ImageIcon(GameConfiguration.START_FILE_PATH + "/boat/"+text+".png"));
            buttonPanel.add(tmpbutton);
            buttonHashMap.put(boat,tmpbutton);
        }
    }

    /**
     * Méthode permettant la suppression d'un bouton couplé à un Bateau précis.
     * @param boat
     * @throws NoSuchElementException
     */
    private void removeButton(Boat boat) throws NoSuchElementException {
        if (buttonHashMap.containsKey(boat)){
            buttonPanel.remove(buttonHashMap.get(boat));
            buttonHashMap.remove(boat);
        }
        else {
            throw (new NoSuchElementException());
        }
    }



    /**
     * Classe de détection de cliques sur les JButton
     */
    private class ButtonSelected implements ActionListener {
        private Boat boat;
        /**
         * Constructeur de classe, prend un bateau en paramètre
         * afin de se rappeler à quel bateau il est associé.
         * @param boat
         */
        public ButtonSelected(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Le bateau :"+boat.getName()+" a été sélectionné");
            placingManager.selectBoat(this.boat);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dashBoardPlacingPanel.repaint();
    }




}
