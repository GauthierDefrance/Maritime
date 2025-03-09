package engine.process;

import config.MapBuilder;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.graph.GraphPoint;
import engine.graph.SearchInGraph;

import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * @see FactionManager
 * @version 0.3
 */
public class FleetManager {
    private final MapBuilder map;
    private final BoatManager boatManager;

    /**
     * Initialize a FleetManager : a way to handle multiple boats with the same goal
     */
    public FleetManager(MapBuilder map, BoatManager boatManager) {
        this.map = map;
        this.boatManager = boatManager;
    }

    /**
     * Changes a given fleet behavior by asking them keep on following a path or not according to a boolean
     * @param fleet targeted fleet
     * @param continuePath boolean setting the expected behavior
     */
    public void setContinuePathAll(Fleet fleet, boolean continuePath) {
        fleet.setContinuePath(continuePath);
        for (Boat boat : fleet.getArrayListBoat()){
            boat.setContinuePath(continuePath);
        }
    }

    /**
     * Remove the path a fleet is supposed to be following by clearing it
     * @param fleet targeted fleet
     */
    public void removePath(Fleet fleet){
        fleet.getPath().clear();
        setContinuePathAll(fleet, false);
        for(Boat boat : fleet.getArrayListBoat()){
            boat.getPath().clear();
        }
    }

    /**
     * Update a fleet Path if necessary
     * @param fleet targeted fleet
     */
    public void pathUpdate(Fleet fleet){
        if(!fleet.getPath().isEmpty()){
            for(Boat boat : fleet.getArrayListBoat()){
                if(boat.getPath().isEmpty()&&(fleet.getContinuePath()||!boat.getPosition().equals(fleet.getPath().get(fleet.getPath().size()-1).getPoint()))){
                    if(boat.getPosition().equals(fleet.getPath().get(0).getPoint())){
                        ArrayList<GraphPoint> newPath = new ArrayList<>();
                        newPath.addAll(fleet.getPath());
                        boat.setPath(newPath);
                        boat.setContinuePath(fleet.getContinuePath());
                    }
                    else {
                        boat.setPath(SearchInGraph.findPath(boat,fleet.getPath().get(0)));
                    }
                }
            }
        }
    }

    /**
     * change a fleet path to a brand new one
     */
    public void setNewPath(Fleet fleet, ArrayList<GraphPoint> path){
        ArrayList<GraphPoint> newPath = new ArrayList<>();
        newPath.addAll(path);
        fleet.setPath(newPath);
        for(Boat boat : fleet.getArrayListBoat()){
            boat.getPath().clear();
        }
    }
}
