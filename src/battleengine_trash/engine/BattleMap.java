package battleengine_trash.engine;

import engine.entity.boats.Fleet;


/**
 * Classe de donnée stockant les bateaux présents sur la bataille
 * @author Gauthier Defrance
 * @version 0.2
 */
public class BattleMap {
    private Fleet teamA;
    private Fleet teamB;
    //private ArrayList<Bullet>
    public BattleMap() {
        teamA = new Fleet();
        teamB = new Fleet();
    }

    public Fleet getTeamA() { return teamA; }
    public Fleet getTeamB() { return teamB; }

    public void setTeamA(Fleet teamA) { this.teamA = teamA; }
    public void setTeamB(Fleet teamB) { this.teamB = teamB; }


}
