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
            lstAttackBoat.remove(boat);
            boat.setAttack(null);
            boat.getPath().clear();
            JOptionPane.showMessageDialog(null, "test");
        }
    }

    public void doYouAllStartFight(){
        ArrayList<Boat> lstAttackBoatTemp = new ArrayList<>();
        lstAttackBoatTemp.addAll(lstAttackBoat);
        for (Boat boat : lstAttackBoatTemp){
                doYouStartFight(boat);
            }
    }


}
