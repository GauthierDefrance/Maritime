package engine.process;

import engine.entity.Entity;
import engine.trading.Inventory;
import engine.trading.Resource;
import engine.trading.TradeOffer;

import java.util.Random;

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
    public boolean transfer(Resource resource, int nb, Entity source, Entity target){
        if (safeSubtract(source.getInventory(), resource, nb)){
            if (safeAdd(target.getInventory(), resource, nb)) return true;
            else {source.getInventory().add(resource,nb);} //Compensate for the failure to safeAdd the designated number of ressource
        } return false;
    }

    /**
     * Calculate the chance of success for a trade --TO BE IMPROVED--
     * @return success chance as a percentage
     */
    public double calculateSuccessChance(TradeOffer offer) {
        double ratio = offer.getRatio();
        // Generate a random factor between 0.5 and 1.0
        double randomFactor = 0.5 + (new Random().nextDouble() * 0.5);

        // Normalize the relationship factor to a range between 0.5 and 1.5
        double relationshipModifier = 1 + (offer.getRelationship() / 200.0); // -100 => 0.5, 0 => 1.0, 100 => 1.5

        // Calculate success chance
        double successChance = Math.max(0, Math.min(1, (ratio * randomFactor * relationshipModifier)));

        return successChance * 100; // Convert to percentage
    }
}
