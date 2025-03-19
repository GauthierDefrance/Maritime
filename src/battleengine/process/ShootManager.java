package battleengine.process;

import battleengine.entity.Battle;
import battleengine.entity.Bullet;
import battleengine.factory.BulletFactory;
import battleengine.tools.AngleCalculator;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.util.ArrayList;
import java.util.Random;

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
                if(tmp != null) {
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
        if (hunter.getPosition().distance(prey.getPosition()) < GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/2) {

            double angle = AngleCalculator.calculateAngle(hunter, prey);
            double deltaAngle = angle - hunter.getAngle();
            deltaAngle = (deltaAngle + Math.PI) % (2 * Math.PI) - Math.PI;

            double minAngle = (GameConfiguration.DEFAULT_MIN_SHOOTING_ANGLE);
            double maxAngle = (GameConfiguration.DEFAULT_MAX_SHOOTING_ANGLE);

            if ((maxAngle>deltaAngle && deltaAngle > minAngle)){
                return true;
            }
            if ((-maxAngle < deltaAngle  && deltaAngle < -minAngle)) {
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
        Bullet bullet;
        double angle = AngleCalculator.calculateAngle(hunter, prey);
        double deltaAngle = angle - hunter.getAngle();
        deltaAngle = (deltaAngle + Math.PI) % (2 * Math.PI) - Math.PI;

        int min = -GameConfiguration.DEFAULT_HEIGHT_BULLET_SPAWN;
        int max = GameConfiguration.DEFAULT_HEIGHT_BULLET_SPAWN+1;
        int randomNumber = GameConfiguration.rand.nextInt(max - min) + min;
        double x;
        double y;
        if(angle<0){
            //Shoot on the right side of the boat
            x = Math.cos(hunter.getAngle()-Math.PI/2)*(GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN+ randomNumber);
            y = Math.sin(hunter.getAngle()-Math.PI/2)*(GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN + randomNumber);
        }
        else{
            //shoot on the left side of the boat
            x = Math.cos(hunter.getAngle()+Math.PI/2)*(GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN + randomNumber);
            y = Math.sin(hunter.getAngle()+Math.PI/2)*(GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN + randomNumber);
        }
        bullet = BulletFactory.createBullet((int) (x+hunter.getPosition().getX()), (int) (y+hunter.getPosition().getY()), angle, hunter.getColor());
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
     * @return  Boolean
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
