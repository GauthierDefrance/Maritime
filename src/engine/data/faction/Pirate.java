package engine.data.faction;

/**
 * A specific kind of Faction, they behave differently.
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * Class Pirate
 * @version 0.2
 */
public class Pirate extends Faction {

    public Pirate(String color,String name){
        super(color, name);
    }

    /**
     * Gets the relationship value with a specific faction.
     * Always returns -100, indicating a default or hostile relationship.
     * @param faction The {@link Faction} to get the relationship with.
     * @return {@link Integer} The relationship value (always -100).
     */
    @Override
    public int getRelationship(Faction faction) {
        return -100;
    }

    /**
     * Sets the relationship value with a specific faction.
     * This implementation is empty and does not store any value.
     * @param faction The {@link Faction} to update.
     * @param relationship The relationship value to set.
     */
    @Override
    public void setRelationship(Faction faction, int relationship) {}
}
