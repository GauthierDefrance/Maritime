package engine.process.manager;

import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.graph.GraphPoint;
import engine.utilities.SearchInGraph;

import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * @see FactionManager
 * @version 1.0
 */
public class FleetManager {

    /**
     * Initialize a FleetManager : a way to handle multiple boats with the same goal
     */
    public FleetManager() {
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
            boat.clearPath();
        }
    }

    /**
     * Remove the path a fleet is supposed to be following by clearing it
     * @param fleet targeted fleet
     */
    public void removePath(Fleet fleet){
        fleet.getPath().clear();
        setContinuePathAll(fleet, false);
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
                        boat.setPath(new ArrayList<>(fleet.getPath()));
                        boat.setContinuePath(fleet.getContinuePath());
                    }
                    else {
                        Harbor harbor = FactionManager.getInstance().getMyHarbor(boat);
                        if(harbor != null && harbor.getHashMapBoat().get(boat)){
                            FactionManager.getInstance().getHarborManager().removeBoatInHarbor(harbor,boat);
                        }
                        boat.setPath(SearchInGraph.findPath(boat,fleet.getPath().get(0)));
                    }
                }
            }
        }
    }

    /**
     * change a fleet path to a brand new one
     */
    public void setNewPath(Fleet fleet, ArrayList<GraphPoint> path,boolean continuePath){
        if(path!=null){
            fleet.setContinuePath(continuePath);
            ArrayList<GraphPoint> newPath = new ArrayList<>(path);
            fleet.setPath(newPath);
            for(Boat boat : fleet.getArrayListBoat()){
                boat.clearPath();
            }
        }
    }
}
