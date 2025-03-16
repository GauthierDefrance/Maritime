package battleengine.process;

import battleengine.entity.Battle;
import battleengine.entity.Bullet;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.process.BoatManager;

import java.util.ArrayList;

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
        collideAll();
        actualizeBullets();
    }

    /**
     * Method that move and decelerate at the same time all the bullets.
     */
    public void moveAllBullets() {
        for(Bullet bullet: this.battle.getLstBullets()) {
            moveBullet(bullet);
            decelerateBullet(bullet);
        }
    }

    /**
     * Method that move a Bullet
     * @param bullet {@link Bullet}
     */
    private void moveBullet(Bullet bullet) {
        int xo = (int) bullet.getPosition().getX();
        int yo = (int) bullet.getPosition().getY();
        int x = (int) (Math.cos(bullet.getAngle())*bullet.getSpeed());
        int y = (int) (Math.sin(bullet.getAngle())*bullet.getSpeed());
        bullet.getPosition().move(x+xo, y+yo);
    }

    /**
     * method that reduce the speed of a bullet and increase the counter of it being alive
     * @param bullet {@link Bullet}
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
        ArrayList<Bullet> bullets = new ArrayList<>();
        for(Bullet bullet: this.battle.getLstBullets()) {
            bullets.add(bulletKiller(bullet));
        }
        for(Bullet bullet: bullets) {
            this.battle.getLstBullets().remove(bullet);
        }
    }

    /**
     * Method that kill Bullet from which the speed is too slow
     * @param bullet {@link Bullet}
     */
    public Bullet bulletKiller(Bullet bullet) {
        if(bullet.getSpeed()<0){
            //Ajouter ici Plouf pop up !!!
            return bullet;
        }
        return null;
    }

    /**
     * Method that check the collision of the bullet with
     * all the boats in Battle.
     */
    public void collideAll(){
        for(Bullet bullet: this.battle.getLstBullets()) {
            collide(bullet,this.battle.getBoatsInBattleA());
        }
        for(Bullet bullet: this.battle.getLstBullets()) {
            collide(bullet,this.battle.getBoatsInBattleA());
        }
    }

    public void collide(Bullet bullet, Fleet fleet) {
        Boat tmp = getBulletCollideFirstBoat(bullet, fleet);
        if (tmp != null) {
            tmp.setCurrentHp(tmp.getCurrentHp()-GameConfiguration.DAMAGE_PER_BULLET);
            bullet.setSpeed(-1);
            //Afficher explosion là ou était la balle
        }
    }


    /**
     * Return an array list of boat that collide
     * Check for friendly fire.
     * @param bullet {@link Bullet}
     * @param fleet {@link Fleet}
     * @return {@link Boat}
     */
    public Boat getBulletCollideFirstBoat(Bullet bullet, Fleet fleet) {
        Boat result=null;
        ArrayList<Boat> tmp = BoatManager.boatCollisionToPoint(bullet.getPosition(), fleet.getArrayListBoat());
        int index = 0;
        if(!tmp.isEmpty()) {
            while(result==null && index<tmp.size()) {
                if(tmp.get(index).getColor().equals(bullet.getColor())){
                    result = tmp.get(index);
                }
                index++;
            }
        }
        return result;
    }


}
