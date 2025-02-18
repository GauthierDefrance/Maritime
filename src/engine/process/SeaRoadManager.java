package engine.process;

import config.MapBuilder;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.graph.GraphPoint;
import engine.trading.SeaRoad;
import gui.PopUp;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * @version 0.3
 */
public class SeaRoadManager {
    private final MapBuilder map;
    private final HarborManager harborManager;
    private final TradeManager tradeManager;
    private final FleetManager fleetManager;
    private final BoatManager boatManager;

    /**
     * Typical constructor generating a SeaRoadManager
     */
    public SeaRoadManager(MapBuilder map, HarborManager harborManager, TradeManager tradeManager, FleetManager fleetManager, BoatManager boatManager) {
        this.map = map;
        this.harborManager = harborManager;
        this.tradeManager = tradeManager;
        this.fleetManager = fleetManager;
        this.boatManager = boatManager;
    }

    /**
     * set the path associated with a SeaRoad (and by extension the fleet that should circulate on this seaRoad)
     * @param seaRoad targeted seaRoad
     * @param path the new Path
     */
    public void setNewPath(SeaRoad seaRoad, ArrayList<GraphPoint> path) {
        seaRoad.setPath(path);
        fleetManager.setNewPath(seaRoad.getFleet(),path);
    }

    /**
     * Take a seaRoad set the fleet and set the continuePath of the fleet true and set the fleet path
     */
    public void setNewFleet(SeaRoad seaRoad, Fleet fleet) {
        seaRoad.setFleet(fleet);
        fleet.setContinuePath(true);
        fleetManager.setNewPath(fleet,seaRoad.getPath());
    }

    /**
     * If the boat is on the seller harbor point, proceeds the exchange
     * @param seaRoad targeted seaRoad
     * @param boat targeted boat
     */
    public void pickUpResources(SeaRoad seaRoad, Boat boat) {
        if (boatManager.HarborReached(boat, seaRoad.getSellerHarbor())){
            tradeManager.transfer(seaRoad.getBuyResource(), boat.getInventory().getNbResource(seaRoad.getBuyResource()), boat, seaRoad.getSellerHarbor());
            int totalFreeSpace = tradeManager.totalFreeSpace(boat.getInventory());
            if (totalFreeSpace!=Math.max(0,(totalFreeSpace * seaRoad.getRatio())-totalFreeSpace)){
                tradeManager.transfer(seaRoad.getSoldResource(), boat.getInventory().getNbResource(seaRoad.getSoldResource()), boat, seaRoad.getSellerHarbor());
                if (!tradeManager.transfer(seaRoad.getSoldResource(), Math.min(totalFreeSpace, (int) (totalFreeSpace * seaRoad.getRatio())), seaRoad.getSellerHarbor(), boat)) {
                    tradeManager.transfer(seaRoad.getSoldResource(), seaRoad.getSellerHarbor().getInventory().getNbResource(seaRoad.getSoldResource()), seaRoad.getSellerHarbor(), boat);
                }
                if(map.getPlayer().getLstBoat().contains(boat))map.addPopUp(new PopUp("-",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10)));
            }
        }
    }

    /**
     * If the boat is on the buyer harbor point, pick-up the resources for the exchange
     * @param seaRoad targeted seaRoad
     * @param boat targeted boat
     */
    public void sellResources(SeaRoad seaRoad, Boat boat){
        if (boatManager.HarborReached(boat, seaRoad.getBuyerHarbor())){
            int nbRessource = boat.checkNbRessource(seaRoad.getSoldResource());
            if(nbRessource!=0){
                if(tradeManager.transfer(seaRoad.getSoldResource(),nbRessource,boat, seaRoad.getBuyerHarbor())) {
                    seaRoad.addTime((int) Math.max(nbRessource,nbRessource/ seaRoad.getRatio()));
                    if(map.getPlayer().getLstBoat().contains(boat)||map.getPlayer().getLstHarbor().contains(seaRoad.getBuyerHarbor()))map.addPopUp(new PopUp("+",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10)));
                }
                if(!tradeManager.transfer(seaRoad.getBuyResource(), (int) (nbRessource/ seaRoad.getRatio()), seaRoad.getBuyerHarbor(),boat)){
                    if (tradeManager.totalFreeSpace(boat.getInventory()) >= (int) (nbRessource/ seaRoad.getRatio())) seaRoad.abandonTask();
                }
            }
        }
    }

    /**
     * Order all Boats associated with a designated seaRoad to do their tasks
     * @param seaRoad targeted seaRoad
     */
    public void sellAndPickUpAllResources(SeaRoad seaRoad){
        for (Boat boat : seaRoad.getFleet().getArrayListFleet()){
            pickUpResources(seaRoad, boat);
            sellResources(seaRoad, boat);
        }
        seaRoad.subtractTime(1);
    }

}

