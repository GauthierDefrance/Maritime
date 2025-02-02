package maritime.engine.entity;

import maritime.engine.inventory.Inventory;

import java.awt.*;
import java.io.Serializable;

public interface EntityInterface extends Serializable {

    //Getters

    public String getName();

    public Point getPosition();

    public int getCurrentHp();

    public int getMaxHp();

    public Inventory getInventory();

    public int getVisionRadius();

    //Setters

    public void setPosition(Point position);

    public void setVisionRadius(int visionRadius);

    //Autres Méthodes

    public boolean reduceHp(int value);

}