package engine.process.manager;

import engine.data.MapGame;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.faction.Faction;

import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * Classe PlayerManager
 * @version 1.0
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
        for (Faction faction : MapGame.getInstance().getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Boat playerBoat : MapGame.getInstance().getPlayer().getLstBoat()){
                    if(!vision.contains(boat)) {
                        if (playerBoat.getVisionRadius() / 2 >= boat.getPosition().distance(playerBoat.getPosition())) {
                            vision.add(boat);
                            break;
                        }
                    }
                    else break;
                }
                for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
                    if(!vision.contains(boat)) {
                        if (harbor.getVisionRadius() / 2 >= boat.getPosition().distance(harbor.getPosition())) {
                            vision.add(boat);
                            break;
                        }
                    }
                    else break;
                }
            }
        }
        MapGame.getInstance().getPlayer().setVision(vision);
    }

    public boolean isInPlayerVision(Boat boat){
        for (Boat playerBoat : MapGame.getInstance().getPlayer().getLstBoat()){
            if (playerBoat.getVisionRadius() / 2 >= boat.getPosition().distance(playerBoat.getPosition())) {
                return true;
            }
        }
        for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
            if (harbor.getVisionRadius() / 2 >= boat.getPosition().distance(harbor.getPosition())) {
                return true;
            }
        }
        return false;
    }
}
