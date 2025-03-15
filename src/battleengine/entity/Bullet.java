package battleengine.entity;

import java.awt.*;

/**
 * Classe servant à la répresentation des boulets de canons tiré par les bateaux.
 * @author Gauthier Defrance
 * @version 0.3
 */
public class Bullet {

    private int tickAlive=0;
    private int speed;
    private int angle;
    private Point position;

    public Bullet(int x, int y, int speed, int angle) {
        this.speed = speed;
        this.angle = angle;
        this.position = new Point(x, y);
    }

    public Bullet(Point position, int speed, int angle) {
        this.speed = speed;
        this.angle = angle;
        this.position = position;
    }

    //---- Getters ----

    /**
     * Récupère la vitesse.
     *
     * @return la vitesse actuelle
     */
    public int getSpeed() {return speed;}

    /**
     * Récupère l'angle.
     *
     * @return l'angle actuel
     */
    public int getAngle() {return angle;}

    /**
     * Récupère la position.
     *
     * @return la position actuelle sous forme d'un objet Point
     */
    public Point getPosition() {return position;}


    //---- Setters ----

    /**
     * Définit la vitesse.
     *
     * @param speed la nouvelle valeur de la vitesse
     */
    public void setSpeed(int speed) {this.speed = speed;}

    /**
     * Définit l'angle.
     *
     * @param angle la nouvelle valeur de l'angle
     */
    public void setAngle(int angle) {this.angle = angle;}

    /**
     * Définit la position.
     *
     * @param position la nouvelle position sous forme d'un objet Point
     */
    public void setPosition(Point position) {this.position = position;}


    public int getTickAlive() {
        return tickAlive;
    }

    public void setTickAlive(int tickAlive) {
        this.tickAlive = tickAlive;
    }
}
