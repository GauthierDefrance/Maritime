package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.entity.Entity;
import engine.data.graph.GraphPoint;
import engine.data.trading.Inventory;
import engine.data.trading.Resource;

import java.awt.*;
import java.util.ArrayList;

/**
 * Abstract class representing boats without specificities
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 1.0
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
    private int upgradePoint;
    private int level;
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
        this.upgradePoint = GameConfiguration.UPGRADE_POINT_DEFAULT;
        this.level = 1;
        this.path = new ArrayList<>();
        this.iPath = 0;
        this.continuePath = false;
        this.inventory = new Inventory(inventorySize);
        this.nextGraphPoint = position;
        this.oldGraphPoint = position;
    }

    //Getters

    /**
     * Gets whether the unit should continue along its current path.
     * @return {@link Boolean} True if the unit continues the path, false otherwise.
     */
    public Boolean getContinuePath() { return continuePath; }

    /**
     * Gets the current path as a list of graph points.
     * @return {@link ArrayList} of {@link GraphPoint} representing the current path.
     */
    public ArrayList<GraphPoint> getPath() { return path; }

    /**
     * Gets the current index in the path.
     * @return {@link Integer} The current path index.
     */
    public int getIPath() { return iPath; }

    /**
     * Gets the unit's current angle of orientation.
     * @return {@link Double} The angle in degrees or radians.
     */
    public double getAngle() { return angle; }

    /**
     * Gets the unit's current speed.
     * @return {@link Integer} The current speed value.
     */
    public int getSpeed() { return speed; }

    /**
     * Gets the unit's name.
     * @return {@link String} The name of the unit.
     */
    @Override
    public String getName() { return name; }

    /**
     * Gets the unit's vision radius.
     * @return {@link Double} The radius within which the unit can see.
     */
    @Override
    public double getVisionRadius() { return visionRadius; }

    /**
     * Gets the unit's maximum health points.
     * @return {@link Integer} The maximum HP value.
     */
    @Override
    public int getMaxHp() { return maxHp; }

    /**
     * Gets the unit's current health points.
     * @return {@link Integer} The current HP value.
     */
    @Override
    public int getCurrentHp() { return currentHp; }

    /**
     * Gets the unit's current position on the map.
     * @return {@link Point} The current position.
     */
    @Override
    public Point getPosition() { return position; }

    /**
     * Gets the unit's inventory.
     * @return {@link Inventory} The inventory object.
     */
    @Override
    public Inventory getInventory() { return inventory; }

    /**
     * Gets the unit's attack or action frequency (damage speed).
     * @return {@link Integer} The current damage speed.
     */
    public int getDamageSpeed() { return damageSpeed; }

    /**
     * Gets the unit's color.
     * @return {@link String} The color assigned to the unit.
     */
    @Override
    public String getColor() { return color; }

    /**
     * Gets the previous graph point in the unit's path.
     * @return {@link GraphPoint} The old graph point.
     */
    public GraphPoint getOldGraphPoint() { return oldGraphPoint; }

    /**
     * Gets the next graph point in the unit's path.
     * @return {@link GraphPoint} The next graph point.
     */
    public GraphPoint getNextGraphPoint() { return nextGraphPoint; }

    /**
     * Gets the number of upgrade points the unit currently has.
     * @return {@link Integer} The number of upgrade points.
     */
    public int getUpgradePoint() { return upgradePoint; }

    /**
     * Gets the current level of the unit.
     * @return {@link Integer} The current level.
     */
    public int getLevel() { return level; }

    //Setters

    /**
     * Sets whether the unit should continue its current path.
     * @param continuePath True to continue, false to stop.
     */
    public void setContinuePath(boolean continuePath) { this.continuePath = continuePath; }

    /**
     * Sets a new path and resets the path index.
     * @param path {@link ArrayList} of {@link GraphPoint} as the new path.
     */
    public void setPath(ArrayList<GraphPoint> path) {
        this.iPath = 0;
        this.path = path;
    }

    /**
     * Sets the current index in the path.
     * @param iPath The index to set.
     */
    public void setIPath(int iPath) { this.iPath = iPath; }

    /**
     * Sets the unit's angle of orientation.
     * @param angle The angle value to set.
     */
    public void setAngle(double angle) { this.angle = angle; }

    /**
     * Sets the unit's speed.
     * @param speed The speed value to set.
     */
    public void setSpeed(int speed) { this.speed = speed; }

    /**
     * Sets the unit's name.
     * @param name The name to assign.
     */
    @Override
    public void setName(String name) { this.name = name; }

    /**
     * Sets the unit's vision radius.
     * @param visionRadius The vision radius to assign.
     */
    @Override
    public void setVisionRadius(double visionRadius) { this.visionRadius = visionRadius; }

    /**
     * Sets the unit's maximum health points.
     * @param maxHp The max HP value.
     */
    @Override
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    /**
     * Sets the unit's current health points.
     * @param currentHp The HP value to assign.
     */
    @Override
    public void setCurrentHp(int currentHp) { this.currentHp = currentHp; }

    /**
     * Sets the unit's position using a {@link Point} object.
     * @param position The position to assign.
     */
    @Override
    public void setPosition(Point position) { this.position.setLocation(position); }

    /**
     * Sets the unit's position using x and y coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public void setPosition(int x, int y) { this.position.setLocation(x, y); }

    /**
     * Sets the unit's damage speed.
     * @param damageSpeed The damage speed value.
     */
    public void setDamageSpeed(int damageSpeed) { this.damageSpeed = damageSpeed; }

    /**
     * Sets the unit's color.
     * @param color The color as a string.
     */
    public void setColor(String color) { this.color = color; }

    /**
     * Sets the previous graph point.
     * @param oldGraphPoint The graph point to assign.
     */
    public void setOldGraphPoint(GraphPoint oldGraphPoint) { this.oldGraphPoint = oldGraphPoint; }

    /**
     * Sets the next graph point.
     * @param nextGraphPoint The graph point to assign.
     */
    public void setNextGraphPoint(GraphPoint nextGraphPoint) { this.nextGraphPoint = nextGraphPoint; }


    //Basic boat handling

    /**
     * Clears the current path and resets the index.
     */
    public void clearPath() {
        this.iPath = 0;
        this.path.clear();
    }

    /**
     * Adds a value to the current path index.
     * @param iPath The value to add to the index.
     */
    public void addIPath(int iPath) { this.iPath += iPath; }

    /**
     * Adds a value to the unit's current HP.
     * @param Hp The HP value to add.
     */
    public void addCurrentHp(int Hp) { this.currentHp += Hp; }

    /**
     * Checks how many elements of a given resource are in the inventory.
     * @param resource The {@link Resource} to check.
     * @return {@link Integer} The number of that resource in the inventory.
     */
    public int checkNbResource(Resource resource) {
        return this.inventory.getNbResource(resource);
    }

    /**
     * Decreases the unit's upgrade point count by one.
     */
    public void useUpgradePoint() {
        this.upgradePoint -= 1;
    }

    /**
     * Levels up the unit and adds upgrade points.
     */
    public void levelUp() {
        this.level += 1;
        this.upgradePoint += GameConfiguration.UPGRADE_POINT_DEFAULT;
    }

    /**
     * Applies an HP upgrade to the unit.
     */
    public void upgradeHp() {
        useUpgradePoint();
        this.setMaxHp(this.getMaxHp() + GameConfiguration.UPGRADE_DEFAULT_HP);
        this.setCurrentHp(this.getCurrentHp() + GameConfiguration.UPGRADE_DEFAULT_HP);
    }

    /**
     * Applies a damage speed upgrade to the unit.
     */
    public void upgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int) (this.getDamageSpeed() + GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED));
    }

    /**
     * Applies a movement speed upgrade to the unit.
     */
    public void upgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int) (this.getSpeed() + GameConfiguration.UPGRADE_DEFAULT_SPEED));
    }

    /**
     * Applies an inventory size upgrade to the unit.
     */
    public void upgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int) (this.getInventory().getCapacity() + GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE));
    }

    /**
     * Calculates the potential max HP after the next upgrade.
     * @return {@link Integer} The resulting HP.
     */
    public int nextUpgradeHp() {
        return this.getMaxHp() + GameConfiguration.UPGRADE_DEFAULT_HP;
    }

    /**
     * Calculates the potential damage speed after the next upgrade.
     * @return {@link Integer} The resulting damage speed.
     */
    public int nextUpgradeDamageSpeed() {
        return (int) (this.getDamageSpeed() + GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED);
    }

    /**
     * Calculates the potential movement speed after the next upgrade.
     * @return {@link Integer} The resulting speed.
     */
    public int nextUpgradeSpeed() {
        return (int) (this.getSpeed() + GameConfiguration.UPGRADE_DEFAULT_SPEED);
    }

    /**
     * Calculates the potential inventory capacity after the next upgrade.
     * @return {@link Integer} The resulting capacity.
     */
    public int nextUpgradeInventorySize() {
        return (int) (this.getInventory().getCapacity() + GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE);
    }

}

