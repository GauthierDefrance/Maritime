package battleengine_trash.engine;

import battleengine_trash.tools.DeepCopy;
import engine.entity.boats.Fleet;

public class Battle {
    private Fleet fleetA, fleetB;
    private Fleet originalA, originalB;

    public Battle(Fleet fleetA, Fleet fleetB) {
        this.fleetA = DeepCopy.copyFleet(fleetA);
        this.fleetB = DeepCopy.copyFleet(fleetB);
        this.originalA = fleetA;
        this.originalB = fleetB;
    }

    public Fleet getFleetA() { return fleetA; }
    public Fleet getFleetB() { return fleetB; }
    public Fleet getOriginalA() { return originalA; }
    public Fleet getOriginalB() { return originalB; }



}
