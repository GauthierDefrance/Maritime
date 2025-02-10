package maritime.engine.process;

import maritime.config.GameInitFactory;
import maritime.engine.trading.Inventory;
import maritime.engine.trading.Ressource;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class TradeManager {

    private final GameInitFactory map;

    public TradeManager(GameInitFactory map) {
        this.map = map;
    }

    public int checkRessourceNumber(Inventory inventory,Ressource elem){
        return inventory.getContent().getOrDefault(elem, 0);
        /* évite de renvoyer null si l'élément n'est pas présent dans l'inventaire et considère plutôt une présence
         par défaut de nb 0 */
    }

    public boolean safeAdd(Inventory inventory, Ressource elem, int nb){
        if ((totalUsedSpace(inventory) + nb) < inventory.getCapacity()) {
            inventory.getContent().replace(elem, checkRessourceNumber(inventory, elem) + nb);
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
    public int totalFreeSpace(Inventory inventory){
        return inventory.getCapacity() - totalUsedSpace(inventory);
    }
}
