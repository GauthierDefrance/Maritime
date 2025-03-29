package engine;

import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.faction.Faction;
import engine.faction.Pirate;
import engine.faction.Player;
import engine.graph.GraphPoint;
import gui.PopUp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Kenan Ammad
 * @version 0.3
 */
public class MapGame implements Serializable {
    private static MapGame instance;
    private HashMap<Boat, Boat> HunterPreyHashMap;
    private HashMap<String, GraphPoint> mapGraphPoint;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<Faction> lstBotFaction;
    private ArrayList<Faction> lstFaction;
    private Player player;
    private Pirate pirate;
    private boolean timeStop;
    private ArrayList<PopUp> lstPopUp;

    private MapGame(){}

    public static synchronized MapGame getInstance() {
        if (instance == null) {
            instance = new MapGame();
        } return instance;
    }

    public static void setInstance(MapGame instance) {
        MapGame.instance = instance;
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

    public void addGraphPoint(GraphPoint graphPoint){ mapGraphPoint.put(graphPoint.getIdPoint(),graphPoint); }

    public HashMap<Boat, Boat> getHunterPreyHashMap() {
        return HunterPreyHashMap;
    }

    public void setHunterPreyHashMap(HashMap<Boat, Boat> hunterPreyHashMap) {
        HunterPreyHashMap = hunterPreyHashMap;
    }

    public void addHunterPreyHashMap(Boat hunter, Boat prey) {
        HunterPreyHashMap.put(hunter,prey);
    }

    public void removeHunterPreyHashMap(Boat hunter) {
        HunterPreyHashMap.remove(hunter);
    }

    public Pirate getPirate() {
        return pirate;
    }

    public void setPirate(Pirate pirate) {
        this.pirate = pirate;
    }
}
