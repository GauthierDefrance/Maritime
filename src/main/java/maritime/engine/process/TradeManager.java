package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.EntityInterface;
import maritime.engine.trading.Inventory;
import maritime.engine.trading.Ressource;

/**
 * @author @Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class TradeManager {

    private MapBuilder map;

    public TradeManager(MapBuilder map) {
        this.map = map;
    }

    public int tradeValue(Ressource ressource, int nb){
        return ressource.getValue() * nb;
    }
    public int totalUsedSpace(Inventory inventory){
        int total = 0;
        for (Ressource elem : inventory.getContent().keySet()) {total += inventory.getContent().get(elem);}
        return total;
    }

    public int totalFreeSpace(Inventory inventory){
        return inventory.getCapacity() - totalUsedSpace(inventory);
    }

    public boolean safeAdd(Inventory inventory, Ressource elem, int nb){
        if ((totalUsedSpace(inventory) + nb) <= inventory.getCapacity()) {
            inventory.add(elem, nb);
            return true;
        } else return false;
    }

    public boolean safeSubtract(Inventory inventory, Ressource elem, int nb){
        if (inventory.getNbRessource(elem) >= nb) {
            inventory.subtract(elem, nb);
            return true;
        } else return false;
    }

    public boolean transfer(Ressource ressource, int nb, EntityInterface source, EntityInterface target){
        if (safeSubtract(source.getInventory(), ressource, nb))
            return safeAdd(target.getInventory(), ressource, nb);
        return false;
    }

    public void transaction(Ressource ressource, int nb, EntityInterface source, EntityInterface target){
    }
}
