package maritime.engine.entity;

import maritime.engine.trading.Inventory;

import java.awt.*;
import java.io.Serializable;

public interface EntityInterface extends Serializable {

    //Getters

    public String getName();

    public Point getPosition();

    public int getCurrentHp();

    public int getMaxHp();

    public Inventory getInventory();

    public double getVisionRadius();

    //Setters

    public void setPosition(Point position);

    public void setCurrentHp(int currentHp);

    public void setMaxHp(int maxHp);

    public void setVisionRadius(double visionRadius);

}