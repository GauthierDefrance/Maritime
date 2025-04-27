package engine.data.entity;

import config.GameConfiguration;
import engine.data.entity.boats.Boat;
import engine.data.graph.GraphPoint;
import engine.data.trading.Inventory;
import engine.data.trading.Resource;

import java.awt.*;
import java.util.HashMap;

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
    private int level;
    private final Inventory inventory;
    private HashMap<Boat,Boolean> hashMapBoat;
    private HashMap<Resource, Integer[]> generator;

    public Harbor(String name,String color, Point position,GraphPoint graphPosition) {
        this.name = name;
        this.color =color;
        this.visionRadius = GameConfiguration.HARBOR_VISION_RADIUS;
        this.inventory = new Inventory();
        this.maxHp = (int) GameConfiguration.HARBOR_HP;
        this.currentHp = (int) GameConfiguration.HARBOR_HP;
        this.level = 1;
        this.position = position;
        this.graphPosition =graphPosition;
        this.hashMapBoat = new HashMap<>();
        this.generator = new HashMap<>();
    }

    //Getters
    /**
     * Gets the name of the entity.
     * @return {@link String} The name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the maximum health points of the entity.
     * @return {@link Integer} The max HP value.
     */
    @Override
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Gets the current health points of the entity.
     * @return {@link Integer} The current HP value.
     */
    @Override
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Gets the vision radius of the entity.
     * @return {@link Double} The vision radius.
     */
    @Override
    public double getVisionRadius() {
        return visionRadius;
    }

    /**
     * Gets the inventory of the entity.
     * @return {@link Inventory} The inventory object.
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the current position of the entity on the map.
     * @return {@link Point} The 2D position.
     */
    @Override
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the graph position node associated with the entity.
     * @return {@link GraphPoint} The current graph node.
     */
    public GraphPoint getGraphPosition() {
        return graphPosition;
    }

    /**
     * Gets the precise point (2D coordinates) from the current graph node.
     * @return {@link Point} The coordinates of the graph position.
     */
    public Point getPreciseGraphPosition() {
        return graphPosition.getPoint();
    }

    /**
     * Gets the entity's assigned color.
     * @return {@link String} The color as a string.
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * Gets the map linking boats to a boolean state.
     * @return {@link HashMap} of {@link Boat} to {@link Boolean}.
     */
    public HashMap<Boat, Boolean> getHashMapBoat() {
        return hashMapBoat;
    }

    /**
     * Gets the resource generation configuration.
     * @return {@link HashMap} of {@link Resource} to {@link Integer} arrays.
     */
    public HashMap<Resource, Integer[]> getGenerator() {
        return generator;
    }

    /**
     * Gets the current level of the entity.
     * @return {@link Integer} The level.
     */
    public int getLevel() {
        return level;
    }



    //Setters

    /**
     * Sets the maximum health points of the entity.
     * @param maxHp The new max HP value.
     */
    @Override
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * Sets the name of the entity.
     * @param name The new name.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the current health points of the entity.
     * @param currentHp The new current HP.
     */
    @Override
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    /**
     * Sets the vision radius of the entity.
     * @param visionRadius The new vision radius.
     */
    @Override
    public void setVisionRadius(double visionRadius) {
        this.visionRadius = visionRadius;
    }

    /**
     * Sets the position of the entity on the map.
     * @param position The new position.
     */
    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Sets the graph node associated with the entity.
     * @param graphPosition The new graph position.
     */
    public void setGraphPosition(GraphPoint graphPosition) {
        this.graphPosition = graphPosition;
    }

    /**
     * Sets the entity's color.
     * @param color The new color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the boat state mapping for the entity.
     * @param hashMapBoat A map of {@link Boat} to {@link Boolean}.
     */
    public void setHashMapBoat(HashMap<Boat, Boolean> hashMapBoat) {
        this.hashMapBoat = hashMapBoat;
    }

    /**
     * Sets the resource generator configuration.
     * @param generator A map of {@link Resource} to {@link Integer} arrays.
     */
    public void setGenerator(HashMap<Resource, Integer[]> generator) {
        this.generator = generator;
    }

    /**
     * Increases the level of the entity and boosts its max and current HP
     * based on the harbor upgrade configuration.
     */
    public void levelUp() {
        this.level += 1;
        maxHp += GameConfiguration.UPGRADE_HARBOR_HP;
        currentHp += GameConfiguration.UPGRADE_HARBOR_HP;
    }


}
