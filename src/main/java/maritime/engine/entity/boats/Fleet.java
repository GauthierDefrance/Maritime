package maritime.engine.entity.boats;

import maritime.engine.graph.GraphPoint;

import java.util.ArrayList;

public class Fleet {
    private ArrayList<Boat> fleet;
    private ArrayList<GraphPoint> path;

    public Fleet(ArrayList<Boat> boats) {
        this.fleet = boats;
        this.path = new ArrayList<>();
    }

    public Fleet() {
        this.fleet = new ArrayList<>();
    }

    //Getters

    public ArrayList<Boat> getArrayListFleet() { return fleet; }
    public ArrayList<GraphPoint> getPath() {return path;}

    //Setters

    public void setFleet(ArrayList<Boat> fleet) { this.fleet = fleet; }
    public void setPath(ArrayList<GraphPoint> path) {this.path = path;}

    //Modifier

    public void add(Boat boat) { this.fleet.add(boat); }

    public void remove(Boat boat) { this.fleet.remove(boat); }

}
