package battleengine.process;

import battleengine.entity.Battle;
import battleengine.entity.Bullet;
import battleengine.factory.BulletFactory;
import battleengine.tools.AngleCalculator;
import config.GameConfiguration;
import engine.entity.boats.Boat;

import java.util.Random;

/**
 * Class that manage the shoots of the {@link Boat}
 * @author Gauthier Defrance
 * @version 0.3
 */
public class shootManager {

    private final Battle battle;

    public shootManager(Battle battle) {
        this.battle = battle;
    }

    public void tick(){

    }








    private boolean isShootable(Boat hunter, Boat prey){
        double angle = AngleCalculator.calculateAngle(hunter, prey);
        if(-GameConfiguration.DEFAULT_SHOOTING_ANGLE >angle &&
            angle> GameConfiguration.DEFAULT_SHOOTING_ANGLE-Math.PI){
            //Right
            return true;
        }
        if( GameConfiguration.DEFAULT_SHOOTING_ANGLE <angle &&
            angle< Math.PI-GameConfiguration.DEFAULT_SHOOTING_ANGLE){
            //Left
            return true;
        }
        return false;
    }


    /**
     * @param hunter
     * @param prey
     * @param angle
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
            bullet = BulletFactory.createBullet((int) x, (int) y, angle, hunter.getColor());
        }
        else{
            //shoot on the left side of the boat
            x = Math.cos(hunter.getAngle()+Math.PI/2)*GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN;
            y = Math.sin(hunter.getAngle()+Math.PI/2)*GameConfiguration.DEFAULT_WIDTH_BULLET_SPAWN + randomNumber;
            bullet = BulletFactory.createBullet((int) x, (int) y, angle, hunter.getColor());
        }
        this.battle.getLstBullets().add(bullet);
    }


    private boolean isReadyToShot(Boat boat){
        int tmp = boat.getDamageSpeed();
        if(tmp< this.battle.getReloadingHashMap().get(boat)){
            return true;
        }
        else {
            boat.setDamageSpeed(tmp+1);
            return false;
        }
    }






}
