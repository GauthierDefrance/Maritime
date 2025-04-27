package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.data.entity.boats.Boat;

import java.util.ArrayList;

/**
 * Manager of the Battle class, manage how the battle go
 * @author Gauthier Defrance
 * @author Kenan Ammad
 * @version 1.0
 */
public class BattleManager {

    private final Battle battle;
    private final BulletManager bulletManager;
    private final PlacingManager placingManager;
    private final BattleBoatManager battleBoatManager;
    private final HunterManager hunterManager;
    private final ShootManager shotManager;
    private final BattleEndManager battleEndManager;

    /**
     * Constructor of the BattleManager.
     * @param battle {@link Battle} the data class
     */
    public BattleManager(Battle battle) {
        this.battle = battle;
        this.shotManager = new ShootManager(battle);
        this.placingManager = new PlacingManager(battle);
        this.bulletManager = new BulletManager(battle);
        this.battleBoatManager = new BattleBoatManager(battle);
        this.hunterManager = new HunterManager(battle);
        this.battleEndManager = new BattleEndManager(battle);
    }

    public PlacingManager getPlacingManager() {return placingManager;}
    public BattleBoatManager getBattleBoatManager() {return battleBoatManager;}
    public BulletManager getBulletManager() {return bulletManager;}
    public ArrayList<String> battleEnd(){
        return battleEndManager.actualizeOriginalFleet();
    }

    /**
     * Method that "tick" the BattleManager,
     * it synchronizes the battle.
     */
    public void tick(){
        if(!battle.isInPlacingMode() && !battleEnded() ){
            hunterManager.ActualizeChase();
            battleBoatManager.tick();
            bulletManager.tick();
            shotManager.tick();
        }
    }

    /**
     * Method the check if the battle has ended, who ever has won.
     * @return Bool
     */
    public boolean battleEnded(){
        if(battleEndManager.playerLose()){
            //Lose
            return true;
        }
        else if(battleEndManager.playerWin()){
            //win
            return true;
        }
        return false;
    }

    /**
     * Method that launch a fake battle not showing on the screen.
     * @param battle
     */
    public static void fakeBattle(Battle battle){
        if(battle.getLstBoatsToPlace() !=null) {
            battle.getBoatsInBattleA().getArrayListBoat().addAll(battle.getLstBoatsToPlace());
            battle.getLstBoatsToPlace().clear();
        }
        int a = 0;
        int b = 0;
        for (Boat boat : battle.getBoatsInBattleA().getArrayListBoat()){
            a += boat.getCurrentHp()*boat.getDamageSpeed()*boat.getSpeed();
        }
        for (Boat boat : battle.getBoatsInBattleB().getArrayListBoat()){
            b += boat.getCurrentHp()*boat.getDamageSpeed()*boat.getSpeed();
        }
        if(a>b) {
            battle.getDeadBoatsB().getArrayListBoat().addAll(battle.getBoatsInBattleB().getArrayListBoat());
            battle.getBoatsInBattleB().getArrayListBoat().clear();
        }
        else {
            battle.getDeadBoatsA().getArrayListBoat().addAll(battle.getBoatsInBattleA().getArrayListBoat());
            battle.getBoatsInBattleA().getArrayListBoat().clear();
        }
        BattleEndManager endManager = new BattleEndManager(battle);
        endManager.actualizeOriginalFleet();
    }

}
