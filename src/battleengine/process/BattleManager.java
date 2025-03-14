package battleengine.process;

import battleengine.entity.Battle;
import engine.entity.boats.Boat;
import engine.process.BoatManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manager of the Battle class, manage how the battle go
 * @author Gauthier Defrance
 * @version 0.3
 */
public class BattleManager {

    private Battle battle;
    private BulletManager bulletManager;
    private PlacingManager placingManager;
    private BattleBoatManager battleBoatManager;

    public BattleManager(Battle battle) {
        this.battle = new Battle(battle.getTeamA(), battle.getTeamB());
        this.placingManager = new PlacingManager(battle);
        this.bulletManager = new BulletManager(battle);
        this.battleBoatManager = new BattleBoatManager(battle);
    }

    public PlacingManager getPlacingManager() {return placingManager;}
    public BattleBoatManager getBattleBoatManager() {return battleBoatManager;}
    public BulletManager getBulletManager() {return bulletManager;}

    public ArrayList<Boat> getBoatsAOnPoint(Point point){
        return BoatManager.boatCollisionToPoint(point,this.battle.getBoatsInBattleA().getArrayListBoat());
    }
    public ArrayList<Boat> getBoatsBOnPoint(Point point){
        return BoatManager.boatCollisionToPoint(point,this.battle.getBoatsInBattleB().getArrayListBoat());
    }


    public void tick(){
        if(!this.battle.isInPlacingMode()){
            this.bulletManager.tick();
        }
    }




}
