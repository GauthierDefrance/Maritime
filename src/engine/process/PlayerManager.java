package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.Harbor;
import maritime.engine.entity.boats.Boat;
import maritime.engine.faction.Faction;

import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * Classe PlayerManager
 * @version 0.1
 */
public class PlayerManager {
    private final MapBuilder map;

    /**
     * Initialize the PlayerManager
     */
    public PlayerManager(MapBuilder map){
        this.map = map;
    }

    /**
     * checks which boat should be displayed to the player
     */
    public void updatePlayerVision(){
        ArrayList<Boat> vision = new ArrayList<>();
        for (Faction faction : map.getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Boat playerBoat : map.getPlayer().getLstBoat()){
                    if(playerBoat.getVisionRadius() /2 >= boat.getPosition().distance(playerBoat.getPosition())){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        for (Faction faction : map.getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Harbor harbor : map.getPlayer().getLstHarbor()){
                    if(harbor.getVisionRadius() /2 >= boat.getPosition().distance(harbor.getPosition())){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        map.getPlayer().setVision(vision);
    }
}
