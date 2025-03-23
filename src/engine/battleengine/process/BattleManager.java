package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.entity.boats.Boat;

/**
 * Manager of the Battle class, manage how the battle go
 * @author Gauthier Defrance
 * @version 0.3
 */
public class BattleManager {

    private final Battle battle;
    private final BulletManager bulletManager;
    private final PlacingManager placingManager;
    private final BattleBoatManager battleBoatManager;
    private final HunterManager hunterManager;
    private final ShootManager shotManager;

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
    }

    public PlacingManager getPlacingManager() {return placingManager;}
    public BattleBoatManager getBattleBoatManager() {return battleBoatManager;}
    public BulletManager getBulletManager() {return bulletManager;}


    /**
     * Method that "tick" the BattleManager,
     * it synchronizes the battle.
     */
    public void tick(){
        if(!battle.isInPlacingMode()){
            hunterManager.ActualizeChase();
            battleBoatManager.tick();
            bulletManager.tick();
            shotManager.tick();
        }
    }


    public boolean battleEnded(){
        if(battle.getBoatsInBattleA().getArrayListBoat().isEmpty()&&battle.getLstBoatsToPlace().isEmpty()&&!battle.isInPlacingMode()){
            //Lose
            return true;
        }
        else if(battle.getBoatsInBattleB().getArrayListBoat().isEmpty()){
            //win
            return true;
        }
        return false;
    }

    public boolean battleWon(){
        return battle.getBoatsInBattleB().getArrayListBoat().isEmpty();
    }

    public void battleEnd(){
        for(Boat boat : battle.getTeamA().getArrayListBoat()) {
        }

        for(Boat boat : battle.getTeamB().getArrayListBoat()) {
        }
    }

}
