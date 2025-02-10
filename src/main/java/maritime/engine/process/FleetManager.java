package maritime.engine.process;

import maritime.config.GameInitFactory;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;

public class FleetManager {
    private final GameInitFactory map;
    private final BoatManager boatManager;

    public FleetManager(GameInitFactory map, BoatManager boatManager) {
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
