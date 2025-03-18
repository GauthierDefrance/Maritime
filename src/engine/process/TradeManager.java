package engine.process;

import engine.entity.Entity;
import engine.trading.Inventory;
import engine.trading.Resource;
import engine.trading.SeaRoad;
import engine.trading.TradeOffer;

import java.util.HashMap;
import java.util.Random;

/**
 * A class handling how trades should work between Entities (and by extension Factions)
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class TradeManager {

    private static TradeManager instance;

    /**
     * Initialize the TradeManager : class handling how trades should work between Entities (and by extension Factions)
     */
    private TradeManager() {}

    /**
     * Allows to fetch and use the TradeManager Singleton
     * @return Instance of TradeManager
     */
    public static TradeManager getInstance() {
        if (instance == null) {
            instance = new TradeManager();
        }
        return instance;
    }

    //Operation on Inventory

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

    public Resource identifyResource(String elem, Inventory inventory){
        for (Resource el : inventory.getContent().keySet()){
            if (elem.equals(el.getName()))
                return el;
        } return null;
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

    //Operation on TradeOffer

    public HashMap<Resource, Integer> Transform(HashMap<Resource, Integer> side, Resource resource, int quantity) {
        HashMap<Resource, Integer> temp = side;
        temp.clear();
        temp.put(resource, quantity);
        return temp;
    }

    public int CalculateValue(HashMap<Resource, Integer> side) {
        int sum = 0;
        for (Resource resource : side.keySet()) {
            sum += resource.getValue() * side.get(resource);
        } return sum;
    }

    public double getRatio(TradeOffer offer) {
        return (double) CalculateValue(offer.getSelection()) /CalculateValue(offer.getDemand());
    }

    /**
     * Calculate the chance of success for a trade --TO BE IMPROVED--
     * @return success chance as a percentage
     */
    public double calculateSuccessChance(TradeOffer offer) {
        double ratio = getRatio(offer);
        // Generate a random factor between 0.5 and 1.0
        double randomFactor = 0.5 + (new Random().nextDouble() * 0.5);

        // Normalize the relationship factor to a range between 0.5 and 1.5
        double relationshipModifier = 1 + (offer.getInterlocutor().getRelationship() / 200.0); // -100 => 0.5, 0 => 1.0, 100 => 1.5

        // Calculate success chance
        double successChance = Math.max(0, Math.min(1, (ratio * randomFactor * relationshipModifier)));

        return successChance * 100;
    }

    /**
     * Determine if the trade is successful based on the success chance
     * @param offer the TradeOffer to evaluate
     */
    public boolean rollForSuccessChance(TradeOffer offer) {
        double successChance = calculateSuccessChance(offer);
        double roll = new Random().nextDouble() * 100;
        return (roll <= successChance);
    }

    public SeaRoad conclude(TradeOffer offer) {
        if (rollForSuccessChance(offer)) {
            //SeaRoad need name
            return new SeaRoad(offer, getRatio(offer),"");
        } else {
            offer.getInterlocutor().setRelationship(offer.getInterlocutor().getRelationship() - 20);
        }
        return null;
    }
}