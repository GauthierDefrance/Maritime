package maritime.engine.process;

import maritime.config.GameInitFactory;
import maritime.engine.entity.Harbor;
import maritime.engine.entity.boats.Boat;
import maritime.engine.trading.Ressource;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class HarborManager {
    private final GameInitFactory map;
    private final TradeManager tradeManager;

    public HarborManager(GameInitFactory map,TradeManager tradeManager){
        this.map = map;
        this.tradeManager = tradeManager;
    }

    public void giveResources(Harbor harbor, Boat boat , Ressource elem, int nb){
        int add;
        if (tradeManager.safeSubtract(harbor.getInventory(),elem,nb)){add = nb;}
        else {
            add = harbor.getInventory().getContent().get(elem);
            tradeManager.safeSubtract(harbor.getInventory(),elem,add);
        }
        if (!tradeManager.safeAdd(boat.getInventory(),elem,add)){
            tradeManager.safeAdd(boat.getInventory(),elem,boat.getInventory().getCapacity()-tradeManager.totalUsedSpace(boat.getInventory()));
            tradeManager.safeAdd(harbor.getInventory(),elem,add-boat.getInventory().getCapacity()-tradeManager.totalUsedSpace(boat.getInventory()));
        }
    }

    public void obtainResources(Harbor harbor, Boat boat, Ressource elem, int nb){
        int add;
        if (tradeManager.safeSubtract(boat.getInventory(),elem,nb)){add = nb;}
        else {
            add = boat.getInventory().getContent().get(elem);
            tradeManager.safeSubtract(boat.getInventory(),elem,add);
        }
        if (!tradeManager.safeAdd(harbor.getInventory(),elem,add)){
            tradeManager.safeAdd(harbor.getInventory(),elem,harbor.getInventory().getCapacity()-tradeManager.totalUsedSpace(harbor.getInventory()));
            tradeManager.safeAdd(boat.getInventory(),elem,add-harbor.getInventory().getCapacity()-tradeManager.totalUsedSpace(harbor.getInventory()));
        }
    }



}
