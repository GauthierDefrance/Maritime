package maritime.engine.entity.boats;

import java.util.ArrayList;

public class Fleet {
    private ArrayList<Boat> fleet;

    public Fleet(ArrayList<Boat> boats) { this.fleet = boats; }

    public Fleet() {
        this.fleet = new ArrayList<>();
    }

    //Getters

    public ArrayList<Boat> getFleet() { return fleet; }

    //Setters

    public void setFleet(ArrayList<Boat> Crew) { this.fleet = Crew; }

    //Modifier

    public void add(Boat boat) { this.fleet.add(boat); }

    public void remove(Boat boat) { this.fleet.remove(boat); }
}
