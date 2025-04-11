package engine.process.manager;

import engine.data.entity.Entity;
import engine.data.entity.Harbor;
import engine.data.trading.*;

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
     * Check if the conditions are met to add a number of elements to the Inventory, and if so proceed to do it
     * @param inventory targeted Inventory
     * @param elem targeted Resource
     * @param nb number of elements to be added
     * @return boolean indicating the success or failure of the operation
     */
    public boolean safeAdd(Inventory inventory, Resource elem, int nb,Object inventoryOwner){
        if(elem instanceof Currency && inventoryOwner instanceof Harbor){
            FactionManager.getInstance().getMyFaction(((Harbor) inventoryOwner).getColor()).addAmountCurrency(nb);
            return true;
        }
        else if ((totalUsedSpace(inventory) + nb) <= inventory.getCapacity()) {
            inventory.add(elem, nb);
            return true;
        }
        else return false;
    }

    /**
     * Check if the conditions are met to subtract a number of elements off the Inventory, and if so proceed to do it
     * @param inventory targeted Inventory
     * @param elem targeted Resource
     * @param nb number of elements to be subtracted
     * @return boolean indicating the success or failure of the operation
     */
    public boolean safeSubtract(Inventory inventory, Resource elem, int nb,Object inventoryOwner){
        if(elem instanceof Currency && inventoryOwner instanceof Harbor){
            if(FactionManager.getInstance().getMyFaction(((Harbor) inventoryOwner).getColor()).getAmountCurrency()>=nb){
                FactionManager.getInstance().getMyFaction(((Harbor) inventoryOwner).getColor()).subtractAmountCurrency(nb);
                return true;
            }
            else return false;
        }
        else if (inventory.getNbResource(elem) >= nb) {
            inventory.subtract(elem, nb);
            return true;
        }
        else return false;
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
        if (safeSubtract(source.getInventory(), resource, nb,source)){
            if (safeAdd(target.getInventory(), resource, nb,target)) return true;
            else safeAdd(source.getInventory(),resource,nb,source);//Compensate for the failure to safeAdd the designated number of resource
        }
        return false;
    }

    /**
     * Transfer everything when possible between two Inventory
     * @param source source Inventory Object
     * @param target target Inventory Object
     * @return success of the global transfer (everything is gone)
     */
    public boolean transferMaxAll(Inventory source, Inventory target, Entity entitySource, Entity entityTarget) {
        HashMap<Resource, Integer> sourceContent = source.getContent();
        int targetFreeSpace = totalFreeSpace(target);

        if (targetFreeSpace >= totalUsedSpace(source)) {
            for (Resource elem : sourceContent.keySet()) {
                safeAdd(target,elem,sourceContent.get(elem),entityTarget);
            }
            sourceContent.clear();
            return true;
        }

        else {
            for (Resource resource : sourceContent.keySet()) {
                int nbResource = sourceContent.get(resource);

                if (targetFreeSpace >= nbResource) {
                    safeAdd(target,resource,nbResource,entityTarget);
                    sourceContent.put(resource, 0);
                    targetFreeSpace -= nbResource;
                } else {
                    safeAdd(target,resource,targetFreeSpace,entityTarget);
                    sourceContent.put(resource, nbResource - targetFreeSpace);
                    return false;
                }
            } return false;
        }
    }

    //Operation on SeaRoad

    /**
     * Calculate the chance of success for a trade
     */
    public double calculateSuccessChance(SeaRoad offer) {
        if (offer.getDemand()!=null || offer.getSelection()!=null ||offer.getDemand().getKey().equals(offer.getSelection().getKey())) {
            return 0; //Cannot trade a resource for the same one
        }

        double ratio = offer.getRatio();
        double relation = FactionManager.getInstance().getMyFaction(offer.getTargetHarbor().getColor()).getRelationship(FactionManager.getInstance().getMyFaction(offer.getSellerHarbor().getColor()));

        double relationshipModifier = (relation / 100.0);

        if(relationshipModifier > 0) {
            relationshipModifier *= 0.25;
        }
        double successChance = Math.max(0, Math.min(1, (ratio + (relationshipModifier))));
        return successChance * 100;
    }

    /**
     * Determine if the trade is successful based on the success chance
     * @param offer the SeaRoad to evaluate
     */
    public boolean evaluate(SeaRoad offer) {
        double roll = new Random().nextDouble() * 100;
        return (roll <= calculateSuccessChance(offer));
    }

    public SeaRoad conclude(SeaRoad offer) {
        if (evaluate(offer)) {
            //SeaRoad need name
            return offer;
        } else {
            FactionManager.getInstance().modifyRelationship(FactionManager.getInstance().getMyFaction(offer.getSellerHarbor().getColor()),FactionManager.getInstance().getMyFaction(offer.getTargetHarbor().getColor()), -10);
        }
        return null;
    }
}