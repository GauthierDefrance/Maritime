package engine.entity;

import config.GameConfiguration;
import engine.graph.GraphPoint;
import engine.trading.Inventory;

import java.awt.*;

/**
 * Class representing Harbors : pivotal elements in gameplay serving as commercial and strategic points
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class Harbor implements Entity {
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

    //Getters

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getCurrentHp() { return currentHp; }

    @Override
    public double getVisionRadius() {return visionRadius; }

    @Override
    public Inventory getInventory() { return inventory; }

    @Override
    public Point getPosition() { return position; }

    public GraphPoint getGraphPosition() { return graphPosition; }

    public Point getPreciseGraphPosition() { return graphPosition.getPoint(); }

    @Override
    public String getColor() { return color; }



    //Setters

    @Override
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCurrentHp(int currentHp) { this.currentHp = currentHp; }

    @Override
    public void setVisionRadius(double visionRadius) { this.visionRadius = visionRadius; }

    @Override
    public void setPosition(Point position) { this.position = position; }

    public void setGraphPosition(GraphPoint graphPosition) { this.graphPosition = graphPosition; }

    public void setColor(String color) { this.color = color; }
}
