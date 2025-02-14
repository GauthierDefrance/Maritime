package maritime.battleengine_trash.map;

import maritime.battleengine_trash.zone.SpawnZone;
import maritime.engine.entity.boats.Fleet;

import java.util.Timer;

public class Battle {
    private Fleet teamA;
    private Fleet teamB;

    private SpawnZone spawnZoneA;
    private SpawnZone spawnZoneB;

    private Timer timer;

    public Battle(Fleet teamA, Fleet teamB, SpawnZone spawnZoneA, SpawnZone spawnZoneB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.spawnZoneA = spawnZoneA;
        this.spawnZoneB = spawnZoneB;
    }

    public Fleet getTeamA() {
        return teamA;
    }

    public Fleet getTeamB() {
        return teamB;
    }

    public SpawnZone getSpawnZoneA() {
        return spawnZoneA;
    }

    public SpawnZone getSpawnZoneB() {
        return spawnZoneB;
    }


}
