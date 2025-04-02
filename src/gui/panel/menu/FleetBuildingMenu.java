package gui.panel.menu;

import engine.data.Fleet;
import engine.data.trading.TradeOffer;

import javax.swing.*;

public class FleetBuildingMenu extends JPanel {
    private Fleet fleet;
    private TradeOffer tradeOffer; //Don't delete it, can be null depending on usage

    public FleetBuildingMenu(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
        this.fleet = tradeOffer.getConcernedFleet();
    }

    public FleetBuildingMenu() {
        this.fleet = new Fleet();
    }
}
