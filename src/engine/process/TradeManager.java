package engine.process;

import engine.entity.EntityInterface;
import engine.trading.Inventory;
import engine.trading.Resource;

/**
 * A class handling how trades should work between Entities (and by extension Factions)
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class TradeManager {

    /**
     * Initialize the TradeManager : class handling how trades should work between Entities (and by extension Factions)
     */
    public TradeManager() {}

    /**
     * Calculate the offered value of a side in a trade
     * @param resource Resource designated by the trade
     * @param nb number of elements to trade
     * @return indicative value of the offer
     */
    public int offerValue(Resource resource, int nb){
        return resource.getValue() * nb;
    }

    /**
     * Calculate the used space in an Inventory
     * @param inventory targeted Inventory
     * @return value indicating how much space is used
     */
    public int totalUsedSpace(Inventory inventory){
        int total = 0;
        for (Resource elem : inventory.getContent().keySet()) {total += inventory.getContent().get(elem);}
        return total;
    }

    /**
     * Calculate the free space in an Inventory
     * @param inventory targeted Inventory
     * @return value indicating how much space remains
     */
    public int totalFreeSpace(Inventory inventory){
        return inventory.getCapacity() - totalUsedSpace(inventory);
    }

    /**
     * Check if the conditions are met to add a number of elements to the Inventory, and if so proceed to do it
     * @param inventory targeted Inventory
     * @param elem targeted Resource
     * @param nb number of elements to be added
     * @return boolean indicating the success or failure of the operation
     */
    public boolean safeAdd(Inventory inventory, Resource elem, int nb){
        if ((totalUsedSpace(inventory) + nb) <= inventory.getCapacity()) {
            inventory.add(elem, nb);
            return true;
        } else return false;
    }

    /**
     * Check if the conditions are met to subtract a number of elements off the Inventory, and if so proceed to do it
     * @param inventory targeted Inventory
     * @param elem targeted Resource
     * @param nb number of elements to be subtracted
     * @return boolean indicating the success or failure of the operation
     */
    public boolean safeSubtract(Inventory inventory, Resource elem, int nb){
        if (inventory.getNbResource(elem) >= nb) {
            inventory.subtract(elem, nb);
            return true;
        } else return false;
    }

    /**
     * Handle the transfer of a number of elements from an Entity to another
     * @param resource targeted resource
     * @param nb number of elements to be transferred
     * @param source sending Entity
     * @param target receiving Entity
     * @return boolean indicating the success or failure of the operation
     */
    public boolean transfer(Resource resource, int nb, EntityInterface source, EntityInterface target){
        if (safeSubtract(source.getInventory(), resource, nb)){
            if (safeAdd(target.getInventory(), resource, nb)) return true;
            else {source.getInventory().add(resource,nb);} //Compensate for the failure to safeAdd the designated number of ressource
        } return false;
    }
}
