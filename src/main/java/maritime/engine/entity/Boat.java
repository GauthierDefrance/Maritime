package maritime.engine.entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import maritime.engine.graph.GraphPoint;

/**
 * Classe entité
 * @version 0.1
 */
public abstract class Boat extends Entity{
    private int speed;
    private double angle;
    private ArrayList<GraphPoint> path = null;
    private int iPath = 0;
    private boolean continuePath = false;
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

    /*
     * Méthode qui indique si le bateau se trouve actuellement sur un point du Graphe.
     * @param GraphPoint point
     * @return Bool
     */
    public boolean isOnPoint(GraphPoint point){
        return point.getPoint()==this.getPosition();
    }

    public void followThePath(){
        if (path==null){}
        else {
            approachingToPoint(path.get(iPath));
        }
    }

    /*
     * Méthode qui déplace le bateau selon sa vitesse en direction du point donnée.
     * Si il se trouve sur le point donnée ou sa vitesse est plus grande que le point donnée,
     * le bateau prend les coordonnées du point.
     * @param GraphPoint point
     */
    public void approachingToPoint(GraphPoint point){
        double x1, y1, x2, y2;
        //On conserve ici les données utiles
        x1 =point.getX(); y1 =point.getY(); //Les coordonnées du point vers lequel on se dirige
        x2 = this.getPosition().getX();
        y2 = this.getPosition().getY();

        this.angle = Math.atan2(y1 - y2, x1 - x2); //calcul avec ArcTan la position ou on doit se déplacer
        double distance = this.getPosition().distance(point.getPoint());

        if (distance < speed) {
            moveTo(point.getPoint());


        }//distance < speed> on se déplace sur le point visé
        else {moveTo(new Point((int) Math.round(x2 + Math.cos(angle) * speed), (int) Math.round(y2 + Math.sin(angle) * speed)));}// Sinon, on se déplace en direction de notre point grâce aux formules de trigo
    }

    public void WeAreOnPoint(Point point){
        if (path.size()>iPath+1)iPath++;
        else {
            iPath=0;
            if (continuePath) Collections.reverse(path);
            else path = null;
        }
    }

    public void moveTo(Point point){
        this.setPosition(point);
    }


    public ArrayList<GraphPoint> getPath() {
        return path;
    }

    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }
}
