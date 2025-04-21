package engine.battleengine.utilities;

import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import static config.GameConfiguration.*;

import java.util.ArrayList;
import java.util.Iterator;


public final class BattlePlaceFleet {

    public static void placeEnemyFleet(Fleet fleet,int x) {
        ArrayList<Boat> group = new ArrayList<>();
        Iterator<Boat> fleetIterator = fleet.getArrayListBoat().iterator();

        int index=0;
        while (fleetIterator.hasNext()) {
            if(group.size()==SPAWN_ZONE_MAX_BOATS_PER_COLUMN){
                boatPlacerColumn(SPAWN_ZONE_MIN_Y, SPAWN_ZONE_MAX_Y, x+ (int)HITBOX_BOAT*index, group);
                group.clear();
                index++;
            }
            else {
                group.add(fleetIterator.next());
            }
        }

        if(!group.isEmpty()){
            boatPlacerColumn(SPAWN_ZONE_MIN_Y, SPAWN_ZONE_MAX_Y, x+(int)HITBOX_BOAT*index, group);
        }
    }

    public static void boatPlacerColumn(int yMin, int yMax, int x, ArrayList<Boat> boats) {
        double distance = (double) (yMax - yMin) / (boats.size()+1);
        int i = 0;
        for(Boat boat : boats) {
            i++;
            boat.setPosition(x, (yMin + (i * distance)));
            if(x>320*GAME_SCALE)boat.setAngle(-Math.PI);
            else boat.setAngle(0);
        }
    }

}
