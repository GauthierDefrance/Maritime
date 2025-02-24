package battleengine_trash.battleengine_trash.process;

import battleengine_trash.battleengine_trash.team.Team;
import config.MapBuilder;
import engine.entity.boats.Boat;
import engine.process.BoatManager;

public class TeamManager {

    private BoatManager boatManager;

    public TeamManager(MapBuilder map) {
        boatManager = new BoatManager(map);
    }
    public TeamManager() {
        boatManager = new BoatManager(null);
    }

    public void move(Team team) {
        for(Boat boat : team.getFleet().getArrayListFleet()){
            if(boat.getPath()!=null&&boat.getPath().size()>0){
                boatManager.approachingToPoint(boat,boat.getPath().get(boat.getIPath()));
            }
        }
    }



}
