package engine.entity.boats;

import engine.graph.GraphPoint;

import java.util.ArrayList;

/**
 * Class representing fleets : group of boats with common goals
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @version 0.2
 */
public class Fleet {
    private ArrayList<Boat> lstBoats;
    private ArrayList<GraphPoint> path;
    private boolean continuePath;

    public Fleet(ArrayList<Boat> boats) {
        this.lstBoats = boats;
        this.path = new ArrayList<>();
    }

    public Fleet() {
        this.lstBoats = new ArrayList<>();
    }

    //Getters

    public ArrayList<Boat> getArrayListBoat() { return lstBoats; }
    public ArrayList<GraphPoint> getPath() {return path;}

    //Setters

    public void setFleet(ArrayList<Boat> fleet) { this.lstBoats = fleet; }
    public void setPath(ArrayList<GraphPoint> path) {this.path = path;}

    //Modifier

    public void add(Boat boat) { this.lstBoats.add(boat); }

    public void remove(Boat boat) { this.lstBoats.remove(boat); }

    public boolean getContinuePath() {
        return continuePath;
    }

    public void setContinuePath(boolean continuePath) {
        this.continuePath = continuePath;
    }
}
