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

    private final Battle battle;


    /**
     * Constructor of the PlacingManager.
     * @param battle {@link Battle} the data class
     */
    public PlacingManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Method that try to place the current Boat, return True if
     * the Boat has been placed. Else return False.
     * @param point object of type {@link Point}
     * @return {@link Boolean}
     */
    public boolean tryPlaceCurrentBoat(Point point) {
        if(tryPlaceBoat(battle.getCurrentBoat(),point)){
            battle.setCurrentBoat(null);
            return true;
        }
        return false;
    }

    /**
     * Method that try to place a given Boat, return True if the Boat has been placed.
     * Else return False.
     * @param boat object of type {@link Boat}
     * @param point object of type {@link Point}
     * @return {@link Boolean}
     */
    public boolean tryPlaceBoat(Boat boat, Point point) {
        if(battle.getSpawnzone().isPlaceable(boat)){
            boat.setPosition(point);
            battle.getLstBoatsToPlace().remove(boat);
            battle.getLstBoatsCurrentlyBeingPlaced().add(boat);
            return true;
        }
        return false;
    }

    /**
     * Method that is used for canceling the placement of Boats.
     */
    public void cancelPlacing(){
        battle.getLstBoatsToPlace().addAll(battle.getLstBoatsCurrentlyBeingPlaced());
        clearBoatsCurrentlyBeingPlaced();
    }

    /**
     * Method that is used to confirm the end of the Placing phase.
     */
    public void confirmContinueBattle(){
        //potentielle modification des conditions
        if(!battle.getLstBoatsCurrentlyBeingPlaced().isEmpty() || !battle.getBoatsInBattleA().getArrayListBoat().isEmpty()){
            for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced() ){
                battle.getBoatsInBattleA().add(boat);
            }
            clearBoatsCurrentlyBeingPlaced();
        }
    }

    /**
     * Void function that clear the Lst of boats that the player is placing.
     */
    private void clearBoatsCurrentlyBeingPlaced(){
        battle.getLstBoatsCurrentlyBeingPlaced().clear();
    }




}
