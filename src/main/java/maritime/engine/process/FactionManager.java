package maritime.engine.process;


import maritime.config.GameConfiguration;
import maritime.config.GameInitFactory;
import maritime.engine.SeaRout;
import maritime.engine.entity.boats.Boat;
import maritime.engine.faction.Faction;
import maritime.engine.graph.GraphPoint;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author @Kenan Ammad
 * Classe FactionManager
 * @version 0.3
 */

public class FactionManager {
    private final GameInitFactory map;
    private final PlayerManager playerManager;
    private final BoatManager boatManager;
    private final HarborManager harborManager;
    private final FleetManager fleetManager;
    private final SeaRoutManager seaRoutManager;
    private ArrayList<Boat[]> lstAttackBoat;

    public FactionManager(GameInitFactory map) {
        this.map = map;
        this.playerManager = new PlayerManager(map);
        this.boatManager = new BoatManager(map);
        this.harborManager = new HarborManager(map,new TradeManager(map));
        this.fleetManager = new FleetManager(map,boatManager);
        this.seaRoutManager = new SeaRoutManager(map,this.harborManager,new TradeManager(map));
        this.lstAttackBoat = new ArrayList<>();
    }

    public void nextRound(){
        moveAllFactionBoat();
        allSeaRoutUpdate();
        allChaseUpdate();
        playerManager.updatePlayerVision();
    }

    public void moveFactionBoat(Faction faction){
        for (Boat boat : faction.getLstBoat()){
            boatManager.followThePath(boat);
        }
    }

    public void moveAllFactionBoat(){
        for (Faction faction : map.getLstFaction()){
            moveFactionBoat(faction);
        }
    }

    public void SeaRoutUpdate(Faction faction){
        ArrayList<SeaRout> lstSeaRouts = new ArrayList<>();
        for (SeaRout seaRout : faction.getLstSeaRouts()){
            seaRoutManager.sellAndPickUpAllResources(seaRout);
            if (!seaRout.available()){lstSeaRouts.add(seaRout);}
        }
        faction.getLstSeaRouts().removeAll(lstSeaRouts);
    }


    public void allSeaRoutUpdate(){
        for (Faction faction : map.getLstFaction()){
            SeaRoutUpdate(faction);
        }
    }


    public void chaseBoat(Boat boat, Boat chasedBoat){
        lstAttackBoat.add(new Boat[]{boat,chasedBoat});
        boat.setPath(new ArrayList<>(Collections.singleton(new GraphPoint(chasedBoat.getPosition(), ""))));
        boat.setIPath(0);
        boat.setContinuePath(false);
    }

    public void chaseUpdate(Boat[] tbBoat){
        double distance = Math.sqrt(Math.pow((tbBoat[1].getPosition().getX() - tbBoat[0].getPosition().getX()), 2) + Math.pow((tbBoat[1].getPosition().getY() - tbBoat[0].getPosition().getY()), 2));
        if(GameConfiguration.HITBOX_BOAT-5 >= distance){
            StartFight(tbBoat[0],tbBoat[1]);
            lstAttackBoat.remove(tbBoat);
            tbBoat[0].getPath().clear();
        }
        else if (tbBoat[0].getVisionRadius()+20000 < distance){
            lstAttackBoat.remove(tbBoat);
            tbBoat[0].getPath().clear();
        }
    }

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

    public Faction getMyFaction(String color){
        for (Faction faction : map.getLstFaction()) {
            if (faction.getColor().equals(color)) {
                return faction;
            }
        }
        return new Faction("");
    }

}
