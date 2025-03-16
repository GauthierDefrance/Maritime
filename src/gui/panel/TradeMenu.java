package gui.panel;

import engine.process.TradeManager;
import engine.trading.Inventory;
import engine.trading.Resource;
import engine.trading.TradeOffer;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.NoSuchElementException;

import static config.GameConfiguration.BUTTON_SEPARATOR;

public class TradeMenu extends JPanel {
    private final TradeOffer offer;
    private String mySelectedResourceName;
    private String interlocutorSelectedResourceName;
    private JTextField myQuantity;
    private JTextField interlocutorQuantity;
    private JTextArea currentProposition;
    private JLabel successChanceLabel;

    public TradeMenu(TradeOffer offer) {
        super();
        this.offer = offer;
        init();
    }

    // Utilities

    private String getSuccessChanceLabelText() {
        return TradeManager.getInstance().calculateSuccessChance(offer) + "%";
    }

    private void updateOfferInfo() {
        if (mySelectedResourceName == null || interlocutorSelectedResourceName == null) {
            currentProposition.setText("Select resources to see the proposition.");
            successChanceLabel.setText(getSuccessChanceLabelText());
            return;
        }

        successChanceLabel.setText(getSuccessChanceLabelText());
        currentProposition.setText("Proposition: \n" +
                mySelectedResourceName + " " + myQuantity.getText() +
                " contre " + interlocutorSelectedResourceName + " " + interlocutorQuantity.getText());
    }

    private int routine(Inventory inventory, JTextField quantityField, Resource resource) {
        int answer = Integer.parseInt(quantityField.getText());
        if (answer < 0) {
            answer = 0;
        }
        if (answer > inventory.getNbResource(resource)) {
            answer = inventory.getNbResource(resource);
        }
        quantityField.setText("" + answer);
        return answer;
    }

    // Initialization

    private void init() {
        this.setLayout(new BorderLayout());

        JScrollPane myInventoryPanel = createInventoryPane(offer.getStartingHarbor().getInventory(), true);
        JScrollPane interlocutorInventoryPanel = createInventoryPane(offer.getTargetedHarbor().getInventory(), false);
        JPanel portDisplay = JComponentBuilder.gridMenuPanel(1, 2, BUTTON_SEPARATOR, BUTTON_SEPARATOR, myInventoryPanel, interlocutorInventoryPanel);

        myQuantity = new JTextField(20);
        interlocutorQuantity = new JTextField(20);
        JPanel myOffer = JComponentBuilder.flowMenuPanel(new JLabel("Your Resource:"), myQuantity);
        JPanel interlocutorOffer = JComponentBuilder.flowMenuPanel(new JLabel("Their Resource:"), interlocutorQuantity);

        currentProposition = new JTextArea("Proposition will appear here.");
        JButton modifyOfferButton = JComponentBuilder.menuButton("Modify Offer", new SelectionListener());
        JPanel centralPanel = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, currentProposition, modifyOfferButton);
        JPanel middleRow = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, myOffer, centralPanel, interlocutorOffer);

        successChanceLabel = JComponentBuilder.menuLabel(getSuccessChanceLabelText());
        JButton cancelButton = JComponentBuilder.menuButton("Cancel Offer", new GoBackListener());
        JButton proceedButton = JComponentBuilder.menuButton("Assign Crew to Proposition", new ProceedListener());
        JPanel buttonPanel = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, cancelButton, successChanceLabel, proceedButton);

        JPanel totalDisplay = JComponentBuilder.gridMenuPanel(3, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, portDisplay, middleRow, buttonPanel);
        this.add(totalDisplay, BorderLayout.CENTER);
    }

    private JScrollPane createInventoryPane(Inventory inventory, boolean isMyInventory) {
        JPanel contentPanel = JComponentBuilder.SelectionZone();

        for (Map.Entry<Resource, Integer> entry : inventory.getContent().entrySet()) {
            Resource resource = entry.getKey();
            int quantity = entry.getValue();
            int value = resource.getValue();

            JButton ressourceButton = new JButton(resource.getName() + " | " + quantity + " | Value: " + value);
            ressourceButton.setContentAreaFilled(false);
            ressourceButton.addActionListener(new ResourceSelectionListener(isMyInventory,resource.getName()));

            contentPanel.add(ressourceButton);
        }

        return new JScrollPane(contentPanel);
    }

    // Listeners

    private class SelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Inventory myInventory = offer.getStartingHarbor().getInventory();
                Inventory interlocutorInventory = offer.getTargetedHarbor().getInventory();

                Resource myResource = TradeManager.getInstance().identifyResource(mySelectedResourceName, myInventory);
                Resource interlocutorResource = TradeManager.getInstance().identifyResource(interlocutorSelectedResourceName, interlocutorInventory);

                offer.setSelection(TradeManager.getInstance().Transform(offer.getSelection(), myResource, routine(myInventory, myQuantity, myResource)));
                offer.setDemand(TradeManager.getInstance().Transform(offer.getDemand(), interlocutorResource, routine(interlocutorInventory, interlocutorQuantity, interlocutorResource)));

                updateOfferInfo();
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class ResourceSelectionListener implements ActionListener {
        private final boolean isMyInventory;
        private final String resourceName;

        public ResourceSelectionListener(boolean isMyInventory, String resourceName) {
            this.isMyInventory = isMyInventory;
            this.resourceName = resourceName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isMyInventory) {
                mySelectedResourceName = resourceName;
            } else {
                interlocutorSelectedResourceName = resourceName;
            } updateOfferInfo();
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
