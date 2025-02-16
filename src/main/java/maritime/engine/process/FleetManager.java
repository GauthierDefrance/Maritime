package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.SearchInGraph;
import java.util.ArrayList;

/**
 * @author @Kenan Ammad
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
        for (Boat boat : fleet.getArrayListFleet()){
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
        for(Boat boat : fleet.getArrayListFleet()){
            boat.getPath().clear();
        }
    }

    /**
     * Update a fleet Path if necessary
     * @param fleet targeted fleet
     */
    public void pathUpdate(Fleet fleet){
        if(!fleet.getPath().isEmpty()){
            for(Boat boat : fleet.getArrayListFleet()){
                if(boat.getPath().isEmpty()&&(fleet.getContinuePath()||!boat.getPosition().equals(fleet.getPath().getLast().getPoint()))){
                    if(boat.getPosition().equals(fleet.getPath().getFirst().getPoint())){
                        ArrayList<GraphPoint> newPath = new ArrayList<>();
                        newPath.addAll(fleet.getPath());
                        boat.setPath(newPath);
                        boat.setContinuePath(fleet.getContinuePath());
                    }
                    else {
                        boat.setPath(SearchInGraph.findPath(boat,fleet.getPath().getFirst()));
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
        for(Boat boat : fleet.getArrayListFleet()){
            boat.getPath().clear();
        }
    }
}
