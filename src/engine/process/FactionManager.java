package engine.process;

import config.GameConfiguration;
import engine.MapGame;
import engine.battleengine.data.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.faction.Faction;
import engine.graph.GraphPoint;
import engine.process.builder.EngineBuilder;
import engine.trading.SeaRoad;
import engine.utilities.SearchInGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Kenan Ammad
 * Classe FactionManager
 * @version 0.5
 */
public class FactionManager {
    private final PlayerManager playerManager;
    private final BoatManager boatManager;
    private final HarborManager harborManager;
    private final FleetManager fleetManager;
    private final SeaRoadManager seaRoutManager;

    /**
     * Typical builder generating an FactionManager
     */
    public FactionManager() {
        this.playerManager = new PlayerManager();
        this.boatManager = new BoatManager();
        this.harborManager = new HarborManager();
        this.fleetManager = new FleetManager(boatManager);
        this.seaRoutManager = new SeaRoadManager(this.harborManager,this.fleetManager, this.boatManager);
    }

    public void nextRound(){
        moveAllFactionBoat();
        allFleetUpdate();
        allSeaRoutUpdate();
        playerManager.updatePlayerVision();
    }

    /**
     * Take all faction boat and make it follow its path, don't do anything if the path is empty
     */
    public void moveFactionBoat(Faction faction){
        for (Boat boat : faction.getLstBoat()){
            boatManager.followThePath(boat);
        }
    }

    /**
     * Take all map boat and make it follow its path, don't do anything if the path is empty
     */
    public void moveAllFactionBoat(){
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            moveFactionBoat(faction);
        }
    }

    /**
     * Take a faction and for all faction fleet update all boat fleet path if is empty
     */
    public void fleetUpdate(Faction faction){
        for (Fleet fleet : faction.getLstFleet()){
            fleetManager.pathUpdate(fleet);
        }
    }

    /**
     * Take a map and for all faction fleet update all boat fleet path if is empty
     */
    public void allFleetUpdate(){
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            fleetUpdate(faction);
        }
    }

    /**
     * for all faction sea road boat pickUpResources and sellResources and remove sea road if timer < 0
     */
    public void seaRoutUpdate(Faction faction){
        ArrayList<SeaRoad> lstSeaRouts = new ArrayList<>();
        for (SeaRoad seaRoad : faction.getLstSeaRouts()){
            seaRoutManager.sellAndPickUpAllResources(seaRoad);
            if (!seaRoad.available()){
                lstSeaRouts.add(seaRoad);
                fleetManager.setContinuePathAll(seaRoad.getFleet(),false);
                seaRoad.getFleet().setPath(new ArrayList<>());
            }
        }
        faction.getLstSeaRouts().removeAll(lstSeaRouts);
    }

    /**
     * for all map faction sea road boat pickUpResources and sellResources and remove sea road if timer < 0
     */
    public void allSeaRoutUpdate(){
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            seaRoutUpdate(faction);
        }
    }

    /**
     * Initialize a chase between two boats
     * @param hunter chasing boat
     * @param prey chased boat
     */
    public void chaseBoat(Boat hunter, Boat prey){
        MapGame.getInstance().addHunterPreyHashMap(hunter,prey);
        hunter.setPath(new ArrayList<>(Collections.singleton(new GraphPoint(prey.getPosition(), "target"))));
        hunter.setIPath(0);
        hunter.setContinuePath(false);
    }

    /**
     * Take two boats starts a fight if they are in contact cancels the chase if they are too far away
     */
    public ArrayList<Boat> chaseUpdate(Boat hunter,Boat prey){
        double distance = hunter.getPosition().distance(prey.getPosition());
        if(GameConfiguration.HITBOX_BOAT-5 >= distance){
            MapGame.getInstance().removeHunterPreyHashMap(hunter);
            hunter.getPath().clear();
            hunter.setNextGraphPoint(prey.getNextGraphPoint());
            ArrayList<Boat> lst = new ArrayList<>();
            lst.add(hunter);
            lst.add(prey);
            return lst;
        }
        else if (hunter.getVisionRadius() < distance){
            MapGame.getInstance().removeHunterPreyHashMap(hunter);
            hunter.getPath().clear();
            hunter.setNextGraphPoint(SearchInGraph.getClosestMapGraphPoint(hunter.getPosition()));
        }
        return null;
    }

    /**
     * For all the boats that are on the chase starts a fight if they are in contact cancels the chase if they are too far away
     */
    public ArrayList<Boat> allChaseUpdate(){
        ArrayList<Boat> lst = null;
        HashMap<Boat, Boat> tmp = new HashMap<>(MapGame.getInstance().getHunterPreyHashMap());
        for (Boat boat : tmp.keySet()){
            lst = chaseUpdate(boat,tmp.get(boat));
            if(lst!=null)return lst;
        }
        return lst;
    }

    // à mettre dans une class combat
    public Battle StartBattle(Boat hunter, Boat prey){
        Fleet fleetHunter = getMyFleet(hunter);
        Fleet fleetPrey = getMyFleet(prey);
        return EngineBuilder.createBattle(getMyFaction(hunter.getColor()),getMyFaction(prey.getColor()),fleetHunter,fleetPrey);
    }

    /**
     * gives the faction associated with a color
     * @param color a String representing a color
     */
    public Faction getMyFaction(String color){
        for (Faction faction : MapGame.getInstance().getLstFaction()) {
            if (faction.getColor().equals(color)) {
                return faction;
            }
        } return new Faction("");
    }

    /**
     * gives the Fleet associated with a boat
     */
    public Fleet getMyFleet(Boat boat){
        Fleet fleet = null;
        Faction tmp = getMyFaction(boat.getColor());
        for(Fleet fleet2 : tmp.getLstFleet()){
            if(fleet2.getArrayListBoat().contains(boat))fleet=fleet2;
        }
        if(fleet == null){
            fleet = new Fleet();
            fleet.add(boat);
        }
        return fleet;
    }

    public SeaRoadManager getSeaRoadManager() {
        return seaRoutManager;
    }

    public BoatManager getBoatManager(){
        return boatManager;
    }

    public HarborManager getHarborManager(){
        return harborManager;
    }
}
