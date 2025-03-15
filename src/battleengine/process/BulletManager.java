package battleengine.process;

import battleengine.entity.Battle;
import battleengine.entity.Bullet;
import config.GameConfiguration;

/**
 * Manager de classe utile pour gérer la partie déplacement et collisions des {@link Bullet}.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class BulletManager {

    private final Battle battle;

    /**
     * Constructor of the BulletManager.
     * @param battle {@link Battle} the data class
     */
    public BulletManager(Battle battle) {
        this.battle = battle;
    }

    /**
     * Method that "tick" the BulletManager,
     * moving the Bullets and remove the dead bullets.
     */
    public void tick(){
        moveAllBullets();
        actualizeBullets();
    }

    /**
     *
     */
    public void moveAllBullets() {
        for(Bullet bullet: this.battle.getLstBullets()) {
            moveBullet(bullet);
            decelerateBullet(bullet);
        }
    }

    /**
     *
     * @param bullet
     */
    private void moveBullet(Bullet bullet) {
        int xo = (int) bullet.getPosition().getX();
        int yo = (int) bullet.getPosition().getY();
        int x = (int) Math.cos(bullet.getAngle())*bullet.getSpeed();
        int y = (int) Math.sin(bullet.getAngle())*bullet.getSpeed();
        bullet.getPosition().move(x+xo, y+yo);
    }

    /**
     *
     * @param bullet
     */
    private void decelerateBullet(Bullet bullet) {
        int newSpeed = (int) ( bullet.getSpeed()- Math.pow(GameConfiguration.DEFAULT_BULLET_FRICTION, bullet.getTickAlive()));
        bullet.setTickAlive(bullet.getTickAlive()+1);
        bullet.setSpeed(newSpeed);
    }

    /**
     * Methode that remove all the dead bullets from whom the speed is too slow
     */
    public void actualizeBullets() {
        for(Bullet bullet: this.battle.getLstBullets()) {
            bulletKiller(bullet);
        }
    }

    /**
     * Method that kill Bullet from which the speed is too slow
     * @param bullet
     */
    public void bulletKiller(Bullet bullet) {
        if(bullet.getSpeed()<0){
            this.battle.getLstBullets().remove(bullet);
            //Ajouter ici Plouf pop up !!!
        }
    }


}
