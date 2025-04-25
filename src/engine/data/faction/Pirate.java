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

    @Override
    public int getRelationship(Faction faction) {
        return -100;
    }

    @Override
    public void setRelationship(Faction faction,int relationship) {}
}
