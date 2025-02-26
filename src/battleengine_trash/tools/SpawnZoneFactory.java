package battleengine_trash.tools;

import battleengine_trash.engine.SpawnZone;
import config.GameConfiguration;
import engine.entity.boats.Fleet;

import java.awt.*;

public final class SpawnZoneFactory {

    public static SpawnZone buildSpawnZone(final int x, final int y, final int width, final int height, Fleet fleet) {
        return new SpawnZone(new Point(x,y),width, height, fleet.getArrayListBoat().get(0).getColor(),fleet);
    }

    public static SpawnZone buildDefaultSpawnZone() {
        return new SpawnZone(GameConfiguration.DEFAULT_SPAWN_ZONE_COORDINATE, GameConfiguration.DEFAULT_SPAWN_ZONE_WIDTH, GameConfiguration.DEFAULT_SPAWN_ZONE_HEIGHT, GameConfiguration.DEFAULT_SPAWN_ZONE_COLOR, new Fleet());
    }

    public static SpawnZone buildDefaultEnnemySpawnZone() {
        return new SpawnZone(GameConfiguration.DEFAULT_ENEMY_SPAWN_ZONE_COORDINATE, GameConfiguration.DEFAULT_SPAWN_ZONE_WIDTH, GameConfiguration.DEFAULT_SPAWN_ZONE_HEIGHT, GameConfiguration.DEFAULT_SPAWN_ZONE_COLOR, new Fleet());
    }



}
