package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.EntityInterface;
import maritime.engine.trading.Inventory;
import maritime.engine.trading.Resource;

/**
 * @author @Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class TradeManager {

    private MapBuilder map;

    public TradeManager(MapBuilder map) {
        this.map = map;
    }

    public int tradeValue(Resource resource, int nb){
        return resource.getValue() * nb;
    }
    public int totalUsedSpace(Inventory inventory){
        int total = 0;
        for (Resource elem : inventory.getContent().keySet()) {total += inventory.getContent().get(elem);}
        return total;
    }

    public int totalFreeSpace(Inventory inventory){
        return inventory.getCapacity() - totalUsedSpace(inventory);
    }

    public boolean safeAdd(Inventory inventory, Resource elem, int nb){
        if ((totalUsedSpace(inventory) + nb) <= inventory.getCapacity()) {
            inventory.add(elem, nb);
            return true;
        } else return false;
    }

    public boolean safeSubtract(Inventory inventory, Resource elem, int nb){
        if (inventory.getNbResource(elem) >= nb) {
            inventory.subtract(elem, nb);
            return true;
        } else return false;
    }

    public boolean transfer(Resource resource, int nb, EntityInterface source, EntityInterface target){
        if (safeSubtract(source.getInventory(), resource, nb)){
            if (safeAdd(target.getInventory(), resource, nb)){
                return true;
            }
            else {source.getInventory().add(resource,nb);}
        }
        return false;
    }

    public void transaction(Resource resource, int nb, EntityInterface source, EntityInterface target){
    }
}
