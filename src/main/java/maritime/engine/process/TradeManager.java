package maritime.engine.process;

import maritime.config.GameInitFactory;
import maritime.engine.trading.Inventory;
import maritime.engine.trading.Ressource;

public class TradeManager {

    private final GameInitFactory map;

    public TradeManager(GameInitFactory map) {
        this.map = map;
    }

    public boolean safeAdd(Inventory inventory, Ressource elem, int nb){
        if ((totalUsedSpace(inventory) + nb) < inventory.getCapacity()) {
            inventory.getContent().replace(elem, inventory.getContent().get(elem) + nb);
            return true;
        }
        else {return false;}
    }

    public boolean safeSubtract(Inventory inventory, Ressource elem, int nb){
        if (inventory.getContent().get(elem) >= nb) {
            inventory.getContent().replace(elem, inventory.getContent().get(elem) - nb);
            return true;
        }
        else {return false;}
    }

    public int totalUsedSpace(Inventory inventory){
        int total = 0;
        for (Ressource elem : inventory.getContent().keySet()) {total += inventory.getContent().get(elem);}
        return total;
    }
}
