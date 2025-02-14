package maritime.config;

import maritime.engine.entity.*;
import maritime.engine.faction.Faction;
import maritime.engine.faction.Player;
import maritime.engine.graph.GraphPoint;
import maritime.gui.PopUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author @Kenan Ammad
 * @version 0.3
 */
public class MapBuilder {
    private HashMap<String, GraphPoint> mapGraphPoint;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<Faction> lstBotFaction;
    private ArrayList<Faction> lstFaction;
    private Player player;
    private boolean timeStop;
    private ArrayList<PopUp> lstPopUp;

    public MapBuilder(int choice){
        timeStop = false;
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
        ArrayList<PopUp> lstPopUp =new ArrayList<>();
        ArrayList<Harbor> lstHarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        Player player = new Player("blue");

//        map.put("A",new GraphPoint(new Point(0*GameConfiguration.GAME_SCALE,0*GameConfiguration.GAME_SCALE),"A"));
//        GraphPoint AHarbor = new GraphPoint(new Point(95*GameConfiguration.GAME_SCALE,65*GameConfiguration.GAME_SCALE),"AHarbor");
//        GraphPoint A = new GraphPoint(new Point(130*GameConfiguration.GAME_SCALE,130*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(225*GameConfiguration.GAME_SCALE,125*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(180*GameConfiguration.GAME_SCALE,90*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(275*GameConfiguration.GAME_SCALE,50*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(65*GameConfiguration.GAME_SCALE,85*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(185*GameConfiguration.GAME_SCALE,235*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(185*GameConfiguration.GAME_SCALE,165*GameConfiguration.GAME_SCALE),"A");
//        GraphPoint A = new GraphPoint(new Point(205*GameConfiguration.GAME_SCALE,140*GameConfiguration.GAME_SCALE),"A");


        lstFaction.addAll(lstBotFaction);
        lstFaction.addLast(player);
        this.setLstPopUp(lstPopUp);
        this.setLstHarbor(lstHarbor);
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
}
