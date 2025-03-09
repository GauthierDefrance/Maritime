package engine.faction;

import engine.entity.boats.Boat;

import java.util.ArrayList;
/**
 * @author Kenan Ammad
 * @version 0.1
 */
public class Player extends Faction {
    private ArrayList<Boat> vision;

    public Player(String color){
        super(color);
        vision = new ArrayList<>();
    }


    public ArrayList<Boat> getVision() {
        return vision;
    }

    public void setVision(ArrayList<Boat> vision) {
        this.vision = vision;
    }
}
