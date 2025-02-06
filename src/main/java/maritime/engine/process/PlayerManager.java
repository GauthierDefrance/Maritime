package maritime.engine.process;

import maritime.config.MapConfig;
import maritime.engine.entity.Boat;
import maritime.engine.entity.Harbor;
import maritime.engine.faction.Faction;

import java.util.ArrayList;

public class PlayerManager {
    private final MapConfig map;

    public PlayerManager(MapConfig map){
        this.map = map;
    }

    public void updatePlayerVision(){
        ArrayList<Boat> vision = new ArrayList<>();
        for (Faction faction : map.getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Boat playerBoat : map.getPlayer().getLstBoat()){
                    if(playerBoat.getVisionRadius() >= (Math.sqrt(Math.pow((boat.getPosition().getX()-playerBoat.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-playerBoat.getPosition().getY()),2)))){
                        vision.add(boat);}
                }
            }
        }
        for (Faction faction : map.getLstBotFaction()){
            for (Boat boat : faction.getLstBoat()){
                for (Harbor harbor : map.getPlayer().getLstHarbor()){
                    if(harbor.getVisionRadius() >= (Math.sqrt(Math.pow((boat.getPosition().getX()-harbor.getPosition().getX()),2)+Math.pow((boat.getPosition().getY()-harbor.getPosition().getY()),2)))){
                        vision.add(boat);}
                }
            }
        }
        map.getPlayer().setVision(vision);
    }
}
