package engine.battleengine.data;

import java.awt.*;

/**
 * Classe servant à la répresentation des boulets de canons tiré par les bateaux.
 * @author Gauthier Defrance
 * @version 1.0
 */
public class Bullet {

    private String color;
    private int tickAlive=0;
    private int speed;
    private double angle;
    private Point position;

    public Bullet(int x, int y, int speed, double angle, String color) {
        this.speed = speed;
        this.angle = angle;
        this.position = new Point(x, y);
        this.color = color;
    }

    public Bullet(Point position, int speed, double angle, String color) {
        this.speed = speed;
        this.angle = angle;
        this.position = position;
        this.color = color;
    }

    //---- Getters ----

    /**
     *
     * @return color {@link String} the color of the team from which the bullet belong.
     */
    public String getColor() {
        return color;
    }
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
    public double getAngle() {return angle;}

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
