package gui.panel;

import engine.process.TradeManager;
import engine.trading.Inventory;
import engine.trading.Resource;
import engine.trading.TradeOffer;
import gui.jComplex.InventoryPane;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import static config.GameConfiguration.BUTTON_SEPARATOR;

public class TradeMenu extends JPanel {
    private final TradeOffer offer;
    private InventoryPane myPanel;
    private InventoryPane interlocutorPanel;
    private JLabel myResource;
    private JLabel interlocutorResource;
    private JTextField myQuantity;
    private JTextField interlocutorQuantity;
    private JTextArea currentProposition;
    private JLabel successChanceLabel;

    public TradeMenu(TradeOffer offer) {
        super();
        this.offer = offer;
        init();
    }

    //Utilities

    private String SuccessChanceLabelText() {
        return TradeManager.getInstance().calculateSuccessChance(offer)+"%";
    }

    private void updateOfferInfo(){
        successChanceLabel.setText(SuccessChanceLabelText());
        currentProposition.setText("Proposition : \n "+myResource.getText()+" "+myQuantity.getText()+" contre "+interlocutorResource.getText()+" "+interlocutorQuantity.getText());
    }

    private int routine(Inventory inventory, JTextField QuantityField, Resource resource) {
        int answer = Integer.parseInt(QuantityField.getText());
        if (answer < 0){
            answer = 0;
        }
        if (answer > inventory.getNbResource(resource)) {
            answer = inventory.getNbResource(resource);
        }
        QuantityField.setText(""+answer);
        return answer;
    }

    //Init

    private void init() {

        this.setLayout(new BorderLayout());
        for (String position : new String[]{BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
            this.add(JComponentBuilder.voidPanel(), position);
        }

        myPanel = new InventoryPane(offer.getStartingHarbor().getInventory());
        interlocutorPanel = new InventoryPane(offer.getTargetedHarbor().getInventory());
        JPanel portDisplay = JComponentBuilder.gridMenuPanel(1,2,BUTTON_SEPARATOR,BUTTON_SEPARATOR, myPanel, interlocutorPanel);

        myResource = JComponentBuilder.menuLabel("Test");
        myQuantity = new JTextField(20);
        JPanel myOffer = JComponentBuilder.flowMenuPanel(myResource, myQuantity);

        interlocutorResource = JComponentBuilder.menuLabel("Test");
        interlocutorQuantity = new JTextField(20);
        JPanel interlocutorOffer = JComponentBuilder.flowMenuPanel(interlocutorResource, interlocutorQuantity);

        currentProposition = new JTextArea();
        JPanel middleRow = JComponentBuilder.gridMenuPanel(1,3,BUTTON_SEPARATOR,BUTTON_SEPARATOR, myOffer,currentProposition, interlocutorOffer);

        successChanceLabel = JComponentBuilder.menuLabel(SuccessChanceLabelText());
        JButton cancelButton = JComponentBuilder.menuButton("Cancel offer", new GoBackListener());
        JButton proceedButton = JComponentBuilder.menuButton("Assign Crew to proposition", new ProceedListener());
        JPanel buttonPanel = JComponentBuilder.gridMenuPanel(1,3,BUTTON_SEPARATOR,BUTTON_SEPARATOR, cancelButton,successChanceLabel, proceedButton);

        JPanel totalDisplay = JComponentBuilder.gridMenuPanel(3,1,BUTTON_SEPARATOR,BUTTON_SEPARATOR, portDisplay, middleRow, buttonPanel);
        this.add(totalDisplay, BorderLayout.CENTER);

    }

    //Listener

    private class DemandListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Inventory inventory = offer.getTargetedHarbor().getInventory();
            Resource resource = TradeManager.getInstance().identifyResource(interlocutorResource.getText(), inventory);

            try {
                offer.setDemand(TradeManager.getInstance().Transform(offer.getDemand(), resource, routine(inventory, interlocutorQuantity, resource)));
                updateOfferInfo();
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SelectionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Inventory inventory = offer.getStartingHarbor().getInventory();
            Resource resource = TradeManager.getInstance().identifyResource(myResource.getText(), inventory);

            try {
                offer.setSelection(TradeManager.getInstance().Transform(offer.getSelection(), resource, routine(inventory, myQuantity, resource)));
                updateOfferInfo();
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class GoBackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadMainGame();
        }
    }

    private class ProceedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadFleetManagingMenu(offer);
        }
    }
}
