package engine.data.faction;

import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;

import java.util.ArrayList;

/**
 * A specific kind of Faction with specific information.
 * @author Kenan Ammad
 * Class Pirate
 * @version 1.0
 */
public class Player extends Faction {
    private ArrayList<Boat> vision;

    public Player(String color,String name){
        super(color,name);
        vision = new ArrayList<>();
    }

    /**
     * Gets the list of boats currently visible to this faction.
     * @return {@link ArrayList} of {@link Boat} representing visible boats.
     */
    public ArrayList<Boat> getVision() {
        return vision;
    }

    /**
     * Sets the list of boats visible to this faction.
     * @param vision {@link ArrayList} of {@link Boat} to set as visible.
     */
    public void setVision(ArrayList<Boat> vision) {
        this.vision = vision;
    }

    /**
     * Gets the relationship with a specific faction.
     * Always returns 0 in this implementation.
     * @param faction The {@link Faction} to check the relationship with.
     * @return {@link Integer} Relationship value (always 0).
     */
    @Override
    public int getRelationship(Faction faction) {
        return 0;
    }

    /**
     * Sets the relationship with a specific faction.
     * This method is currently not implemented.
     * @param faction The {@link Faction} to set relationship with.
     * @param relationship The new relationship value.
     */
    @Override
    public void setRelationship(Faction faction, int relationship) {}

    /**
     * Adds a harbor to the faction.
     * Sets the harbor's color to match the faction before adding it.
     * @param harbor The {@link Harbor} to be added.
     */
    @Override
    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        getLstHarbor().add(harbor);
    }

}
