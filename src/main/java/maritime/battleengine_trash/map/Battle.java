package maritime.battleengine_trash.map;

import maritime.battleengine_trash.team.Team;
import maritime.battleengine_trash.zone.SpawnZone;

import java.util.Timer;

public class Battle {
    private final Team teamA;
    private final Team teamB;

    private SpawnZone spawnZoneA;
    private SpawnZone spawnZoneB;

    private Timer timer;

    public Battle(Team teamA, Team teamB, SpawnZone spawnZoneA, SpawnZone spawnZoneB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.spawnZoneA = spawnZoneA;
        this.spawnZoneB = spawnZoneB;
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public SpawnZone getSpawnZoneA() {
        return spawnZoneA;
    }

    public SpawnZone getSpawnZoneB() {
        return spawnZoneB;
    }


}
