package maritime.entity;

import java.awt.*;
import maritime.graph.GraphPoint;

/**
 * Classe entité
 * @version 0.1
 */
public abstract class Boat extends Entity{
    private int speed;
    private double angle;
    /**
     * Constructeur de la classe Boat.
     * @param name
     * @param visionRadius
     * @param maxHp
     * @param position
     * @param idModel
     * @param speed
     */
    public Boat(String name, int visionRadius, int maxHp, Point position, String idModel, int speed) {
        super(name, visionRadius, maxHp, position, idModel);
        this.speed = speed;
    }

    //static public Entity create(String name, int visionRadius, int maxHp, Point position, String idModel) {
    //    return new Boat(name, visionRadius, maxHp, position, idModel);
    //}


    /*
     * Méthode qui indique si le bateau se trouve actuellement sur un point du Graphe.
     * @param GraphPoint p
     * @return Bool
     */
    public boolean isOn(GraphPoint p){
        return p.getX()==((int) getPosition().getX()) && p.getY()==((int) getPosition().getY());
     }


    /*
     * Méthode qui déplace le bateau selon sa vitesse en direction du point donnée.
     * Si il se trouve sur le point donnée ou sa vitesse est plus grande que le point donnée,
     * le bateau prend les coordonnées du point.
     * Utilise la trigonométrie pour fonctionner.
     * @param GraphPoint p
     */
    public void approach(GraphPoint p){
        double X, Y, x, y;
        Point tmp=getPosition();
        //On conserve ici les données utiles
        X=p.getX(); Y=p.getY(); //Les coordonnées du point vers lequel on se dirige
        x= (double) tmp.getX(); y= (double) tmp.getY(); //les coordonnées de là ou on se trouve

        this.angle = Math.atan2(Y - y, X - x); //calcul avec ArcTan la position ou on doit se déplacer
        double distance = Math.sqrt(Math.pow(X - x, 2) + Math.pow(Y - y, 2)); //Pythagore

        if (distance < speed) {
            // distance<speed  -> on se déplace sur le point visé
            tmp.setLocation((int) X, (int) Y);
        } else {
            /* Sinon, on se déplace en direction de notre point grâce aux formules de trigo
             * x + Math.cos(angle) * speed : nous donne de combien on doit se déplacer sur l'axe x
             * y + Math.sin(angle) * speed : nous donne de combien on doit se déplacer sur l'axe y
             */
            tmp.move((int) Math.round(x + Math.cos(angle) * speed), (int) Math.round(y + Math.sin(angle) * speed));
        }
    }


}
