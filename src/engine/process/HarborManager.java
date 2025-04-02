package engine.process;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;

import java.awt.*;

/**
 * @author Kenan Ammad
 * @version 0.1
 */
public class HarborManager {

    /**
     * Typical constructor generating an HarborManager
     */
    public HarborManager(){

    }

    /**
     * Check if a Harbor reached a targeted point in map
     * @param point concerned
     * @return Harbor
     */
    public Harbor pointCollisionToMapHarbor(Point point){
        Harbor harbor1 = null;
        for (Harbor harbor2 : MapGame.getInstance().getLstHarbor()){
            if (GameConfiguration.HITBOX_BOAT - 5 >= point.distance(harbor2.getPosition())){
                if(harbor1 != null){
                    if(point.distance(harbor2.getPosition()) < point.distance(harbor1.getPosition())){
                        harbor1 = harbor2;
                    }
                }
                else harbor1 = harbor2;
            }
        }
        return harbor1;
    }


}
