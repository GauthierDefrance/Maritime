package battleengine.factory;

import battleengine.entity.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import static config.GameConfiguration.*;

import java.util.ArrayList;
import java.util.Iterator;


public final class BattleFactory {

    public static Battle createBattle(Fleet fleetA, Fleet fleetB) {
        Battle battle = new Battle(fleetA, fleetB);
        placeEnemyFleet(battle);
        return battle;
    }

    private static void placeEnemyFleet(Battle battle) {
        Fleet fleet = battle.getTeamB();
        Boat tmp;
        ArrayList<Boat> group = new ArrayList<Boat>();
        Iterator<Boat> fleetIterator = fleet.getArrayListBoat().iterator();

        int index=0;
        while (fleetIterator.hasNext()) {
            if(group.size()==SPAWN_ZONE_MAX_BOATS_PER_COLUMN){
                boatPlacerColumn(SPAWN_ZONE_MIN_Y, SPAWN_ZONE_MAX_Y, SPAWN_ZONE_STARTING_X + SPAWN_ZONE_SPAWN_WIDTH*index, group);
                group.clear();
                index++;
            }
            else {
                group.add(fleetIterator.next());
            }
        }

        if(!group.isEmpty()){
            boatPlacerColumn(SPAWN_ZONE_MIN_Y, SPAWN_ZONE_MAX_Y, SPAWN_ZONE_STARTING_X+SPAWN_ZONE_SPAWN_WIDTH*index, group);
        }

        battle.getBoatsInBattleB().getArrayListBoat().addAll(fleet.getArrayListBoat());
    }

    public static void boatPlacerColumn(int yMin, int yMax, int x, ArrayList<Boat> boats) {
        double distance = (double) (yMax - yMin) / (boats.size()+1);
        for(int i = 1; i < boats.size()+1; i++) {
            boats.get(i-1).setPosition(x, (yMin + (i * distance)));
            boats.get(i-1).setAngle(-Math.PI);
        }
    }

}
