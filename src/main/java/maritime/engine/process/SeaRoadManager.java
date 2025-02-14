package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.trading.SeaRoad;
import maritime.engine.entity.boats.Boat;
import maritime.engine.trading.Resource;
import maritime.gui.PopUp;

import java.awt.*;
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
        if (boat.getPosition().equals(seaRoad.getSellerHarbor().getPreciseGraphPosition())){
            tradeManager.transfer(seaRoad.getBuyResource(), boat.getInventory().getNbResource(seaRoad.getBuyResource()), boat, seaRoad.getSellerHarbor());
            int totalFreeSpace = tradeManager.totalFreeSpace(boat.getInventory());
            if (totalFreeSpace!=Math.max(0,(totalFreeSpace * seaRoad.getRatio())-totalFreeSpace)){
                tradeManager.transfer(seaRoad.getSoldResource(), boat.getInventory().getNbResource(seaRoad.getSoldResource()), boat, seaRoad.getSellerHarbor());
                if (!tradeManager.transfer(seaRoad.getSoldResource(), Math.min(totalFreeSpace, (int) (totalFreeSpace * seaRoad.getRatio())), seaRoad.getSellerHarbor(), boat)) {
                    tradeManager.transfer(seaRoad.getSoldResource(), seaRoad.getSellerHarbor().getInventory().getNbResource(seaRoad.getSoldResource()), seaRoad.getSellerHarbor(), boat);
                }
                map.addPopUp(new PopUp("-",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10)));
            }
        }
    }

    public void sellResources(SeaRoad seaRoad, Boat boat){
        if (boat.getPosition().equals(seaRoad.getBuyerHarbor().getPreciseGraphPosition())){
            int nbRessource = boat.getInventory().getNbResource(seaRoad.getSoldResource());
            if(nbRessource!=0){
                if(tradeManager.transfer(seaRoad.getSoldResource(),nbRessource,boat, seaRoad.getBuyerHarbor())) {
                    seaRoad.addTime((int) Math.max(nbRessource,nbRessource/ seaRoad.getRatio()));
                    map.addPopUp(new PopUp("+",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10)));
                }
                if(!tradeManager.transfer(seaRoad.getBuyResource(), (int) (nbRessource/ seaRoad.getRatio()), seaRoad.getBuyerHarbor(),boat)){
                    if (tradeManager.totalFreeSpace(boat.getInventory()) >= (int) (nbRessource/ seaRoad.getRatio())) seaRoad.abandonTask();
                }
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

