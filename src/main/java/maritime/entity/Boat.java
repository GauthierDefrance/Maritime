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
     * @param GraphPoint point
     * @return Bool
     */
    public boolean isOnPoint(GraphPoint point){
        return point.getX()==((int) this.getPosition().getX()) && point.getY()==((int) this.getPosition().getY());
    }
    /*
     * Méthode qui déplace le bateau selon sa vitesse en direction du point donnée.
     * Si il se trouve sur le point donnée ou sa vitesse est plus grande que le point donnée,
     * le bateau prend les coordonnées du point.
     * Utilise la trigonométrie pour fonctionner.
     * @param GraphPoint point
     */
    public void approachingToPoint(GraphPoint point){
        double x1, y1, x2, y2;
        //On conserve ici les données utiles
        x1 =point.getX(); y1 =point.getY(); //Les coordonnées du point vers lequel on se dirige
        x2 = this.getPosition().getX();
        y2 = this.getPosition().getY();

        this.angle = Math.atan2(y1 - y2, x1 - x2); //calcul avec ArcTan la position ou on doit se déplacer
        double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)); //Pythagore

        if (distance < speed) {
//             distance<speed> on se déplace sur le point visé
            this.setPosition(point.getPoint());
        } else {
            /* Sinon, on se déplace en direction de notre point grâce aux formules de trigo
             * x + Math.cos(angle) * speed : nous donne de combien on doit se déplacer sur l'axe x
             * y + Math.sin(angle) * speed : nous donne de combien on doit se déplacer sur l'axe y
             */
            this.getPosition().move((int) Math.round(x2 + Math.cos(angle) * speed), (int) Math.round(y2 + Math.sin(angle) * speed));
        }
    }


}
