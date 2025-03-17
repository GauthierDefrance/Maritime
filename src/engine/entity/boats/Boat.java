package engine.entity.boats;

import engine.entity.Entity;
import engine.graph.GraphPoint;
import engine.trading.Inventory;
import engine.trading.Resource;

import java.awt.*;
import java.util.ArrayList;

/**
 * Abstract class representing boats without specificities
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public abstract class Boat implements Entity {
    private String name;
    private String color;
    private double visionRadius;
    private Point position;
    private int maxHp;
    private int currentHp;
    private int damageSpeed;
    private int speed;
    private double angle;
    private ArrayList<GraphPoint> path;
    private GraphPoint oldGraphPoint;
    private GraphPoint nextGraphPoint;
    private int iPath;
    private boolean continuePath;
    private final Inventory inventory;

    public Boat(String name,String color, double visionRadius, GraphPoint position, int maxHp, int damageSpeed, int speed,int inventorySize) {
        this.name = name;
        this.color = color;
        this.visionRadius = visionRadius;
        this.position = new Point(position.getPoint());
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.damageSpeed = damageSpeed;
        this.speed = speed;
        this.path = new ArrayList<>();
        this.iPath = 0;
        this.continuePath = false;
        this.inventory = new Inventory(inventorySize);
        this.nextGraphPoint = position;
        this.oldGraphPoint = position;
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

    @Override
    public String getName() {return name;}

    @Override
    public double getVisionRadius() {return visionRadius;}

    @Override
    public int getMaxHp() {return maxHp;}

    @Override
    public int getCurrentHp() {return currentHp;}

    @Override
    public Point getPosition() {return position;}

    @Override
    public Inventory getInventory() {return inventory;}

    public int getDamageSpeed() {return damageSpeed;}

    @Override
    public String getColor() {
        return color;
    }

    public GraphPoint getOldGraphPoint() {
        return oldGraphPoint;
    }

    public GraphPoint getNextGraphPoint() {
        return nextGraphPoint;
    }

    //Setters

    public void setContinuePath(boolean continuePath) {this.continuePath = continuePath;}

    public void setPath(ArrayList<GraphPoint> path) {
        this.iPath = 0;
        this.path = path;
    }

    public void setIPath(int iPath) {this.iPath = iPath;}

    public void setAngle(double angle) { this.angle = angle; }

    public void setSpeed(int speed) {this.speed = speed;}

    public void setName(String name) {this.name = name;}

    @Override
    public void setVisionRadius(double visionRadius) {this.visionRadius = visionRadius;}

    @Override
    public void setMaxHp(int maxHp) {this.maxHp = maxHp;}

    @Override
    public void setCurrentHp(int currentHp) {this.currentHp = currentHp;}

    @Override
    public void setPosition(Point position) {this.position = position;}

    public void setPosition(double x,double y) {position.setLocation(x,y);}

    public void setDamageSpeed(int damageSpeed) {this.damageSpeed = damageSpeed;}

    public void setColor(String color) {
        this.color = color;
    }

    public void setOldGraphPoint(GraphPoint oldGraphPoint) {
        this.oldGraphPoint = oldGraphPoint;
    }

    public void setNextGraphPoint(GraphPoint nextGraphPoint) {
        this.nextGraphPoint = nextGraphPoint;
    }

    //Basic boat handling

    public void addIPath(int iPath) {this.iPath += iPath;}

    public void addCurrentHp(int Hp) {this.currentHp += Hp;}

    /**
     * check how many elements of a ressource this boat's inventory has
     * @param resource the targeted ressource
     * @return number of element of said ressource
     */
    public int checkNbRessource(Resource resource) {
        return this.inventory.getNbResource(resource);
    }

}

