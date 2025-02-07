package maritime.engine.entity.boats;

import java.util.ArrayList;

public class Fleet {
    private ArrayList<Boat> crew;

    public Fleet(ArrayList<Boat> boats) { this.crew = boats; }

    public Fleet() {
        this.crew = new ArrayList<>();
    }

    //Getters

    public ArrayList<Boat> getCrew() { return crew; }

    //Setters

    public void setCrew(ArrayList<Boat> Crew) { this.crew = Crew; }

    //Modifier

    public void add(Boat boat) { this.crew.add(boat); }

    public void remove(Boat boat) { this.crew.remove(boat); }
}
