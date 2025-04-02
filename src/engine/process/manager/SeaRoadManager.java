package engine.process.manager;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;
import engine.data.trading.*;
import gui.PopUp;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;

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
        if (seaRoad.getProposedObject() instanceof Resource) if (seaRoad.getInterlocutorObject() instanceof Resource) {
            Resource proposedRes = (Resource) seaRoad.getProposedObject();
            Resource interlocutorRes = (Resource) seaRoad.getInterlocutorObject();
            if (boatManager.HarborReached(boat, seaRoad.getSellerHarbor())){
                TradeManager.getInstance().transfer(interlocutorRes, boat.getInventory().getNbResource(interlocutorRes), boat, seaRoad.getSellerHarbor());
                int totalFreeSpace = TradeManager.getInstance().totalFreeSpace(boat.getInventory());
                if (totalFreeSpace!=Math.max(0,(totalFreeSpace * seaRoad.getRatio())-totalFreeSpace)){
                    TradeManager.getInstance().transfer(proposedRes, boat.getInventory().getNbResource(proposedRes), boat, seaRoad.getSellerHarbor());
                    if (!TradeManager.getInstance().transfer(proposedRes, min(totalFreeSpace, (int) (totalFreeSpace * seaRoad.getRatio())), seaRoad.getSellerHarbor(), boat)) {
                        TradeManager.getInstance().transfer(proposedRes, seaRoad.getSellerHarbor().getInventory().getNbResource(proposedRes), seaRoad.getSellerHarbor(), boat);
                    }
                    if(MapGame.getInstance().getPlayer().getLstBoat().contains(boat)) MapGame.getInstance().addPopUp(new PopUp("-",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10), GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES));
                }
            }
        }
    }

    /**
     * If the boat is on the buyer harbor point, pick-up the resources for the exchange
     * @param seaRoad targeted seaRoad
     * @param boat targeted boat
     */
    public void sellResources(SeaRoad seaRoad, Boat boat){
        if (seaRoad.getProposedObject() instanceof Resource) if (seaRoad.getInterlocutorObject() instanceof Resource) {
            Resource proposedRes = (Resource) seaRoad.getProposedObject();
            Resource interlocutorRes = (Resource) seaRoad.getInterlocutorObject();
            if (boatManager.HarborReached(boat, seaRoad.getTargetHarbor())){
                int NbResource = boat.checkNbResource(proposedRes);
                if(NbResource!=0){
                    if(TradeManager.getInstance().transfer(proposedRes,NbResource,boat, seaRoad.getTargetHarbor())) {
                        seaRoad.addTime((int) Math.max(NbResource,NbResource/ seaRoad.getRatio()));
                        if(MapGame.getInstance().getPlayer().getLstBoat().contains(boat)|| MapGame.getInstance().getPlayer().getLstHarbor().contains(seaRoad.getTargetHarbor()))
                            MapGame.getInstance().addPopUp(new PopUp("+",new Point((int) boat.getPosition().getX(), (int) boat.getPosition().getY()-10),GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES));
                    }
                    if(!TradeManager.getInstance().transfer(interlocutorRes, (int) (NbResource/ seaRoad.getRatio()), seaRoad.getTargetHarbor(),boat)){
                        if (TradeManager.getInstance().totalFreeSpace(boat.getInventory()) >= (int) (NbResource/ seaRoad.getRatio())) seaRoad.abandonTask();
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
            pickUpResources(seaRoad, boat);
            sellResources(seaRoad, boat);
        }
        seaRoad.subtractTime(1);
    }

    public boolean mission(SeaRoad seaRoad, Boat boat){ /* Work in Progress */
        TradeManager tradeManager = TradeManager.getInstance();
        TradeOffer associatedOffer = seaRoad.getAssociatedOffer();

        boolean concluded = false;
        boolean failed = false;

        while (!concluded) {

            Inventory boatInventory = boat.getInventory();

            if (seaRoad.getProposedObject() instanceof Currency) /*I want Resource, I give gold*/{
                Resource clientResource = (Resource) seaRoad.getInterlocutorObject();
                Currency currency = (Currency) seaRoad.getProposedObject();
                int quota = associatedOffer.getDemand().get(clientResource); //Number to satisfy
                int proposedAmount = associatedOffer.getSelection().get(currency);

                log.info("quota: " + quota);

                int freeSpace = tradeManager.totalFreeSpace(boatInventory);
                int quantity  = min(freeSpace, quota);
                tradeManager.transfer(clientResource, quantity, seaRoad.getTargetHarbor(), boat);
                quota -= quantity;

                if (quota == 0) {
                    FactionManager fm = FactionManager.getInstance();
                    Faction sellerFaction = fm.getMyFaction(seaRoad.getSellerHarbor().getColor());
                    Faction clientFaction = fm.getMyFaction(seaRoad.getTargetHarbor().getColor());
                    if (!tradeManager.transfer(proposedAmount,sellerFaction,clientFaction)) failed = true;
                    concluded = true;
                }
            }
        } return !failed;
    }
}

