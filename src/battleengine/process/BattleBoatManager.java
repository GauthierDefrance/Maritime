package battleengine.process;

import battleengine.entity.Battle;
import battleengine.tools.CirclePursuit;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;
import java.util.HashMap;

/**
 * Class the manage the movement and operations of the {@link Boat}
 * @author Gauthier Defrance
 * @version 0.3
 */
public class BattleBoatManager {

    private Battle battle;

    public BattleBoatManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Method that "tick" the BattleBoatManager,
     * moving the boats and actualizing the angle.
     */
    public void tick(){
        actualizeFleetAngle(this.battle.getBoatsInBattleA());
        actualizeFleetAngle(this.battle.getBoatsInBattleB());
        moveForwardFleet(this.battle.getBoatsInBattleA());
        moveForwardFleet(this.battle.getBoatsInBattleB());
    }


    /**
     * Method that actualize the angle of all the {@link Boat} of a {@link Fleet}.
     * If it was given a position to move to, it will turn in direction of the point.
     * Else if it has a prey, it will follow the prey.
     * @param fleet A {@link Fleet} object
     */
    public void actualizeFleetAngle(Fleet fleet){
        HashMap<Boat,Boat> HunterPreyHashMap = this.battle.getHunterPreyHashMap();
        Boat prey;
        for(Boat hunter : fleet.getArrayListBoat()){
            if(hunter.getNextGraphPoint()!=null){
                //Si un point précis a été sélectionné par le joueur
                turnBoat(hunter, hunter.getNextGraphPoint().getPoint());
            }
            else {
                //Sinon, on essaye de traquer une proie si elle existe
                prey = HunterPreyHashMap.get(hunter);
                if(prey!=null){
                    turnBoat(hunter, CirclePursuit.getPointToFollow(hunter,prey));
                }
            }
        }
    }

    /**
     * Method that turn a boat with his turn speed in direction of a given point.
     * @param boat A {@link Boat} object
     * @param point A {@link Point} object
     */
    private static void turnBoat(Boat boat, Point point){
        double x1 = point.getX();
        double y1 = point.getY();
        double x2 = boat.getPosition().getX();
        double y2 = boat.getPosition().getY();
        double angle = Math.atan2(y1 - y2, x1 - x2);
        if(angle>=0){
            boat.setAngle(boat.getAngle() - GameConfiguration.BOAT_ROTATION_SPEED);
        } else {
            boat.setAngle(boat.getAngle() + GameConfiguration.BOAT_ROTATION_SPEED);
        }

    }

    private static void moveForwardFleet(Fleet fleet){
        for(Boat boat : fleet.getArrayListBoat()){
            moveForward(boat);
        }
    }

    private static void moveForward(Boat boat){
        int x = (int) Math.cos(boat.getAngle())*boat.getSpeed();
        int y = (int) Math.sin(boat.getAngle()*boat.getSpeed());
        boat.setPosition(x,y);
    }


}
