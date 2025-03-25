package gui.panel;

import engine.entity.Harbor;
import engine.entity.boats.Fleet;
import engine.trading.TradeOffer;

import javax.swing.*;

public class FleetManagingMenu extends JPanel {
    private Fleet fleet;
    private TradeOffer tradeOffer;
    private Harbor startingHarbor;
    private Harbor targetedHarbor;

    public FleetManagingMenu(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
        this.startingHarbor = tradeOffer.getStartingHarbor();
        this.targetedHarbor = tradeOffer.getTargetedHarbor();
    }

    public FleetManagingMenu(Harbor A, Harbor B) {
        this.startingHarbor = A;
        this.targetedHarbor = B;
    }

}
