package engine.entity;

import engine.trading.Inventory;

import java.awt.*;
import java.io.Serializable;

/**
 * Interface designed to handle interactions involving any kind of Entity indistinguishably when relevant
 * @author Kenan Ammad
 * @version 0.1
 */
public interface Name extends Serializable {

    //Getters

    /**
     * Allows fetching of the name of the Entity
     * @return name of the entity
     */
    public String getName();;

    //Setters

    /**
     * Allows to assign the name of the Entity
     * @return name of the entity
     */
    public void setName(String name);

}