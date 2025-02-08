package maritime.engine.process;

import maritime.config.GameInitFactory;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.Harbor;
import maritime.engine.faction.Faction;
import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * Classe PlayerManager
 */

public class PlayerManager {
    private final GameInitFactory map;

    public PlayerManager(GameInitFactory map){
        this.map = map;
    }

    public void updatePlayerVision(){
        ArrayList<Boat> vision = new ArrayList<>();
        for (Faction faction : map.getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Boat playerBoat : map.getPlayer().getLstBoat()){
                    if(playerBoat.getVisionRadius() /2 >= Math.sqrt(Math.pow((boat.getPosition().getX()-playerBoat.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-playerBoat.getPosition().getY()),2))){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        for (Faction faction : map.getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Harbor harbor : map.getPlayer().getLstHarbor()){
                    if(harbor.getVisionRadius() /2 >= Math.sqrt(Math.pow((boat.getPosition().getX()-harbor.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-harbor.getPosition().getY()),2))){
                        if(!vision.contains(boat)){vision.add(boat);}
                    }
                }
            }
        }
        map.getPlayer().setVision(vision);
    }
}
