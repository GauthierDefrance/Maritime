package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.SearchInGraph;

import java.util.ArrayList;

public class FleetManager {
    private final MapBuilder map;
    private final BoatManager boatManager;

    public FleetManager(MapBuilder map, BoatManager boatManager) {
        this.map = map;
        this.boatManager = boatManager;
    }

    public void setContinuePathAll(Fleet fleet, boolean continuePath) {
        for (Boat boat : fleet.getArrayListFleet()){
            boat.setContinuePath(continuePath);
        }
    }

    public void pathUpdate(Fleet fleet){
        for(Boat boat : fleet.getArrayListFleet()){
            if(boat.getPath().isEmpty()){
                if(boat.getPosition().equals(fleet.getPath().getFirst().getPoint())){
                    boat.setPath(fleet.getPath());
                }
                else {
                    boat.setPath(SearchInGraph.findPath(boat,fleet.getPath().getFirst()));
                }
            }
        }
    }

    public void setNewPath(Fleet fleet, ArrayList<GraphPoint> path){
        fleet.setPath(path);
        for(Boat boat : fleet.getArrayListFleet()){
            boat.getPath().clear();
        }
    }
}
