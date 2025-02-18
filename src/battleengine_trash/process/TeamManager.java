package battleengine_trash.process;
import battleengine_trash.map.Battle;
import engine.graph.GraphPoint;
import engine.process.BoatManager;
import engine.entity.boats.Boat;

import java.awt.*;

public class TeamManager {

    private Battle battle;
    private BoatManager boatManager;

    public TeamManager(Battle battle) {
        this.battle = battle;
    }

    public void moveBoats(){
        for (Boat b : battle.getTeamA().getAliveFleet().getArrayListFleet()){
            boatManager.approachingToPoint(b,b.getNextGraphPoint());
        }
    }


    public void MoveBoat(Boat b, GraphPoint gp){

    }

}
