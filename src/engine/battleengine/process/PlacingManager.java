package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;

import java.awt.*;

/**
 * Manager de classe utile pour gérer la partie placement des {@link Fleet}.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class PlacingManager {

    private final Battle battle;


    /**
     * Constructor of the PlacingManager.
     * @param battle {@link Battle} the data class
     */
    public PlacingManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Method that try to place a given Boat, return True if the Boat has been placed.
     * Else return False.
     * @param boat object of type {@link Boat}
     * @param point object of type {@link Point}
     * @return {@link Boolean}
     */
    public boolean placeBoat(Boat boat, Point point) {
        boat.setPosition(point);
        battle.getLstBoatsToPlace().remove(boat);
        battle.getLstBoatsCurrentlyBeingPlaced().add(boat);
        return true;
    }

    /**
     * Method that is used for canceling the placement of Boats.
     */
    public void cancelPlacing(){
        battle.getLstBoatsToPlace().addAll(battle.getLstBoatsCurrentlyBeingPlaced());
        for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced()){
            boat.setNextGraphPoint(null);
        }
        battle.getLstBoatsCurrentlyBeingPlaced().clear();
    }

    /**
     * Method that is used to confirm the end of the Placing phase.
     * @return Bool
     */
    public boolean confirmContinueBattle(){
        //potentielle modification des conditions
        if(!battle.getLstBoatsCurrentlyBeingPlaced().isEmpty() || !battle.getBoatsInBattleA().getArrayListBoat().isEmpty()){
            for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced() ){
                battle.getBoatsInBattleA().add(boat);
            }
            battle.getLstBoatsCurrentlyBeingPlaced().clear();
            this.battle.setPlacingMode(false);
            return true;
        }
        else {
            return false;
        }
    }




}
