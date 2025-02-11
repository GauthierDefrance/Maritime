package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.SeaRout;
import maritime.engine.entity.boats.Boat;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class SeaRoutManager {
    // Cette classe-là, c'est non, overlap et conflit d'intérêt avec TradeManager en l'état, le système de déplacement et l'économie doivent être totalement déliées.
    private final MapBuilder map;
    private final HarborManager harborManager;
    private final TradeManager tradeManager;

    public SeaRoutManager(MapBuilder map, HarborManager harborManager, TradeManager tradeManager) {
        this.map = map;
        this.harborManager = harborManager;
        this.tradeManager = tradeManager;
    }

    /*
        public void sellResources(SeaRout seaRout,Boat boat){
        if (boat.getPosition().equals(seaRout.getEndSeaRout().getGraphPosition().getPoint())){
            int ressourceNumber = tradeManager.checkRessourceNumber(boat.getInventory(),seaRout.getSold());
            int nb = (int) (ressourceNumber / seaRout.getRatio());
            harborManager.obtainResources(seaRout.getEndSeaRout(),boat,seaRout.getSold(), Math.min(seaRout.getEndSeaRout().getInventory().getContent().getOrDefault(seaRout.getSold(), 0),ressourceNumber));
            harborManager.giveResources(seaRout.getEndSeaRout(),boat,seaRout.getBuy(),Math.min(seaRout.getEndSeaRout().getInventory().getContent().getOrDefault(seaRout.getBuy(), 0),nb));
        }
    }

    public void pickUpResources(SeaRout seaRout,Boat boat){
        if (boat.getPosition().equals(seaRout.getStartSeaRout().getGraphPosition().getPoint())){
            if (tradeManager.checkRessourceNumber(boat.getInventory(),seaRout.getBuy())!=0){
                harborManager.obtainResources(seaRout.getStartSeaRout(),boat,seaRout.getBuy(),tradeManager.checkRessourceNumber(boat.getInventory(),seaRout.getBuy()));
            }
            if (tradeManager.checkRessourceNumber(boat.getInventory(),seaRout.getSold())!=0){
                harborManager.obtainResources(seaRout.getStartSeaRout(),boat,seaRout.getSold(),tradeManager.checkRessourceNumber(boat.getInventory(),seaRout.getSold()));
            }
            int nb = (int) (tradeManager.totalFreeSpace( boat.getInventory()) / seaRout.getRatio());
            harborManager.giveResources(seaRout.getStartSeaRout(),boat,seaRout.getSold(),Math.min(seaRout.getStartSeaRout().getInventory().getContent().getOrDefault(seaRout.getSold(), 0),nb));
        }
    }
    */
    public void sellAndPickUpAllResources(SeaRout seaRout){
        for (Boat boat : seaRout.getFleet().getArrayListFleet()){
            pickUpResources(seaRout, boat);
            sellResources(seaRout, boat);
        }
        seaRout.subtractTime(1);
    }
}

