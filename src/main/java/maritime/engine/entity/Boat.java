package maritime.engine.entity;

import java.awt.*;
import java.util.ArrayList;

import maritime.engine.graph.GraphPoint;
import maritime.engine.inventory.Inventory;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public abstract class Boat{
    private String name;
    private double visionRadius;
    private int maxHp;
    private int currentHp;
    private final Inventory inventory;
    private Point position;
    private int speed;
    private double angle;
    private ArrayList<GraphPoint> path;
    private int iPath;
    private boolean continuePath;
    private int damageSpeed;

    public Boat(String name, double visionRadius, int maxHp, int damageSpeed, Point position, int speed) {
        this.name = name;
        this.visionRadius = visionRadius;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.damageSpeed = damageSpeed;
        this.position = position;
        this.speed = speed;
        this.path = new ArrayList<>();
        this.continuePath = false;
        this.iPath = 0;
        this.inventory = new Inventory();
    }

    public Boolean getContinuePath() {return continuePath;}
    public ArrayList<GraphPoint> getPath() {
        return path;
    }
    public int getIPath() {
        return iPath;
    }
    public double getAngle() { return angle; }
    public int getSpeed() {
        return speed;
    }

    public void setContinuePath(boolean continuePath) {this.continuePath=continuePath;}
    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }
    public void setIPath(int iPath) {this.iPath = iPath;}
    public void addIPath(int iPath) {this.iPath += iPath;}
    public void setAngle(double angle) { this.angle = angle; }
    public void setSpeed(int speed) {this.speed = speed;}


//    public void followThePath(){
//        if (!path.isEmpty()){
//            approachingToPoint(path.get(iPath));
//        }
//    }

    /*
     * Méthode qui déplace le bateau selon sa vitesse en direction du point donnée.
     * S'il se trouve sur le point donné ou sa vitesse est plus grande que le point donné,
     * le bateau prend les coordonnées du point.
     * @param GraphPoint point
     */
//    public void approachingToPoint(GraphPoint point){
//        double x1, y1, x2, y2;
//        //On conserve ici les données utiles
//        x1 =point.getX(); y1 =point.getY(); //Les coordonnées du point vers lequel on se dirige
//        x2 = this.getPosition().getX();
//        y2 = this.getPosition().getY();
//
//        double distance = this.getPosition().distance(point.getPoint());
//
//        if (distance < speed) {
//            moveTo(x1,y1);
//            weAreOnPoint();
//        }//distance < speed, on se déplace sur le point visé
//        else {
//            angle = Math.atan2(y1 - y2, x1 - x2); //calcul avec ArcTan la position cible
//            moveTo(Math.round(x2 + Math.cos(angle) * speed),Math.round(y2 + Math.sin(angle) * speed));}// Sinon, on se déplace en direction de notre point grâce aux formules de trigonometrie
//    }

//    public void weAreOnPoint(){
//        if (path.size()>iPath+1)iPath++;
//        else {
//            iPath=0;
//            if (continuePath) Collections.reverse(path);
//            else path.clear();
//        }
//    }

//    public void moveTo(double x,double y){this.setPosition(x,y);}


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getVisionRadius() {return visionRadius;}

    public void setVisionRadius(double visionRadius) {this.visionRadius = visionRadius;}

    public int getMaxHp() {return maxHp;}

    public void setMaxHp(int maxHp) {this.maxHp = maxHp;}

    public int getCurrentHp() {return currentHp;}

    public void setCurrentHp(int currentHp) {this.currentHp = currentHp;}

    public void addCurrentHp(int currentHp) {this.currentHp += currentHp;}

    public Point getPosition() {return position;}
    public void setPosition(Point position) {this.position = position;}
    public void setPosition(double x,double y) {position.setLocation(x,y);}

    public Inventory getInventory() {return inventory;}

    public int getDamageSpeed() {
        return damageSpeed;
    }

    public void setDamageSpeed(int damageSpeed) {
        this.damageSpeed = damageSpeed;
    }
}
