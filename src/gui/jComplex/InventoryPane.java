package gui.jComplex;

import engine.trading.Inventory;
import engine.trading.Resource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class InventoryPane extends JScrollPane {

    private JPanel contentPanel;
    private String selectedResourceName; // Stores the last clicked resource name

    public InventoryPane(Inventory inventory) {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Transparent background

        for (Map.Entry<Resource, Integer> entry : inventory.getContent().entrySet()) {
            Resource resource = entry.getKey();
            int quantity = entry.getValue();
            int value = resource.getValue();

            JButton button = new JButton(resource.getName() + " | " + quantity + " | Value: " + value);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);

            // Create and set action listener
            button.addActionListener(new ResourceSelectionHandler(resource));

            contentPanel.add(button);
        }

        setViewportView(contentPanel);
    }

    public String getSelectedResourceName() {
        return selectedResourceName;
    }

    // Separate ActionListener class
    private class ResourceSelectionHandler implements ActionListener {
        private final Resource resource;

        public ResourceSelectionHandler(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedResourceName = resource.getName();
        }
    }
}
