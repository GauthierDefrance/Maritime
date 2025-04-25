package engine.process.manager;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.graph.GraphPoint;
import engine.data.trading.*;
import gui.PopUp;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author Kenan Ammad
 * @version 0.3
 */
public class SeaRoadManager {
    private static final Logger log = Logger.getLogger(SeaRoadManager.class);
    private final FleetManager fleetManager;
    private final BoatManager boatManager;

    /**
     * Typical constructor generating a SeaRoadManager
     */
    public SeaRoadManager(FleetManager fleetManager, BoatManager boatManager) {
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
        fleetManager.setNewPath(seaRoad.getFleet(),path,true);
    }

    /**
     * Take a seaRoad set the fleet and set the continuePath of the fleet true and set the fleet path
     */
    public void setNewFleet(SeaRoad seaRoad, Fleet fleet) {
        fleetManager.removePath(seaRoad.getFleet());
        seaRoad.setFleet(fleet);
        fleet.setContinuePath(true);
        fleetManager.setNewPath(fleet,seaRoad.getPath(),true);
    }

    /**
     * If the boat is on the seller harbor point, proceeds the exchange
     * @param seaRoad targeted seaRoad
     * @param boat targeted boat
     */
    public void pickUpResources(SeaRoad seaRoad, Boat boat) {
        if (boatManager.HarborReached(boat, seaRoad.getSellerHarbor())){
            TradeManager.getInstance().transfer(seaRoad.getDemand().getKey(), boat.getInventory().getNbResource(seaRoad.getDemand().getKey()), boat, seaRoad.getSellerHarbor());
            int totalFreeSpace = TradeManager.getInstance().totalFreeSpace(boat.getInventory());
            if (totalFreeSpace > Math.max(0,(boat.getInventory().getNbResource(seaRoad.getSelection().getKey())*seaRoad.getRatio())-boat.getInventory().getNbResource(seaRoad.getSelection().getKey())) && seaRoad.getSellerHarbor().getInventory().getNbResource(seaRoad.getSelection().getKey())>0){
                TradeManager.getInstance().transfer(seaRoad.getSelection().getKey(), boat.getInventory().getNbResource(seaRoad.getSelection().getKey()), boat, seaRoad.getSellerHarbor());
                totalFreeSpace = TradeManager.getInstance().totalFreeSpace(boat.getInventory());
                if (!TradeManager.getInstance().transfer(seaRoad.getSelection().getKey(), min(totalFreeSpace, (int) (totalFreeSpace * seaRoad.getRatio())), seaRoad.getSellerHarbor(), boat)) {
                    TradeManager.getInstance().transfer(seaRoad.getSelection().getKey(), seaRoad.getSellerHarbor().getInventory().getNbResource(seaRoad.getSelection().getKey()), seaRoad.getSellerHarbor(), boat);
                }
                if(MapGame.getInstance().getPlayer().getLstBoat().contains(boat)) MapGame.getInstance().addPopUp(new PopUp("-",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10), GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES));
            }
        }
    }

    /**
     * If the boat is on the buyer harbor point, pick-up the resources for the exchange
     * @param seaRoad targeted seaRoad
     * @param boat targeted boat
     */
    public void sellResources(SeaRoad seaRoad, Boat boat){
        if (boatManager.HarborReached(boat, seaRoad.getTargetHarbor())){
            int NbResource = boat.checkNbResource(seaRoad.getSelection().getKey());
            if(NbResource>max(0,(int)seaRoad.getRatio())){
                if(TradeManager.getInstance().transfer(seaRoad.getSelection().getKey(),NbResource,boat, seaRoad.getTargetHarbor())) {

                    seaRoad.addTime(2*(int) Math.max(NbResource, NbResource / seaRoad.getRatio()));
                    seaRoad.getSelection().setValue(seaRoad.getSelection().getValue()-NbResource);
                    seaRoad.getDemand().setValue(seaRoad.getDemand().getValue()-(int) (NbResource * seaRoad.getRatio()));
                    if (MapGame.getInstance().getPlayer().getLstBoat().contains(boat) || MapGame.getInstance().getPlayer().getLstHarbor().contains(seaRoad.getTargetHarbor())) MapGame.getInstance().addPopUp(new PopUp("+", new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY() - 10), GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES));

                    if (!TradeManager.getInstance().transfer(seaRoad.getDemand().getKey(), (int) (NbResource * seaRoad.getRatio()), seaRoad.getTargetHarbor(), boat)) {
                        if (TradeManager.getInstance().totalFreeSpace(boat.getInventory()) >= (int) (NbResource * seaRoad.getRatio())){
                            TradeManager.getInstance().transfer(seaRoad.getDemand().getKey(),seaRoad.getTargetHarbor().getInventory().getNbResource(seaRoad.getSelection().getKey()), seaRoad.getTargetHarbor(), boat);
                            TradeManager.getInstance().transfer(seaRoad.getSelection().getKey(),NbResource-seaRoad.getTargetHarbor().getInventory().getNbResource(seaRoad.getSelection().getKey()),seaRoad.getTargetHarbor(),boat);
                            seaRoad.abandonTask();
                        }
                    }
                }
            }
        }
    }

    /**
     * Order all Boats associated with a designated seaRoad to do their tasks
     * @param seaRoad targeted seaRoad
     */
    public void sellAndPickUpAllResources(SeaRoad seaRoad){
        for (Boat boat : seaRoad.getFleet().getArrayListBoat()){
            if(seaRoad.isActive()) {
                pickUpResources(seaRoad, boat);
                sellResources(seaRoad, boat);
            }
        }
        seaRoad.subtractTime(1);
    }
}

