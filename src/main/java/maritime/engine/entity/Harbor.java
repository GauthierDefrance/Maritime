package maritime.engine.entity;

import maritime.engine.graph.GraphPoint;
import maritime.engine.inventory.Inventory;

import java.awt.*;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class Harbor {
    private String name;
    private double visionRadius;
    private int maxHp;
    private int currentHp;
    private Inventory inventory;
    private Point position;
    private GraphPoint graphPosition;

    public Harbor(String name, double visionRadius, int maxHp, Point position,GraphPoint graphPosition) {
        this.name = name;
        this.visionRadius = visionRadius;
        this.inventory = new Inventory();
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.position = position;
        this.graphPosition =graphPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVisionRadius() {
        return visionRadius;
    }

    public void setVisionRadius(double visionRadius) {
        this.visionRadius = visionRadius;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public GraphPoint getGraphPosition() {
        return graphPosition;
    }

    public void setGraphPosition(GraphPoint graphPosition) {
        this.graphPosition = graphPosition;
    }
}
