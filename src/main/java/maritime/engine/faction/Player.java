package maritime.engine.faction;

import maritime.engine.entity.Boat;

import java.util.ArrayList;

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
