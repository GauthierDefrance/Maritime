package maritime.engine.process;


import maritime.config.GameConfiguration;
import maritime.config.GameInitFactory;
import maritime.engine.entity.boats.Boat;
import maritime.engine.faction.Faction;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * Classe FactionManager
 * @version 0.2
 */

public class FactionManager {
    private final GameInitFactory map;
    private final BoatManager boatManager;
    private final HarborManager harborManager;
    private ArrayList<Boat> lstAttackBoat;

    public FactionManager(GameInitFactory map) {
        this.map = map;
        this.boatManager = new BoatManager(map);
        this.harborManager = new HarborManager(map);
        this.lstAttackBoat = new ArrayList<>();
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

    public void attackBoat(Boat boat, Boat attackBoat){
        lstAttackBoat.add(boat);
        boatManager.attack(boat,attackBoat);
    }

    public void doYouStartFight(Boat boat){
        if(GameConfiguration.HITBOX_BOAT-5 >= Math.sqrt(Math.pow((boat.getAttack().getPosition().getX()-boat.getPosition().getX()),2)+Math.pow((boat.getAttack().getPosition().getY()-boat.getPosition().getY()),2))){
            StartFight(boat,boat.getAttack());
            lstAttackBoat.remove(boat);
            boat.setAttack(null);
            boat.getPath().clear();
        }
    }

    public void doYouAllStartFight(){
        ArrayList<Boat> lstAttackBoatTemp = new ArrayList<>();
        lstAttackBoatTemp.addAll(lstAttackBoat);
        for (Boat boat : lstAttackBoatTemp){
                doYouStartFight(boat);
        }
    }
    public void StartFight(Boat boat1,Boat boat2){
        ArrayList<Boat> vision1 = new ArrayList<>();
        ArrayList<Boat> vision2 = new ArrayList<>();
        vision1.add(boat1);
        vision2.add(boat2);
        for (Boat boat : boatManager.getMyFaction(boat1).getLstBoat()){
            for (Boat playerBoat : map.getPlayer().getLstBoat()){
                if(playerBoat.getVisionRadius() /2 >= Math.sqrt(Math.pow((boat.getPosition().getX()-playerBoat.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-playerBoat.getPosition().getY()),2))){
                    if(!vision1.contains(boat)){vision1.add(boat);}
                }
            }
        }
        for (Boat boat : boatManager.getMyFaction(boat2).getLstBoat()){
            for (Boat playerBoat : map.getPlayer().getLstBoat()){
                if(playerBoat.getVisionRadius() /2 >= Math.sqrt(Math.pow((boat.getPosition().getX()-playerBoat.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-playerBoat.getPosition().getY()),2))){
                    if(!vision2.contains(boat)){vision2.add(boat);}
                }
            }
        }
        JOptionPane.showMessageDialog(null, vision1+"vs"+vision2);
    }

}
