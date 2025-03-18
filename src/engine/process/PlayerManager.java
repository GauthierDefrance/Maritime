package engine.process;

import engine.MapGame;
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
        for (Faction faction : MapGame.getInstance().getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Boat playerBoat : MapGame.getInstance().getPlayer().getLstBoat()){
                    if(playerBoat.getVisionRadius() /2 >= boat.getPosition().distance(playerBoat.getPosition())){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        for (Faction faction : MapGame.getInstance().getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
                    if(harbor.getVisionRadius() /2 >= boat.getPosition().distance(harbor.getPosition())){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        MapGame.getInstance().getPlayer().setVision(vision);
    }
}
