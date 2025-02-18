package maritime.engine.process;

import maritime.config.GameConfiguration;
import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.faction.Faction;
import maritime.engine.graph.GraphPoint;
import maritime.engine.trading.SeaRoad;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author @Kenan Ammad
 * Classe FactionManager
 * @version 0.5
 */
public class FactionManager {
    private final MapBuilder map;
    private final PlayerManager playerManager;
    private final BoatManager boatManager;
    private final HarborManager harborManager;
    private final FleetManager fleetManager;
    private final SeaRoadManager seaRoutManager;
    private ArrayList<Boat[]> lstAttackBoat;

    /**
     * Typical builder generating an FactionManager
     */
    public FactionManager(MapBuilder map) {
        this.map = map;
        this.playerManager = new PlayerManager(map);
        this.boatManager = new BoatManager(map);
        this.harborManager = new HarborManager(map,new TradeManager());
        this.fleetManager = new FleetManager(map,boatManager);
        this.seaRoutManager = new SeaRoadManager(map,this.harborManager,new TradeManager(),this.fleetManager, this.boatManager);
        this.lstAttackBoat = new ArrayList<>();
    }

    public void nextRound(){
        moveAllFactionBoat();
        allFleetUpdate();
        allSeaRoutUpdate();
        allChaseUpdate();
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
        for (Faction faction : map.getLstFaction()){
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
        for (Faction faction : map.getLstFaction()){
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
        for (Faction faction : map.getLstFaction()){
            seaRoutUpdate(faction);
        }
    }

    /**
     * Initialize a chase between two boats
     * @param hunter chasing boat
     * @param prey chased boat
     */
    public void chaseBoat(Boat hunter, Boat prey){
        lstAttackBoat.add(new Boat[]{hunter,prey});
        hunter.setPath(new ArrayList<>(Collections.singleton(new GraphPoint(prey.getPosition(), "target"))));
        hunter.setIPath(0);
        hunter.setContinuePath(false);
    }

    /**
     * Take two boats starts a fight if they are in contact cancels the chase if they are too far away
     */
    public void chaseUpdate(Boat[] tbBoat){
        double distance = Math.sqrt(Math.pow((tbBoat[1].getPosition().getX() - tbBoat[0].getPosition().getX()), 2) + Math.pow((tbBoat[1].getPosition().getY() - tbBoat[0].getPosition().getY()), 2));
        if(GameConfiguration.HITBOX_BOAT-5 >= distance){
            StartFight(tbBoat[0],tbBoat[1]);
            lstAttackBoat.remove(tbBoat);
            tbBoat[0].getPath().clear();
            tbBoat[0].setNextGraphPoint(tbBoat[1].getNextGraphPoint());
        }
        else if (tbBoat[0].getVisionRadius()+20 < distance){
            lstAttackBoat.remove(tbBoat);
            tbBoat[0].getPath().clear();
            tbBoat[0].setNextGraphPoint(tbBoat[1].getNextGraphPoint());
        }
    }

    /**
     * For all the boats that are on the chase starts a fight if they are in contact cancels the chase if they are too far away
     */
    public void allChaseUpdate(){
        ArrayList<Boat[]> lstAttackBoatTemp = new ArrayList<>();
        lstAttackBoatTemp.addAll(lstAttackBoat);
        for (Boat[] tbBoat : lstAttackBoatTemp){
                chaseUpdate(tbBoat);
        }
    }

    // à mettre dans une class combat
    public void StartFight(Boat boat1,Boat boat2){
        ArrayList<Boat> vision1 = new ArrayList<>();
        ArrayList<Boat> vision2 = new ArrayList<>();
        vision1.add(boat1);
        vision2.add(boat2);
        for (Boat boat : getMyFaction(boat1.getColor()).getLstBoat()){
            for (Boat playerBoat : map.getPlayer().getLstBoat()){
                if (playerBoat.getVisionRadius() /2 >= Math.sqrt(Math.pow((boat.getPosition().getX()-playerBoat.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-playerBoat.getPosition().getY()),2))){
                    if (!vision1.contains(boat)){vision1.add(boat);}
                }
            }
        }
        for (Boat boat : getMyFaction(boat2.getColor()).getLstBoat()){
            for (Boat playerBoat : map.getPlayer().getLstBoat()){
                if(playerBoat.getVisionRadius() /2 >= Math.sqrt(Math.pow((boat.getPosition().getX()-playerBoat.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-playerBoat.getPosition().getY()),2))){
                    if(!vision2.contains(boat)){vision2.add(boat);}
                }
            }
        }
        JOptionPane.showMessageDialog(null, vision1+"vs"+vision2);
    }

    /**
     * gives the faction associated with a color
     * @param color a String representing a color
     */
    public Faction getMyFaction(String color){
        for (Faction faction : map.getLstFaction()) {
            if (faction.getColor().equals(color)) {
                return faction;
            }
        }
        return new Faction("");
    }

    public SeaRoadManager getSeaRoadManager() {
        return seaRoutManager;
    }
}
