package maritime.engine.entity.boats;

import java.awt.*;
import java.util.ArrayList;
import maritime.engine.entity.EntityInterface;
import maritime.engine.graph.GraphPoint;
import maritime.engine.trading.Inventory;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public abstract class Boat implements EntityInterface {
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

    //Getters

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

    public String getName() {return name;}

    public double getVisionRadius() {return visionRadius;}

    public int getMaxHp() {return maxHp;}

    public int getCurrentHp() {return currentHp;}

    public Point getPosition() {return position;}

    public Inventory getInventory() {return inventory;}

    public int getDamageSpeed() {return damageSpeed;}

    //Setters


    public void setContinuePath(boolean continuePath) {this.continuePath=continuePath;}

    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }

    public void setIPath(int iPath) {this.iPath = iPath;}

    public void setAngle(double angle) { this.angle = angle; }

    public void setSpeed(int speed) {this.speed = speed;}

    public void setName(String name) {this.name = name;}

    public void setVisionRadius(double visionRadius) {this.visionRadius = visionRadius;}

    public void setMaxHp(int maxHp) {this.maxHp = maxHp;}

    public void setCurrentHp(int currentHp) {this.currentHp = currentHp;}

    public void setPosition(Point position) {this.position = position;}

    public void setPosition(double x,double y) {position.setLocation(x,y);}


    public void setDamageSpeed(int damageSpeed) {this.damageSpeed = damageSpeed;}

    //Logic Methods (à déplacer)

    public void addIPath(int iPath) {this.iPath += iPath;}

    public void addCurrentHp(int currentHp) {this.currentHp += currentHp;}
}
