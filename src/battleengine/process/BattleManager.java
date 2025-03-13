package battleengine.process;

import battleengine.entity.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.process.BoatManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manager de classe servant à orchestrer le bon déroulement d'une bataille.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class BattleManager {

    private Battle battle;
    private BulletManager bulletManager;
    private PlacingManager placingManager;

    public BattleManager(Fleet fleetA, Fleet fleetB) {
        this.battle = new Battle(fleetA, fleetB);
        this.placingManager = new PlacingManager(battle);
        this.bulletManager = new BulletManager(battle);
    }

    public void tick(){
        if(!this.battle.isInPlacingMode()){
            this.bulletManager.tick();
        }
    }

//    public ArrayList<Boat> getAllBoatsOnPoint(Point point){
//        //return getBoatsAOnPoint(point).addAll(getBoatsBOnPoint(point));
//    }

    public ArrayList<Boat> getBoatsAOnPoint(Point point){
        return BoatManager.boatCollisionToPoint(point,this.battle.getBoatsInBattleA().getArrayListBoat());
    }

    public ArrayList<Boat> getBoatsBOnPoint(Point point){
        return BoatManager.boatCollisionToPoint(point,this.battle.getBoatsInBattleB().getArrayListBoat());
    }

    public Battle getBattle() {
        return battle;
    }


}
