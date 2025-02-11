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

    public void allContinuePathFalse(Fleet fleet){
        for (Boat boat : fleet.getArrayListFleet()){
            boat.setContinuePath(false);
        }
    }

    public void allContinuePathTrue(Fleet fleet){
        for (Boat boat : fleet.getArrayListFleet()){
            boat.setContinuePath(true);
        }
    }
}
