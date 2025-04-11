package engine.data.faction;

public class Pirate extends Faction {

    public Pirate(String color){
        super(color);
    }

    @Override
    public int getRelationship(Faction faction) {
        return -100;
    }

    @Override
    public void setRelationship(Faction faction,int relationship) {}
}
