package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.trading.SeaRoad;
import maritime.engine.entity.boats.Boat;
import maritime.engine.trading.Resource;

import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class SeaRoadManager {
    private final MapBuilder map;
    private final HarborManager harborManager;
    private final TradeManager tradeManager;
    private final FleetManager fleetManager;

    public SeaRoadManager(MapBuilder map, HarborManager harborManager, TradeManager tradeManager, FleetManager fleetManager) {
        this.map = map;
        this.harborManager = harborManager;
        this.tradeManager = tradeManager;
        this.fleetManager = fleetManager;
    }

    public void setNewPath(SeaRoad seaRoad, ArrayList<GraphPoint> path) {
        seaRoad.setPath(path);
        fleetManager.setNewPath(seaRoad.getFleet(),path);
    }

    public void setNewFleet(SeaRoad seaRoad, Fleet fleet) {
        seaRoad.setFleet(fleet);
        fleet.setContinuePath(true);
        fleetManager.setNewPath(fleet,seaRoad.getPath());
    }

    public void pickUpResources(SeaRoad seaRoad, Boat boat) {
        Resource ResourceA = seaRoad.getSellingResource();
        if (boat.getPosition().equals(seaRoad.getSellerHarbor().getPreciseGraphPosition())){
            tradeManager.transfer(ResourceA, boat.getInventory().getNbResource(ResourceA), boat, seaRoad.getSellerHarbor());
            tradeManager.transfer(seaRoad.getSold(), boat.getInventory().getNbResource(seaRoad.getSold()), boat, seaRoad.getSellerHarbor());
            if(!tradeManager.transfer(seaRoad.getSold(), Math.min(tradeManager.totalFreeSpace(boat.getInventory()),(int) (tradeManager.totalFreeSpace(boat.getInventory()) * seaRoad.getRatio())), seaRoad.getSellerHarbor(),boat)){
                tradeManager.transfer(seaRoad.getSold(), seaRoad.getSellerHarbor().getInventory().getNbResource(seaRoad.getSold()), seaRoad.getSellerHarbor(),boat);
            }
        }
    }

    public void sellResources(SeaRoad seaRoad, Boat boat){
        if (boat.getPosition().equals(seaRoad.getBuyerHarbor().getPreciseGraphPosition())){
            int nbRessource = boat.getInventory().getNbResource(seaRoad.getSold());
            if(tradeManager.transfer(seaRoad.getSold(),nbRessource,boat, seaRoad.getBuyerHarbor())) seaRoad.addTime(nbRessource);
            if(!tradeManager.transfer(seaRoad.getSellingResource(), (int) (nbRessource/ seaRoad.getRatio()), seaRoad.getBuyerHarbor(),boat)){
                if (tradeManager.totalFreeSpace(boat.getInventory()) >= (int) (nbRessource/ seaRoad.getRatio()))
                    seaRoad.abandonTask();
            }
        }
    }

    public void sellAndPickUpAllResources(SeaRoad seaRoad){
        for (Boat boat : seaRoad.getFleet().getArrayListFleet()){
            pickUpResources(seaRoad, boat);
            sellResources(seaRoad, boat);
        }
        seaRoad.subtractTime(1);
    }
}

