package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.battleengine.data.Bullet;
import engine.battleengine.utilities.AngleCalculator;
import config.GameConfiguration;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.process.creational.EngineBuilder;

import java.util.ArrayList;

/**
 * Class that manage the shoots of the {@link Boat}
 * @author Gauthier Defrance
 * @version 0.3
 */
public class ShootManager {

    private final Battle battle;

    /**
     *
     * @param battle {@link Battle}
     */
    public ShootManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Reload the gun of each boats
     * Then try to make each boats shoot.
     */
    public void tick(){
        shootFleet(this.battle.getBoatsInBattleA(), this.battle.getBoatsInBattleB());
        shootFleet(this.battle.getBoatsInBattleB(), this.battle.getBoatsInBattleA());
    }



    /**
     * Make all the boat in a fleet try to shoot another fleet.
     * @param hunterFleet {@link Fleet}
     * @param preyFleet {@link Fleet}
     */
    private void shootFleet(Fleet hunterFleet, Fleet preyFleet) {
        Boat tmp;
        for (Boat hunter : hunterFleet.getArrayListBoat()) {
            if (isReadyToShot(hunter)){
                tmp = this.battle.getHunterPreyHashMap().get(hunter);
                if(tmp != null&&isShootable(hunter,tmp)) {
                    //prioirité à la proie
                    tryshoot(hunter, tmp);
                }
                else{
                    //priorité au plus proche
                    tmp = getShootableFirstBoat(hunter, preyFleet);
                    if (tmp != null) {
                        shoot(hunter, tmp);
                    }
                }
            }
        }
    }

    /**
     * Try to make a hunter shoot a prey.
     * @param hunter {@link Boat}
     * @param prey {@link Boat}
     */
    private void tryshoot(Boat hunter, Boat prey){
        if(isShootable(hunter, prey)){
            shoot(hunter, prey);
        }
    }


    /**
     * Get the first shootable boat in a Fleet
     * @param hunter {@link Boat}
     * @param fleet {@link Fleet}
     * @return {@link Boat}
     */
    private Boat getShootableFirstBoat(Boat hunter, Fleet fleet){
        Boat result=null;
        Boat tmp;
        ArrayList<Boat> boats = fleet.getArrayListBoat();
        int index = 0;
        while(result==null && index<boats.size()){
            tmp = boats.get(index);
            if(isShootable(hunter,tmp)){
                result=tmp;
            }
            index++;
        }
        return result;
    }


    /**
     * Check if a hunter can shoot a prey.
     * => The prey is in range and in the correct angle.
     * @param hunter {@link Boat}
     * @param prey {@link Boat}
     * @return {@link Boolean}
     */
    private boolean isShootable(Boat hunter, Boat prey) {
        if (hunter.getPosition().distance(prey.getPosition())-(GameConfiguration.HITBOX_BOAT/2) < GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/2) {
            double deltaAngle = AngleCalculator.calculateDeltaAngle(hunter,prey.getPosition());
            if ((GameConfiguration.DEFAULT_MAX_SHOOTING_ANGLE>deltaAngle && deltaAngle > GameConfiguration.DEFAULT_MIN_SHOOTING_ANGLE)){
                return true;
            }
            if ((-GameConfiguration.DEFAULT_MAX_SHOOTING_ANGLE < deltaAngle  && deltaAngle < -GameConfiguration.DEFAULT_MIN_SHOOTING_ANGLE)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param hunter {@link Boat}
     * @param prey {@link Boat}
     */
    private void shoot(Boat hunter, Boat prey){
        double angle = AngleCalculator.calculateAngle(hunter.getPosition(), prey.getPosition());
        Bullet bullet = EngineBuilder.createBullet((int) (hunter.getPosition().getX()), (int) (hunter.getPosition().getY()), angle, hunter.getColor());
        if(this.battle.getBoatsInBattleA().getArrayListBoat().contains(hunter)){
            this.battle.getLstBulletsteamA().add(bullet);
        }
        else {
            this.battle.getLstBulletsteamB().add(bullet);
        }
    }


    /**
     * Method that check if a boat has reloaded his gun.
     * @param boat
     * @return Boolean
     */
    private boolean isReadyToShot(Boat boat){
        int result = this.battle.getReloadingHashMap().get(boat);
        if(boat.getDamageSpeed() > result){
            this.battle.getReloadingHashMap().put(boat, GameConfiguration.RELOAD_TIME);
            return true;
        }
        else {
            this.battle.getReloadingHashMap().put(boat, result-1);
            return false;
        }
    }

}
