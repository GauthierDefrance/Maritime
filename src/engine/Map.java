package engine;

import engine.entity.Harbor;
import engine.faction.Faction;
import engine.faction.Player;
import engine.graph.GraphPoint;
import gui.process.PopUp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Kenan Ammad
 * @version 0.3
 */
public class Map implements Serializable {
    private static Map instance;
    private HashMap<String, GraphPoint> mapGraphPoint;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<Faction> lstBotFaction;
    private ArrayList<Faction> lstFaction;
    private Player player;
    private boolean timeStop;
    private ArrayList<PopUp> lstPopUp;

    private Map(){}

    public static synchronized Map getInstance() {
        if (instance == null) {
            instance = new Map();
        } return instance;
    }

    public static void setInstance(Map instance) {
        Map.instance = instance;
    }

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
        return lstHarbor;
    }

    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        this.lstHarbor = lstHarbor;
    }

    public boolean isTimeStop() {
        return timeStop;
    }

    public void setTimeStop(boolean timeStop) {
        this.timeStop = timeStop;
    }

    public ArrayList<PopUp> getLstPopUp() {
        return lstPopUp;
    }

    public void setLstPopUp(ArrayList<PopUp> lstPopUp) {
        this.lstPopUp = lstPopUp;
    }

    public void addPopUp(PopUp PopUp) {
        this.lstPopUp.add(PopUp);
    }

    public void removePopUp(PopUp PopUp) {
        this.lstPopUp.remove(PopUp);
    }

    public void removeFaction(Faction faction) {
        this.lstFaction.remove(faction);
        this.lstBotFaction.remove(faction);
    }

    public void add(GraphPoint graphPoint){ mapGraphPoint.put(graphPoint.getIdPoint(),graphPoint); }
}
