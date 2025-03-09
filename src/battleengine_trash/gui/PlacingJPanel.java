package battleengine_trash.gui;

import battleengine_trash.engine.Battle;
import config.GameConfiguration;
import engine.entity.boats.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static gui.process.JComponentBuilder.menuButton;

/**
 *
 * @author Gauthier Defrance
 * @version 0.2
 */
public class PlacingJPanel extends JPanel {

    private Battle battle;

    public PlacingJPanel(Battle battle) {
        super(new BorderLayout());
        this.battle = battle;
        this.battle.getPlacing().setButtonHashMap(new HashMap<Boat,JButton>());
        this.battle.getPlacing().setDashBoardPlacingPanel(new DashBoardPlacingPanel(battle));

        this.battle.getPlacing().setButtonPanel(new JPanel());
        buttonPanelFleetAdding();

        this.battle.getPlacing().setScrollPane(new JScrollPane(this.battle.getPlacing().getButtonPanel()));
        this.battle.getPlacing().getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel decidingButtonPanel = new JPanel();
        decidingButtonPanel.setBackground(Color.DARK_GRAY);

        JButton startingBattleButton = new JButton("Battle !");
        startingBattleButton.setPreferredSize(new Dimension(200,100));
        JButton CancelPlacingButton = new JButton("Cancel");
        CancelPlacingButton.setPreferredSize(new Dimension(200,100));
        CancelPlacingButton.addActionListener(new CancelButton());

        decidingButtonPanel.add(startingBattleButton);
        decidingButtonPanel.add(CancelPlacingButton);

        add(decidingButtonPanel , BorderLayout.SOUTH);

        add(this.battle.getPlacing().getDashBoardPlacingPanel(), BorderLayout.CENTER);
        add(this.battle.getPlacing().getScrollPane(), BorderLayout.WEST);

    }


    /**
     * Méthode générant les boutons associés à chaque bateau
     */
    public void buttonPanelFleetAdding(){
        JButton tmpbutton;
        this.battle.getPlacing().getButtonPanel().removeAll();
        this.battle.getPlacing().getButtonPanel().setLayout(new GridLayout(0, GameConfiguration.NUMBER_COLUMN_BATTLE_PLACING));
        for(Boat boat : this.battle.getPlacing().getBoatToPlace().getArrayListBoat()){

            tmpbutton = menuButton(boat,new ButtonSelected(boat));
            tmpbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
            tmpbutton.setHorizontalTextPosition(SwingConstants.CENTER);
            tmpbutton.setBackground(GameConfiguration.DEFAULT_BACKGROUND_COLOR);
            tmpbutton.setFocusPainted(false);
            tmpbutton.setBorderPainted(false);

            this.battle.getPlacing().getButtonPanel().add(tmpbutton);
            this.battle.getPlacing().getButtonHashMap().put(boat,tmpbutton);
        }
    }

    /**
     * Méthode permettant la suppression d'un bouton couplé à un Bateau précis.
     * @param boat
     * @throws NoSuchElementException
     */
    private void removeButton(Boat boat) throws NoSuchElementException {
        if (this.battle.getPlacing().getButtonHashMap().containsKey(boat)){
            this.battle.getPlacing().getButtonPanel().remove(this.battle.getPlacing().getButtonHashMap().get(boat));
            this.battle.getPlacing().getButtonHashMap().remove(boat);
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
            battle.getPlacingManager().selectBoat(this.boat);
        }
    }

    private class CancelButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            battle.getPlacingManager().cancelPlacing();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.battle.getPlacing().getDashBoardPlacingPanel().repaint();
    }

}
