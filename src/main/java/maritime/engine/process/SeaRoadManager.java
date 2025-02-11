package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.SeaRoad;
import maritime.engine.entity.boats.Boat;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class SeaRoadManager {
    // Cette classe-là, c'est non, overlap et conflit d'intérêt avec TradeManager en l'état, le système de déplacement et l'économie doivent être totalement déliées.
    private final MapBuilder map;
    private final HarborManager harborManager;
    private final TradeManager tradeManager;

    public SeaRoadManager(MapBuilder map, HarborManager harborManager, TradeManager tradeManager) {
        this.map = map;
        this.harborManager = harborManager;
        this.tradeManager = tradeManager;
    }


    public void pickUpResources(SeaRoad seaRoad, Boat boat) {
        if (boat.getPosition().equals(seaRoad.getStartSeaRout().getGraphPosition().getPoint())){
            tradeManager.transfer(seaRoad.getBuy(), boat.getInventory().getNbRessource(seaRoad.getBuy()), boat, seaRoad.getStartSeaRout());
            tradeManager.transfer(seaRoad.getSold(), boat.getInventory().getNbRessource(seaRoad.getSold()), boat, seaRoad.getStartSeaRout());
            if(!tradeManager.transfer(seaRoad.getSold(), Math.min(tradeManager.totalFreeSpace(boat.getInventory()),(int) (tradeManager.totalFreeSpace(boat.getInventory()) * seaRoad.getRatio())), seaRoad.getStartSeaRout(),boat)){
                tradeManager.transfer(seaRoad.getSold(), seaRoad.getStartSeaRout().getInventory().getNbRessource(seaRoad.getSold()), seaRoad.getStartSeaRout(),boat);
            }
        }
    }

    public void sellResources(SeaRoad seaRoad, Boat boat){
        if (boat.getPosition().equals(seaRoad.getEndSeaRout().getGraphPosition().getPoint())){
            int nbRessource = boat.getInventory().getNbRessource(seaRoad.getSold());
            if(tradeManager.transfer(seaRoad.getSold(),nbRessource,boat, seaRoad.getEndSeaRout())) seaRoad.addTime(nbRessource);
            if(!tradeManager.transfer(seaRoad.getBuy(), (int) (nbRessource/ seaRoad.getRatio()), seaRoad.getEndSeaRout(),boat)){
                if (tradeManager.totalFreeSpace(boat.getInventory()) >= (int) (nbRessource/ seaRoad.getRatio()))
                    seaRoad.setTime0();
            }
        }
    }
    public void sellAndPickUpAllResources(SeaRoad seaRoad){
        for (Boat boat : seaRoad.getFleet().getArrayListFleet()){
            pickUpResources(seaRoad, boat);
            sellResources(seaRoad, boat);
        }
        seaRoad.subtractTime(1);
    }
}

