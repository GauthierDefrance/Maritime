package battleengine.factory;

import battleengine.entity.Battle;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

public final class BattleFactory {

    public static Battle createBattle(Fleet fleetA, Fleet fleetB) {
        Battle battle = new Battle(fleetA, fleetB);
        initEnemyFleet(battle);
        return battle;
    }

    private static void initEnemyFleet(Battle battle) {
        Fleet fleet = battle.getTeamB();
        int x = (int) GameConfiguration.DEFAULT_ENEMY_SPAWN_ZONE_COORDINATE.getX()*GameConfiguration.GAME_SCALE;
        int y = (int) 180*GameConfiguration.GAME_SCALE;
        int index = 0;

//        fleet.getArrayListBoat().get(0).setAngle(Math.PI);
//        fleet.getArrayListBoat().get(0).setPosition(x, y + (20 * index * GameConfiguration.GAME_SCALE));
//        battle.getBoatsInBattleB().add(fleet.getArrayListBoat().get(0));

        for(Boat boat: fleet.getArrayListBoat()){
            if(!battle.getBoatsInBattleB().getArrayListBoat().contains(boat)) {
                boat.setAngle(Math.PI);
                boat.setPosition(x, y + (20 * index * GameConfiguration.GAME_SCALE));
                battle.getBoatsInBattleB().add(boat);
                index++;
            }

        }
    }

}
