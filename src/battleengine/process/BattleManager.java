package battleengine.process;

import battleengine.entity.Battle;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.process.BoatManager;

import java.awt.*;
import java.util.ArrayList;

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
            hunterManager.ActualizeChase(); // DANGEREUX /!\
            battleBoatManager.tick();
            bulletManager.tick();
            shotManager.tick();
        }
    }


    

}
