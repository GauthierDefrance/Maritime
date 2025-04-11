package engine.process.manager;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.Fleet;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.trading.Currency;
import engine.data.trading.Resource;
import engine.utilities.SearchInGraph;

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

    public int getNbGenerator(Harbor harbor){
        int nb = 0;
        for (Resource resource : harbor.getGenerator().keySet()){
            nb +=  harbor.getGenerator().get(resource)[1];

        }
        return nb;
    }

    public void addBoatInHarbor(Harbor harbor,Boat boat){
        boat.getPath().clear();
        boat.setContinuePath(false);
        harbor.getHashMapBoat().put(boat,false);
    }

    public void addFleetInHarbor(Harbor harbor, Fleet fleet){
        for (Boat boat : fleet.getArrayListBoat()){
            addBoatInHarbor(harbor,boat);
        }
    }

    public void removeBoatInHarbor(Harbor harbor,Boat boat){
        harbor.getHashMapBoat().remove(boat);
        if(harbor.getHashMapBoat().get(boat)) {
            boat.setPosition(new Point(harbor.getGraphPosition().getPoint()));
        }
    }

    public void removeFleetInHarbor(Harbor harbor, Fleet fleet){
        for (Boat boat : fleet.getArrayListBoat()){
            removeBoatInHarbor(harbor,boat);
        }
    }

    public void boatApproachingHarbor(Harbor harbor){
        for (Boat boat : harbor.getHashMapBoat().keySet()){
            if(!harbor.getHashMapBoat().get(boat)){
                if(harbor.getGraphPosition().getPoint().equals(boat.getPosition())){
                    harbor.getHashMapBoat().put(boat,true);
                    boat.setPosition(harbor.getPosition().getX()*-100000,harbor.getPosition().getY()*-100000);
                }
                else if(boat.getPath().isEmpty()) {
                    SearchInGraph.findPath(boat, harbor.getGraphPosition());
                }
            }
        }
    }

    public boolean canIAddGenerator(Harbor harbor,Resource resource){
        return getNbGenerator(harbor) < harbor.getLevel()
                && resource.getValue() / resource.getProductionRate()*GameConfiguration.COST_GENERATOR < FactionManager.getInstance().getMyFaction(harbor.getColor()).getAmountCurrency()
                &&(!harbor.getGenerator().containsKey(resource));
    }

    public void addGenerator(Harbor harbor,Resource resource){
        FactionManager.getInstance().getMyFaction(harbor.getColor()).subtractAmountCurrency(resource.getValue() / resource.getProductionRate()*GameConfiguration.COST_GENERATOR);
        if(harbor.getGenerator().containsKey(resource))harbor.getGenerator().get(resource)[1]+=1;
        else harbor.getGenerator().put(resource, new Integer[]{resource.getValue(),1});
    }

    public void removeGenerator(Harbor harbor,Resource resource){
        if (harbor.getGenerator().get(resource)[1]<2)harbor.getGenerator().remove(resource);
        else harbor.getGenerator().get(resource)[1]-=1;
    }

    public boolean canILevelUpHarbor(Harbor harbor){
        return harbor.getLevel()*GameConfiguration.COST_LEVEL_UP_HARBOR < FactionManager.getInstance().getMyFaction(harbor.getColor()).getAmountCurrency();
    }

    public void levelUpHarbor(Harbor harbor){
        FactionManager.getInstance().getMyFaction(harbor.getColor()).subtractAmountCurrency(harbor.getLevel()*GameConfiguration.COST_LEVEL_UP_HARBOR);
        harbor.levelUp();
    }

    public boolean canILevelUpBoat(Harbor harbor, Boat boat){
        return boat.getLevel() < harbor.getLevel()
                && boat.getLevel()*GameConfiguration.COST_LEVEL_UP_BOAT < FactionManager.getInstance().getMyFaction(harbor.getColor()).getAmountCurrency();
    }

    public void levelUpBoat(Harbor harbor, Boat boat){
        FactionManager.getInstance().getMyFaction(harbor.getColor()).subtractAmountCurrency(harbor.getLevel()*GameConfiguration.COST_LEVEL_UP_BOAT);
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
            if(harbor.getGenerator().get(resource)[0]<harbor.getLevel()){
                harbor.getGenerator().get(resource)[0] = resource.getProductionRate();
                harbor.getInventory().add(resource,1);
                //add popup here
            }
            else harbor.getGenerator().get(resource)[0] -= 1;
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
