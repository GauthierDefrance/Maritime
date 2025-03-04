package battleengine_trash.engine;

import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;

public final class Collision {

    public static Fleet collideGroup(Point p, Fleet fleet){
        Fleet result = new Fleet();
        for(Boat boat : fleet.getArrayListBoat()){
            if(collide(p, boat)){
                result.add(boat);
            }
        }
        return result;
    }

    public static Boolean collide(Point p, Boat boat){
        if (boat.getPosition().distance(p)< GameConfiguration.HITBOX_BOAT/2 ) {
            return true;
        } return false;
    }

}
