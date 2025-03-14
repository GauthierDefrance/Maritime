package battleengine.process;

import battleengine.entity.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;

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

    public void tick(){

    }

    public void moveAllBoats(){
        movefleet(this.battle.getBoatsInBattleA());
        movefleet(this.battle.getBoatsInBattleB());
    }

    private static void movefleet(Fleet fleet){
        for(Boat boat : fleet.getArrayListBoat()){
            moveBoat(boat);
        }
    }

    private static void moveBoat(Boat boat){
        Point point = boat.getPosition();
        double x1 = point.getX();
        double y1 = point.getY();
        double x2 = boat.getPosition().getX();
        double y2 = boat.getPosition().getY();
        double distance = boat.getPosition().distance(point);


    }


}
