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

    public FleetManager(MapBuilder map, BoatManager boatManager) {
        this.map = map;
        this.boatManager = boatManager;
    }

    public void setContinuePathAll(Fleet fleet, boolean continuePath) {
        for (Boat boat : fleet.getArrayListFleet()){
            boat.setContinuePath(continuePath);
        }
    }

    public void removePath(Fleet fleet){
        fleet.getPath().clear();
        setContinuePathAll(fleet, false);
        for(Boat boat : fleet.getArrayListFleet()){
            boat.getPath().clear();
        }
    }

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

    public void setNewPath(Fleet fleet, ArrayList<GraphPoint> path){
        ArrayList<GraphPoint> newPath = new ArrayList<>();
        newPath.addAll(path);
        fleet.setPath(newPath);
        for(Boat boat : fleet.getArrayListFleet()){
            boat.getPath().clear();
        }
    }
}
