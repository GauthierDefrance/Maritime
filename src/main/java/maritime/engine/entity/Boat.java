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
    public void setContinuePath(boolean continuePath) {this.continuePath=continuePath;}

    public ArrayList<GraphPoint> getPath() {
        return path;
    }
    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }

    public int getIPath() {
        return iPath;
    }
    public void setIPath(int iPath) {this.iPath = iPath;}
    public void addIPath(int iPath) {this.iPath += iPath;}

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {this.speed = speed;}


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

    public int getDamageSpeed() {return damageSpeed;}
    public void setDamageSpeed(int damageSpeed) {this.damageSpeed = damageSpeed;}
}
