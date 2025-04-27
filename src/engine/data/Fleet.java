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

    /**
     * Gets the list of boats in the fleet.
     * @return {@link ArrayList} The list of {@link Boat} objects in the fleet.
     */
    public ArrayList<Boat> getArrayListBoat() {
        return lstBoats;
    }

    /**
     * Gets the path associated with the fleet.
     * @return {@link ArrayList} The path as an {@link ArrayList} of {@link GraphPoint}.
     */
    public ArrayList<GraphPoint> getPath() {
        return path;
    }

    /**
     * Gets the name of the fleet.
     * @return {@link String} The name of the fleet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the list of boats in the fleet.
     * @param fleet {@link ArrayList} The list of {@link Boat} objects to be set in the fleet.
     */
    public void setFleet(ArrayList<Boat> fleet) {
        this.lstBoats = fleet;
    }

    /**
     * Sets the path associated with the fleet.
     * @param path {@link ArrayList} The path to be set as an {@link ArrayList} of {@link GraphPoint}.
     */
    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }

    /**
     * Sets the name of the fleet.
     * @param name {@link String} The name to be set for the fleet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a boat to the fleet. This method clears the boat's path and sets the continue path flag to false.
     * @param boat {@link Boat} The boat to be added to the fleet.
     */
    public void add(Boat boat) {
        boat.clearPath();
        boat.setContinuePath(false);
        this.lstBoats.add(boat);
    }

    /**
     * Removes a boat from the fleet. This method clears the boat's path and sets the continue path flag to false.
     * @param boat {@link Boat} The boat to be removed from the fleet.
     */
    public void remove(Boat boat) {
        boat.clearPath();
        boat.setContinuePath(false);
        this.lstBoats.remove(boat);
    }

    /**
     * Gets the status of whether the path should continue.
     * @return {@link Boolean} True if the path should continue, false otherwise.
     */
    public boolean getContinuePath() {
        return continuePath;
    }

    /**
     * Sets whether the path should continue.
     * @param continuePath {@link Boolean} The flag indicating if the path should continue.
     */
    public void setContinuePath(boolean continuePath) {
        this.continuePath = continuePath;
    }

}
