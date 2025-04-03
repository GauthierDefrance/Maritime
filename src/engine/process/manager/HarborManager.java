package engine.process.manager;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.trading.Resource;

import java.awt.*;

/**
 * @author Kenan Ammad
 * @version 0.1
 */
public class HarborManager {

    /**
     * Typical constructor generating an HarborManager
     */
    public HarborManager(){

    }

    public boolean canIAddGenerator(Harbor harbor,Resource resource){
        return harbor.getGenerator().size() < harbor.getLevel()
                && resource.getValue() / resource.getProductionRate()*GameConfiguration.COST_GENERATOR < FactionManager.getInstance().getMyFaction(harbor.getColor()).getCurrency().getAmount()
                &&(!harbor.getGenerator().containsKey(resource));
    }

    public boolean canILevelUpHarbor(Harbor harbor){
        return harbor.getLevel()*GameConfiguration.COST_LEVEL_UP_HARBOR < FactionManager.getInstance().getMyFaction(harbor.getColor()).getCurrency().getAmount();
    }

    public void levelUpHarbor(Harbor harbor){
        FactionManager.getInstance().getMyFaction(harbor.getColor()).getCurrency().subtractAmount(harbor.getLevel()*GameConfiguration.COST_LEVEL_UP_HARBOR);
        harbor.levelUp();
    }

    public boolean canILevelUpBoat(Harbor harbor, Boat boat){
        return boat.getLevel() < harbor.getLevel()
                && boat.getLevel()*GameConfiguration.COST_LEVEL_UP_BOAT < FactionManager.getInstance().getMyFaction(harbor.getColor()).getCurrency().getAmount();
    }

    public void levelUpBoat(Harbor harbor, Boat boat){
        FactionManager.getInstance().getMyFaction(harbor.getColor()).getCurrency().subtractAmount(harbor.getLevel()*GameConfiguration.COST_LEVEL_UP_BOAT);
        boat.levelUp();
    }

    public boolean canIUpgradeBoat(Harbor harbor, Boat boat){
        return boat.getLevel()<= harbor.getLevel() && boat.getUpgradePoint()>0;
    }

    public boolean canIUpgradeHp(Harbor harbor, Boat boat) {
        return canIUpgradeBoat(harbor,boat)
                && 100 < harbor.getInventory().getNbResource(GameConfiguration.WOOD)
                && 100 < harbor.getInventory().getNbResource(GameConfiguration.METAL);
    }

    public void upgradeHp(Harbor harbor, Boat boat) {
        harbor.getInventory().subtract(GameConfiguration.WOOD,100);
        harbor.getInventory().subtract(GameConfiguration.METAL,100);
        boat.upgradeHp();
    }

    public boolean canIUpgradeDamageSpeed(Harbor harbor, Boat boat) {
        return canIUpgradeBoat(harbor,boat)
                && 100 < harbor.getInventory().getNbResource(GameConfiguration.METAL)
                && 200 < harbor.getInventory().getNbResource(GameConfiguration.CLOTH);
    }

    public void upgradeDamageSpeed(Harbor harbor, Boat boat) {
        harbor.getInventory().subtract(GameConfiguration.METAL,100);
        harbor.getInventory().subtract(GameConfiguration.CLOTH,200);
        boat.upgradeDamageSpeed();
    }

    public boolean canIUpgradeSpeed(Harbor harbor, Boat boat) {
        return canIUpgradeBoat(harbor,boat)
                && 300 < harbor.getInventory().getNbResource(GameConfiguration.CLOTH);
    }

    public void upgradeSpeed(Harbor harbor, Boat boat) {
        harbor.getInventory().subtract(GameConfiguration.CLOTH,300);
        boat.upgradeSpeed();
    }

    public boolean canIUpgradeInventorySize(Harbor harbor, Boat boat) {
        return canIUpgradeBoat(harbor,boat)
                && 100 < harbor.getInventory().getNbResource(GameConfiguration.WOOD);
    }

    public void upgradeInventorySize(Harbor harbor, Boat boat) {
        harbor.getInventory().subtract(GameConfiguration.WOOD,100);
        boat.upgradeInventorySize();
    }


    public void updateGeneratorTime(Harbor harbor){
        for(Resource resource : harbor.getGenerator().keySet()){
            if(harbor.getGenerator().get(resource)<harbor.getLevel()){
                harbor.getGenerator().put(resource,resource.getProductionRate());
                harbor.getInventory().add(resource,1);
                //add popup here
            }
            else harbor.getGenerator().put(resource,harbor.getGenerator().get(resource)-1);
        }
    }

    /**
     * Check if a Harbor reached a targeted point in map
     * @param point concerned
     * @return Harbor
     */
    public Harbor pointCollisionToMapHarbor(Point point){
        Harbor harbor1 = null;
        for (Harbor harbor2 : MapGame.getInstance().getLstHarbor()){
            if (GameConfiguration.HITBOX_BOAT - 5 >= point.distance(harbor2.getPosition())){
                if(harbor1 != null){
                    if(point.distance(harbor2.getPosition()) < point.distance(harbor1.getPosition())){
                        harbor1 = harbor2;
                    }
                }
                else harbor1 = harbor2;
            }
        }
        return harbor1;
    }


}
