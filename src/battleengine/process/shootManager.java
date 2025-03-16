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
public class shootManager {

    private final Battle battle;

    /**
     *
     * @param battle {@link Battle}
     */
    public shootManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Reload the gun of each boats
     * Then try to make each boats shoot.
     */
    public void tick(){
        reloadAllBoats();
        shootFleet(this.battle.getBoatsInBattleA(), this.battle.getBoatsInBattleB());
        shootFleet(this.battle.getBoatsInBattleB(), this.battle.getBoatsInBattleA());
    }


    /**
     * Method that make all the boats reloads the guns.
     */
    private void reloadAllBoats(){
        for(Boat boat :this.battle.getReloadingHashMap().keySet()){
            reload(boat);
        }
    }

    /**
     * Make all the boat in a fleet try to shoot another fleet.
     * @param fleet {@link Fleet}
     * @param preyFleet {@link Fleet}
     */
    private void shootFleet(Fleet fleet, Fleet preyFleet) {
        Boat tmp;
        for (Boat hunter : fleet.getArrayListBoat()) {
            tmp = this.battle.getHunterPreyHashMap().get(hunter);
            if (tmp != null) {
                //prioirité à la proie
                tryshoot(hunter, tmp);
            } else if (isReadyToShot(hunter)) {
                //priorité au plus proche
                tmp = getShootableFirstBoat(hunter, preyFleet);
                shoot(hunter, tmp, AngleCalculator.calculateAngle(hunter, tmp));
            }
        }
    }

    /**
     * Try to make a hunter shoot a prey.
     * @param hunter {@link Boat}
     * @param prey {@link Boat}
     */
    private void tryshoot(Boat hunter, Boat prey){
        double angle = 0;
        if(isShootable(hunter, prey, angle)&&isReadyToShot(hunter)){
            shoot(hunter, prey, angle);
            this.battle.getReloadingHashMap().put(hunter,0);
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
        while(result==null){
            tmp = boats.get(index);
            if(isShootable(hunter,tmp, 0)){
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
     * @param angle {@link Double}
     * @return {@link Boolean}
     */
    private boolean isShootable(Boat hunter, Boat prey, double angle) {
        if (hunter.getPosition().distance(prey.getPosition())<GameConfiguration.DEFAULT_SHOOT_DISTANCE){
            //If the enemy is at close distance
            angle = AngleCalculator.calculateAngle(hunter, prey);
            if (-GameConfiguration.DEFAULT_SHOOTING_ANGLE > angle &&
                    angle > GameConfiguration.DEFAULT_SHOOTING_ANGLE - Math.PI) {
                //Right
                return true;
            }
            //Left
            return GameConfiguration.DEFAULT_SHOOTING_ANGLE < angle &&
                    angle < Math.PI - GameConfiguration.DEFAULT_SHOOTING_ANGLE;
        }
        return false;
    }


    /**
     *
     * @param hunter {@link Boat}
     * @param prey {@link Boat}
     * @param angle {@link Double}
     */
    private void shoot(Boat hunter, Boat prey, double angle){
        Bullet bullet;
        Random rand = new Random();
        int min = -GameConfiguration.DEFAULT_HEIGHT_BULLET_SPAWN;
        int max = GameConfiguration.DEFAULT_HEIGHT_BULLET_SPAWN+1;
        int randomNumber = rand.nextInt(max - min) + min;
        double x;
        double y;
        if(angle<0){
            //Shoot on the right side of the boat
            x = Math.cos(hunter.getAngle()-Math.PI/2)*GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN;
            y = Math.sin(hunter.getAngle()-Math.PI/2)*GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN + randomNumber;
        }
        else{
            //shoot on the left side of the boat
            x = Math.cos(hunter.getAngle()+Math.PI/2)*GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN;
            y = Math.sin(hunter.getAngle()+Math.PI/2)*GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN + randomNumber;
        }
        bullet = BulletFactory.createBullet((int) x, (int) y, angle, hunter.getColor());
        this.battle.getLstBullets().add(bullet);
    }


    /**
     * Method that check if a boat has reloaded his gun.
     * @param boat {@link Boat}
     * @return {@link Boolean}
     */
    private boolean isReadyToShot(Boat boat){
        int tmp = boat.getDamageSpeed();
        return tmp < this.battle.getReloadingHashMap().get(boat);
    }


    /**
     * Method that try to reload a gun.
     * @param boat {@link Boat}
     */
    private void reload(Boat boat){
        int tmp = boat.getDamageSpeed();
        int result = this.battle.getReloadingHashMap().get(boat);
        if(!(tmp < result)){
            this.battle.getReloadingHashMap().put(boat, result+1);
        }
    }

}
