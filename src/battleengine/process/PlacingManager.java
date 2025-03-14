package battleengine.process;


import battleengine.entity.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;

/**
 * Manager de classe utile pour gérer la partie placement des {@link Fleet}.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class PlacingManager {

    private Battle battle;
    
    public PlacingManager(Battle battle) {
        this.battle = battle;
    }

    public boolean tryPlaceCurrentBoat(Point point) {
        if(tryPlaceBoat(battle.getCurrentBoat(),point)){
            battle.setCurrentBoat(null);
            return true;
        }
        return false;
    }

    public boolean tryPlaceBoat(Boat boat, Point point) {
        if(battle.getSpawnzone().isPlaceable(boat)){
            boat.setPosition(point);
            battle.getLstBoatsToPlace().remove(boat);
            battle.getLstBoatsCurrentlyBeingPlaced().add(boat);
            return true;
        }
        return false;
    }

    public void cancelPlacing(){
        battle.getLstBoatsToPlace().addAll(battle.getLstBoatsCurrentlyBeingPlaced());
        clearBoatsCurrentlyBeingPlaced();
    }

    public void confirmContinueBattle(){
        if(!battle.getLstBoatsCurrentlyBeingPlaced().isEmpty() || !battle.getBoatsInBattleA().getArrayListBoat().isEmpty()){
            for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced() ){
                battle.getBoatsInBattleA().add(boat);
            }
            clearBoatsCurrentlyBeingPlaced();
        }
    }

    private void clearBoatsCurrentlyBeingPlaced(){
        battle.getLstBoatsCurrentlyBeingPlaced().clear();
    }




}
