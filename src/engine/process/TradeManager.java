package engine.process;

import engine.entity.Entity;
import engine.faction.Faction;
import engine.trading.*;

import java.util.HashMap;
import java.util.Random;

/**
 * A class handling how trades should work between Entities (and by extension Factions)
 * @author Zue Jack-Arthur
 * @version 0.4
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

    /**
     * Identify the resource behind a String in a given Inventory
     * @param elem String representing the resource to seek
     * @param inventory inventory targeted for identification
     * @return correct element or null if not found
     */
    public Resource identifyResource(String elem, Inventory inventory){
        if (elem != null){
            for (Resource el : inventory.getContent().keySet()){
                if (elem.equals(el.getName()))
                    return el;
            }
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

    public boolean safeAddAll(Inventory source,Inventory target){
        for(Resource resource : source.getContent().keySet()){
            if (!safeAdd(target, resource, source.getContent().get(resource)))return false;
        }
        return true;
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
            else {source.getInventory().add(resource,nb);} //Compensate for the failure to safeAdd the designated number of resource
        } return false;
    }

    /**
     * Transfer everything when possible between two Inventory
     * @param source source Inventory Object
     * @param target target Inventory Object
     * @return success of the global transfer (everything is gone)
     */
    public boolean transferAll(Inventory source, Inventory target) {
        HashMap<Resource, Integer> sourceContent = source.getContent();
        int targetFreeSpace = totalFreeSpace(target);

        if (targetFreeSpace >= totalUsedSpace(source)) {
            for (Resource elem : sourceContent.keySet()) {
                target.add(elem, sourceContent.get(elem));
            }
            sourceContent.clear();
            return true;
        }

        else {
            for (Resource resource : sourceContent.keySet()) {
                int nbResource = sourceContent.get(resource);

                if (targetFreeSpace >= nbResource) {
                    target.add(resource, nbResource);
                    sourceContent.put(resource, 0);
                    targetFreeSpace -= nbResource;
                } else {
                    target.add(resource, targetFreeSpace);
                    sourceContent.put(resource, nbResource - targetFreeSpace);
                    return false;
                }
            } return false;
        }
    }


    /**
     * Handle the transfer of currency between Faction
     * @param nb amount to transfer
     * @param source Faction that will give
     * @param target Faction that will receive
     * @return boolean indicating the success or failure of the operation
     */
    public boolean transfer(int nb, Faction source, Faction target){
        int swing = source.getCurrency().getAmount();
        if ( swing >= nb) {
            source.getCurrency().setAmount(swing - nb);
            target.getCurrency().setAmount(target.getCurrency().getAmount() + nb);
            return true;
        } return false;
    }

    //Operation on TradeOffer


    /**
     * Update one side of the offer to fit what is currently asked
     * @param side side of the offer that need to be updated
     * @param elem TradeObject that fits the update
     * @param quantity Quantity befitting the update
     * @return Updated offer
     */
    public HashMap<TradeObject, Integer> Update(HashMap<TradeObject, Integer> side, TradeObject elem, int quantity) {
        HashMap<TradeObject, Integer> temp = side;
        temp.clear();
        temp.put(elem, quantity);
        return temp;
    }

    public int CalculateValue(HashMap<TradeObject, Integer> side) {
        int sum = 0;
        for (TradeObject element : side.keySet()) {
            sum += element.getValue() * side.get(element);
        } return sum;
    }

    public double getRatio(TradeOffer offer) {
        return (double) CalculateValue(offer.getSelection()) /CalculateValue(offer.getDemand());
    }

    /**
     * Calculate the chance of success for a trade
     */
    public void calculateSuccessChance(TradeOffer offer) {
        if (offer.getDemand().keySet().equals(offer.getSelection().keySet())) {
            offer.setSuccessChance(0);
            return; //Cannot trade a resource for the same one
        }

        double ratio = getRatio(offer);
        double relation = offer.getInterlocutor().getRelationship();

        double relationshipModifier = (relation / 100.0);

        if(relationshipModifier > 0) {
            relationshipModifier *= 0.25;
        }
        double successChance = Math.max(0, Math.min(1, (ratio + (relationshipModifier))));
        offer.setSuccessChance( successChance * 100);
    }

    /**
     * Determine if the trade is successful based on the success chance
     * @param offer the TradeOffer to evaluate
     */
    public boolean evaluate(TradeOffer offer) {
        double successChance = offer.getSuccessChance();
        double roll = new Random().nextDouble() * 100;
        return (roll <= successChance);
    }

    public SeaRoad conclude(TradeOffer offer) {
        if (evaluate(offer)) {
            //SeaRoad need name
            return new SeaRoad(offer, getRatio(offer),"");
        } else {
            offer.getInterlocutor().setRelationship(offer.getInterlocutor().getRelationship() - 10);
        }
        return null;
    }
}