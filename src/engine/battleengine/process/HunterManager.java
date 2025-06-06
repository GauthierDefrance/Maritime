package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;

import java.awt.*;

/**
 * Manager of the hunting behavior
 * @author Gauthier Defrance
 * @author Kenan Ammad
 * @version 1.0
 */
public class HunterManager {

    private final Battle battle;

    /**
     * Constructor of the HunterManager.
     * @param battle {@link Battle} the data class
     */
    public HunterManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Method that actualize the prey that the Boat chase.
     */
    public void ActualizeChase(){
        ActualizeChaseFleet(this.battle.getBoatsInBattleA(), this.battle.getBoatsInBattleB());
        ActualizeChaseFleet(this.battle.getBoatsInBattleB(), this.battle.getBoatsInBattleA());
    }

    /**
     * Method that actualize the chase of a {@link Fleet} chasing another {@link Fleet}.
     * @param hunterFleet {@link Fleet} the fleet of the hunters
     * @param preyFleet {@link Fleet} the fleet of the preys
     */
    private void ActualizeChaseFleet(Fleet hunterFleet, Fleet preyFleet){
        if(!preyFleet.getArrayListBoat().isEmpty()){
            Boat prey;
            for(Boat hunter : hunterFleet.getArrayListBoat()){
                prey = this.battle.getHunterPreyHashMap().get(hunter);
                if(prey==null){
                    this.battle.getHunterPreyHashMap().put(hunter,searchClosestPrey(hunter, preyFleet));
                } else if (prey.getCurrentHp()<1){
                    this.battle.getHunterPreyHashMap().put(hunter, null);
                }
            }
        }
    }

    /**
     * Method that find the closest prey for a hunter among a given {@link Fleet}.
     * @param hunter {@link Boat}
     * @param fleet {@link Fleet}
     * @return prey {@link Boat}
     */
    private Boat searchClosestPrey(Boat hunter, Fleet fleet) {
        Boat prey;
        Point hunterP = hunter.getPosition();
        double shortestDistance;
        double cdistance;

        if(!fleet.getArrayListBoat().isEmpty()){
            prey = fleet.getArrayListBoat().get(0); //On prend par défaut la première proie
            shortestDistance= prey.getPosition().distance(hunterP); //Calcul distance première proie / hunter

            for(Boat boat: fleet.getArrayListBoat()){
                cdistance = hunterP.distance(boat.getPosition()); //Calcule distance hunter / proie potentiel

                if(cdistance < shortestDistance){ //Si la distance calculé est plus courte on l'a choisie
                    shortestDistance = cdistance;
                    prey = boat;
                }
            }
            return prey;
        }
        return null;
    }


}
