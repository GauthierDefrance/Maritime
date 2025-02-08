package maritime.engine.entity;

import maritime.config.GameConfiguration;
import maritime.engine.graph.GraphPoint;
import maritime.engine.trading.Inventory;

import java.awt.*;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class Harbor {
    private String name;
    private String color;
    private double visionRadius;
    private Point position;
    private GraphPoint graphPosition;
    private int maxHp;
    private int currentHp;
    private final Inventory inventory;



    public Harbor(String name,String color, Point position,GraphPoint graphPosition) {
        this.name = name;
        this.color =color;
        this.visionRadius = GameConfiguration.HARBOR_VISION_RADIUS;
        this.inventory = new Inventory();
        this.maxHp = (int) GameConfiguration.HARBOR_HP;
        this.currentHp = (int) GameConfiguration.HARBOR_HP;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
