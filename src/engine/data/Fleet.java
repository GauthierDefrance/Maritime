package engine.data;

import engine.data.entity.boats.Boat;
import engine.data.graph.GraphPoint;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing fleets : group of boats with common goals
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @version 0.2
 */
public class Fleet implements Serializable {
    private ArrayList<Boat> lstBoats;
    private ArrayList<GraphPoint> path;
    private String name;
    private boolean continuePath;

    public Fleet(ArrayList<Boat> lstBoats, String name) {
        this.lstBoats = lstBoats;
        this.path = new ArrayList<>();
        this.name = name;
    }

    public Fleet(String name) {
        this.lstBoats = new ArrayList<>();
        this.path = new ArrayList<>();
        this.name = name;
    }

    public Fleet() {
        this.lstBoats = new ArrayList<>();
        this.path = new ArrayList<>();
        this.name = "";
    }

    //Getters

    public ArrayList<Boat> getArrayListBoat() {
        return lstBoats;
    }

    public ArrayList<GraphPoint> getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    //Setters

    public void setFleet(ArrayList<Boat> fleet) {
        this.lstBoats = fleet;
    }

    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Modifier

    /**
     *
     * @param boat
     */
    public void add(Boat boat) {
        boat.clearPath();
        boat.setContinuePath(false);
        this.lstBoats.add(boat);
    }

    /**
     *
     * @param boat
     */
    public void remove(Boat boat) {
        boat.clearPath();
        boat.setContinuePath(false);
        this.lstBoats.remove(boat);
    }

    public boolean getContinuePath() {
        return continuePath;
    }

    public void setContinuePath(boolean continuePath) {
        this.continuePath = continuePath;
    }

}
