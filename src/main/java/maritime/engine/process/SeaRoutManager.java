package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.SeaRoad;
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


    public void pickUpResources(SeaRoad seaRout, Boat boat) {
        if (boat.getPosition().equals(seaRout.getStartSeaRout().getGraphPosition().getPoint())){
            tradeManager.transfer(seaRout.getBuy(), boat.getInventory().getNbRessource(seaRout.getBuy()), boat, seaRout.getStartSeaRout());
            tradeManager.transfer(seaRout.getSold(), boat.getInventory().getNbRessource(seaRout.getSold()), boat, seaRout.getStartSeaRout());
            tradeManager.transfer(seaRout.getSold(), (int) (tradeManager.totalFreeSpace(boat.getInventory()) * seaRout.getRatio()),seaRout.getStartSeaRout(),boat);
        }
    }

    public void sellResources(SeaRoad seaRout, Boat boat){
        if (boat.getPosition().equals(seaRout.getEndSeaRout().getGraphPosition().getPoint())){
            int nbRessource = boat.getInventory().getNbRessource(seaRout.getSold());
            tradeManager.transfer(seaRout.getSold(),nbRessource,boat,seaRout.getEndSeaRout());
            if(!tradeManager.transfer(seaRout.getBuy(), (int) (nbRessource/seaRout.getRatio()),seaRout.getEndSeaRout(),boat)){
                if (tradeManager.totalFreeSpace(boat.getInventory()) >= (int) (nbRessource/seaRout.getRatio()))seaRout.setTime0();
            }
        }
    }
    public void sellAndPickUpAllResources(SeaRoad seaRout){
        for (Boat boat : seaRout.getFleet().getArrayListFleet()){
            pickUpResources(seaRout, boat);
            sellResources(seaRout, boat);
        }
        seaRout.subtractTime(1);
    }
}

