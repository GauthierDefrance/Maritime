package maritime.engine.process;


import maritime.config.GameInitFactory;
import maritime.engine.entity.boats.Boat;
import maritime.engine.faction.Faction;

/**
 * @author @Kenan Ammad
 * Classe FactionManager
 */
public class FactionManager {
    private final GameInitFactory map;
    private final BoatManager boatManager;
    private final HarborManager harborManager;

    public FactionManager(GameInitFactory map) {
        this.map = map;
        this.boatManager = new BoatManager(map);
        this.harborManager = new HarborManager(map);
    }

    public void moveFactionBoat(Faction faction){
        for (Boat boat : faction.getLstBoat()){
            boatManager.followThePath(boat);
        }
    }
    public void moveAllFactionBoat(){
        for (Faction faction : map.getLstFaction()){
            moveFactionBoat(faction);
        }
    }

}
