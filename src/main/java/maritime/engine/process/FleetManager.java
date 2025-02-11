package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;

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

}
