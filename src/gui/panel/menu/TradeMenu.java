package gui.panel.menu;

import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.process.manager.FactionManager;
import engine.process.manager.TradeManager;
import engine.data.trading.*;
import gui.utilities.GUILoader;
import gui.process.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    private String successChance() {
        return ((int) offer.getSuccessChance()) + "%";
    }

    private void updateOfferInfo() {
        if (mySelectedResourceName == null || interlocutorSelectedResourceName == null) {
            currentProposition.setText("Select resources to see the proposition.");
            successChanceLabel.setText(successChance());
            return;
        }

        successChanceLabel.setText(successChance());
        currentProposition.setText("Proposition: \n" +
                mySelectedResourceName + " (" + myQuantity.getText() +
                ") for " + interlocutorSelectedResourceName + " (" + interlocutorQuantity.getText() + ")");
    }

    private int validateEntry(Inventory inventory, JTextField quantityField, TradeObject object) {
        int answer = Integer.parseInt(quantityField.getText());
        answer = Math.max(answer, 0);
        if (object instanceof Resource) {
            Resource r = (Resource) object;
            answer = Math.min(answer, inventory.getNbResource(r));
        }
        if (object instanceof Currency) {
            Currency c = (Currency) object;
            answer = Math.min(answer, c.getAmount());
        }
        quantityField.setText("" + answer);
        return answer;
    }

    // Initialization

    private void init() {
        this.setLayout(new BorderLayout());

        JScrollPane myInventoryPanel = createInventoryPane(offer.getStartingHarbor());
        JScrollPane interlocutorInventoryPanel = createInventoryPane(offer.getTargetedHarbor());
        JPanel portDisplay = JComponentBuilder.gridMenuPanel(1, 2, BUTTON_SEPARATOR, BUTTON_SEPARATOR, myInventoryPanel, interlocutorInventoryPanel);

        myQuantity = new JTextField(20);
        interlocutorQuantity = new JTextField(20);
        JPanel myOffer = JComponentBuilder.flowMenuPanel(new JLabel("Your Resource:"), myQuantity);
        JPanel interlocutorOffer = JComponentBuilder.flowMenuPanel(new JLabel("Their Resource:"), interlocutorQuantity);

        currentProposition = new JTextArea("Proposition will appear here.");
        JButton modifyOfferButton = JComponentBuilder.menuButton("Modify Offer", new UpdateOfferListener());
        JPanel centralPanel = JComponentBuilder.gridMenuPanel(2, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, currentProposition, modifyOfferButton);
        JPanel middleRow = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, myOffer, centralPanel, interlocutorOffer);

        successChanceLabel = JComponentBuilder.menuLabel(successChance());
        JButton cancelButton = JComponentBuilder.menuButton("Cancel Offer", new GoBackListener());
        JButton proceedButton = JComponentBuilder.menuButton("Assign Crew", new ProceedListener());
        JPanel buttonPanel = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, cancelButton, successChanceLabel, proceedButton);

        JPanel totalDisplay = JComponentBuilder.gridMenuPanel(3, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, portDisplay, middleRow, buttonPanel);
        this.add(totalDisplay, BorderLayout.CENTER);
    }

    private JScrollPane createInventoryPane(Harbor harbor) {
        JPanel contentPanel = JComponentBuilder.SelectionZone();
        Faction side = FactionManager.getInstance().getMyFaction(harbor.getColor());
        boolean isMe = side != offer.getInterlocutor();

        Currency money = side.getCurrency();
        JButton moneyButton = new JButton(money.getName() + " | " + money.getAmount() + " | Value : " + money.getValue());
        moneyButton.setContentAreaFilled(false);
        moneyButton.addActionListener(new ResourceSelectionListener(isMe,money.getName()));
        contentPanel.add(moneyButton);

        for (Map.Entry<Resource, Integer> entry : harbor.getInventory().getContent().entrySet()) {
            Resource resource = entry.getKey();
            int quantity = entry.getValue();
            int value = resource.getValue();
            String resourceName = resource.getName();

            JButton resourceButton = new JButton(resourceName + " | " + quantity + " | Value: " + value);
            resourceButton.setContentAreaFilled(false);
            resourceButton.addActionListener(new ResourceSelectionListener(isMe, resourceName));
            contentPanel.add(resourceButton);
        }

        return new JScrollPane(contentPanel);
    }

    // Listeners

    private class UpdateOfferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Inventory myInventory = offer.getStartingHarbor().getInventory();
                Inventory interlocutorInventory = offer.getTargetedHarbor().getInventory();
                FactionManager fm = FactionManager.getInstance();
                TradeObject myResource = TradeManager.getInstance().identifyResource(mySelectedResourceName, myInventory);
                if (myResource == null) {
                    myResource = fm.getMyFaction(offer.getStartingHarbor().getColor()).getCurrency();
                }

                TradeObject interlocutorResource = TradeManager.getInstance().identifyResource(interlocutorSelectedResourceName, interlocutorInventory);
                if (interlocutorResource == null) {
                    interlocutorResource = fm.getMyFaction(offer.getInterlocutor().getColor()).getCurrency();
                }

                offer.setSelection(TradeManager.getInstance().updateSide(offer.getSelection(), myResource, validateEntry(myInventory, myQuantity, myResource)));
                offer.setDemand(TradeManager.getInstance().updateSide(offer.getDemand(), interlocutorResource, validateEntry(interlocutorInventory, interlocutorQuantity, interlocutorResource)));

                TradeManager.getInstance().calculateSuccessChance(offer);
                updateOfferInfo();
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Offer condition aren't filled", "Can't proceed", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class ResourceSelectionListener implements ActionListener {
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

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                GUILoader.loadRelationMenu(offer.getInterlocutor());
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadRelationMenu(offer.getInterlocutor());
        }
    }

    private class ProceedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (offer.getDemand().isEmpty() || offer.getSelection().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Offer is empty", "Can't proceed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                GUILoader.loadFleetManagingMenu(offer);
            }
        }
    }
}
