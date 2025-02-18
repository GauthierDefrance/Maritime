package engine.entity;

import engine.trading.Inventory;

import java.awt.*;
import java.io.Serializable;

/**
 * Interface designed to handle interactions involving any kind of Entity indistinguishably when relevant
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public interface EntityInterface extends Serializable {

    //Getters

    /**
     * Allows fetching of the name of the Entity
     * @return name of the entity
     */
    public String getName();

    /**
     * Allows fetching of the current Position of the Entity
     * @return position of the Entity
     */
    public Point getPosition();

    /**
     * Allows fetching of the current health point value of the Entity
     * @return current health points (int)
     */
    public int getCurrentHp();

    /**
     * Allows fetching of the maximum health point value of the Entity
     * @return maximum health points (int)
     */
    public int getMaxHp();

    /**
     * Allows fetching of the Inventory of the Entity
     * @return Inventory of the entity
     * @see Inventory
     */
    public Inventory getInventory();

    /**
     * Allows fetching of the vision radius of the Entity
     * @return vision radius (double)
     */
    public double getVisionRadius();

    /**
     * Allows fetching of a String representing the "color" relative to the faction of the entity
     * @return String linked with a color
     */
    public String getColor();

    //Setters

    /**
     * Allows to assign the position of an Entity
     * @param position new position point
     */
    public void setPosition(Point position);

    /**
     * Allows to alter the currentHp value
     * @param currentHp new value for current health points
     */
    public void setCurrentHp(int currentHp);

    /**
     * Allows to alter the maxHp value
     * @param maxHp new value for maximum health points
     */
    public void setMaxHp(int maxHp);

    /**
     * Allows to alter the vision radius value
     * @param visionRadius new value for the vision radius
     */
    public void setVisionRadius(double visionRadius);

}