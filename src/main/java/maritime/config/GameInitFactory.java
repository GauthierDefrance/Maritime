package maritime.config;

import maritime.engine.entity.*;
import maritime.engine.faction.Faction;
import maritime.engine.faction.Player;
import maritime.engine.graph.GraphPoint;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInitFactory {
    private HashMap<String, GraphPoint> mapGraphPoint;
    private ArrayList<Harbor> LstHarbor;
    private ArrayList<Faction> lstBotFaction;
    private ArrayList<Faction> lstFaction;
    private Player player;

    public GameInitFactory(int choice){init0();
        switch (choice) {
            case 0 -> {
                init0();
            }
            case 1 -> {
                init1();
            }
            default -> {
            }
        }
    }

    public void init0(){
        ArrayList<Harbor> lstfarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        Player player = new Player("blue");

//        map.put("A",new GraphPoint(new Point(0*GameConfiguration.GAME_SCALE,0*GameConfiguration.GAME_SCALE),"A"));


        lstFaction.addAll(lstBotFaction);
        lstFaction.addLast(player);
        this.setLstHarbor(lstfarbor);
        this.setLstFaction(lstFaction);
        this.setLstBotFaction(lstBotFaction);
        this.setMapGraphPoint(mapGraphPoint);
        this.setPlayer(player);
    }
    public void init1(){}

    public ArrayList<Faction> getLstBotFaction() {
        return lstBotFaction;
    }

    public void setLstBotFaction(ArrayList<Faction> lstBotFaction) {
        this.lstBotFaction = lstBotFaction;
    }

    public HashMap<String, GraphPoint> getMapGraphPoint() {
        return mapGraphPoint;
    }

    public void setMapGraphPoint(HashMap<String, GraphPoint> mapGraphPoint) {
        this.mapGraphPoint = mapGraphPoint;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Faction> getLstFaction() {
        return lstFaction;
    }

    public void setLstFaction(ArrayList<Faction> lstFaction) {
        this.lstFaction = lstFaction;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Harbor> getLstHarbor() {
        return LstHarbor;
    }

    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        LstHarbor = lstHarbor;
    }
}
