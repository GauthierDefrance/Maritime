package engine.process;

import engine.Map;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.faction.Faction;

import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * Classe PlayerManager
 * @version 0.1
 */
public class PlayerManager {

    /**
     * Initialize the PlayerManager
     */
    public PlayerManager(){
    }

    /**
     * checks which boat should be displayed to the player
     */
    public void updatePlayerVision(){
        ArrayList<Boat> vision = new ArrayList<>();
        for (Faction faction : Map.getInstance().getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Boat playerBoat : Map.getInstance().getPlayer().getLstBoat()){
                    if(playerBoat.getVisionRadius() /2 >= boat.getPosition().distance(playerBoat.getPosition())){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        for (Faction faction : Map.getInstance().getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Harbor harbor : Map.getInstance().getPlayer().getLstHarbor()){
                    if(harbor.getVisionRadius() /2 >= boat.getPosition().distance(harbor.getPosition())){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        Map.getInstance().getPlayer().setVision(vision);
    }
}
