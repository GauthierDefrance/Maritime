package engine.data.faction;

import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;

import java.util.ArrayList;

/**
 * A specific kind of Faction with specific information.
 * @author Kenan Ammad
 * Class Pirate
 * @version 0.1
 */
public class Player extends Faction {
    private ArrayList<Boat> vision;

    public Player(String color,String name){
        super(color,name);
        vision = new ArrayList<>();
    }

    public ArrayList<Boat> getVision() {
        return vision;
    }

    public void setVision(ArrayList<Boat> vision) {
        this.vision = vision;
    }

    @Override
    public int getRelationship(Faction faction) {
        return 0;
    }

    @Override
    public void setRelationship(Faction faction,int relationship) {}

    @Override
    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        getLstHarbor().add(harbor);
    }
}
