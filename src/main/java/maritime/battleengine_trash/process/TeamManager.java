package maritime.battleengine_trash.process;
import maritime.battleengine_trash.map.Battle;
import maritime.engine.graph.GraphPoint;
import maritime.engine.process.BoatManager;
import maritime.engine.entity.boats.Boat;

import java.awt.*;

public class TeamManager {

    private Battle battle;
    private BoatManager boatManager;

    public TeamManager(Battle battle) {
        this.battle = battle;
        boatManager = boatManager
    }

    public void moveBoats(){
        for (Boat b : battle.getTeamA().getAliveFleet().getArrayListFleet()){
            (b,b.getNextGraphPoint());
        }
    }


    public void MoveBoat(Boat b, GraphPoint gp){

    }

}
