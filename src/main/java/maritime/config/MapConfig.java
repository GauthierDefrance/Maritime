package maritime.config;

import maritime.engine.entity.Harbor;
import maritime.engine.faction.Faction;
import maritime.engine.faction.Player;
import maritime.engine.graph.GraphPoint;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MapConfig {
    private HashMap<String, GraphPoint> mapGraphPoint;
    private ArrayList<Harbor> LstHarbor;
    private ArrayList<Faction> lstBotFaction;
    private ArrayList<Faction> lstFaction;
    private Player player;

    public MapConfig(){
        init();
    }

    public abstract void init();

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
