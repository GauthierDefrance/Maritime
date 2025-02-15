package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.SearchInGraph;
import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * Classe FactionManager
 * @version 0.2
 */
public class FleetManager {
    private final MapBuilder map;
    private final BoatManager boatManager;

    /**
     * Typical builder generating an FleetManager
     */
    public FleetManager(MapBuilder map, BoatManager boatManager) {
        this.map = map;
        this.boatManager = boatManager;
    }

    /**
     * Take a fleet and make it continuePath true or false
     */
    public void setContinuePathAll(Fleet fleet, boolean continuePath) {
        fleet.setContinuePath(continuePath);
        for (Boat boat : fleet.getArrayListFleet()){
            boat.setContinuePath(continuePath);
        }
    }

    /**
     * Take a fleet and make it path empty and is continuePath false
     */
    public void removePath(Fleet fleet){
        fleet.getPath().clear();
        setContinuePathAll(fleet, false);
        for(Boat boat : fleet.getArrayListFleet()){
            boat.getPath().clear();
        }
    }

    /**
     * Take a fleet and update all boat fleet path if is empty
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
     * Take a fleet set the path and clear all boat fleet Path
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
