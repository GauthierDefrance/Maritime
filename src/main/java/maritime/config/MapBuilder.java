package maritime.config;

import maritime.engine.entity.*;
import maritime.engine.faction.Faction;
import maritime.engine.faction.Player;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.GraphSegment;
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
    public MapBuilder(){
        timeStop = false;
        init0();
    }

    public void init0(){
        ArrayList<PopUp> lstPopUp =new ArrayList<>();
        ArrayList<Harbor> lstHarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        Player player = new Player("blue");
//        map.put("A",new GraphPoint(new Point(0*GameConfiguration.GAME_SCALE,0*GameConfiguration.GAME_SCALE),"A"));
        GraphPoint AHarbor = new GraphPoint(new Point(65*GameConfiguration.GAME_SCALE,80*GameConfiguration.GAME_SCALE),"AHarbor");
        GraphPoint BHarbor = new GraphPoint(new Point(150*GameConfiguration.GAME_SCALE,300*GameConfiguration.GAME_SCALE),"BHarbor");
        GraphPoint CHarbor = new GraphPoint(new Point(340*GameConfiguration.GAME_SCALE,130*GameConfiguration.GAME_SCALE),"CHarbor");
        GraphPoint DHarbor = new GraphPoint(new Point(580*GameConfiguration.GAME_SCALE,225*GameConfiguration.GAME_SCALE),"DHarbor");

        GraphPoint P1 = new GraphPoint(new Point(135*GameConfiguration.GAME_SCALE,125*GameConfiguration.GAME_SCALE),"P1");
        GraphPoint P2 = new GraphPoint(new Point(255*GameConfiguration.GAME_SCALE,150*GameConfiguration.GAME_SCALE),"P2");
        GraphPoint P3 = new GraphPoint(new Point(140*GameConfiguration.GAME_SCALE,225*GameConfiguration.GAME_SCALE),"P3");
        GraphPoint P4 = new GraphPoint(new Point(200*GameConfiguration.GAME_SCALE,240*GameConfiguration.GAME_SCALE),"P4");
        GraphPoint P5 = new GraphPoint(new Point(340*GameConfiguration.GAME_SCALE,215*GameConfiguration.GAME_SCALE),"P5");
        GraphPoint P6 = new GraphPoint(new Point(420*GameConfiguration.GAME_SCALE,160*GameConfiguration.GAME_SCALE),"P6");
        GraphPoint P7 = new GraphPoint(new Point(490*GameConfiguration.GAME_SCALE,205*GameConfiguration.GAME_SCALE),"P7");

        AHarbor.addSegment(new GraphSegment(P1, (int) AHarbor.getPoint().distance(P1.getPoint())));

        BHarbor.addSegment(new GraphSegment(P3, (int) BHarbor.getPoint().distance(P3.getPoint())));
        BHarbor.addSegment(new GraphSegment(P4, (int) BHarbor.getPoint().distance(P4.getPoint())));

        CHarbor.addSegment(new GraphSegment(P2, (int) CHarbor.getPoint().distance(P2.getPoint())));
        CHarbor.addSegment(new GraphSegment(P5, (int) CHarbor.getPoint().distance(P5.getPoint())));
        CHarbor.addSegment(new GraphSegment(P6, (int) CHarbor.getPoint().distance(P6.getPoint())));

        DHarbor.addSegment(new GraphSegment(P7, (int) DHarbor.getPoint().distance(P7.getPoint())));

        P1.addSegment(new GraphSegment(AHarbor, (int) P1.getPoint().distance(AHarbor.getPoint())));
        P1.addSegment(new GraphSegment(P2, (int) P1.getPoint().distance(P2.getPoint())));
        P1.addSegment(new GraphSegment(P3, (int) P1.getPoint().distance(P3.getPoint())));
        P1.addSegment(new GraphSegment(P4, (int) P1.getPoint().distance(P4.getPoint())));

        P2.addSegment(new GraphSegment(CHarbor, (int) P2.getPoint().distance(CHarbor.getPoint())));
        P2.addSegment(new GraphSegment(P1, (int) P2.getPoint().distance(P1.getPoint())));
        P2.addSegment(new GraphSegment(P3, (int) P2.getPoint().distance(P3.getPoint())));
        P2.addSegment(new GraphSegment(P4, (int) P2.getPoint().distance(P4.getPoint())));
        P2.addSegment(new GraphSegment(P5, (int) P2.getPoint().distance(P5.getPoint())));

        P3.addSegment(new GraphSegment(BHarbor, (int) P3.getPoint().distance(BHarbor.getPoint())));
        P3.addSegment(new GraphSegment(P1, (int) P3.getPoint().distance(P1.getPoint())));
        P3.addSegment(new GraphSegment(P2, (int) P3.getPoint().distance(P2.getPoint())));
        P3.addSegment(new GraphSegment(P4, (int) P3.getPoint().distance(P4.getPoint())));

        P4.addSegment(new GraphSegment(BHarbor, (int) P4.getPoint().distance(BHarbor.getPoint())));
        P4.addSegment(new GraphSegment(P1, (int) P4.getPoint().distance(P1.getPoint())));
        P4.addSegment(new GraphSegment(P2, (int) P4.getPoint().distance(P2.getPoint())));
        P4.addSegment(new GraphSegment(P3, (int) P4.getPoint().distance(P3.getPoint())));
        P4.addSegment(new GraphSegment(P5, (int) P4.getPoint().distance(P5.getPoint())));

        P5.addSegment(new GraphSegment(CHarbor, (int) P5.getPoint().distance(CHarbor.getPoint())));
        P5.addSegment(new GraphSegment(P2, (int) P5.getPoint().distance(P2.getPoint())));
        P5.addSegment(new GraphSegment(P4, (int) P5.getPoint().distance(P4.getPoint())));
        P5.addSegment(new GraphSegment(P6, (int) P5.getPoint().distance(P6.getPoint())));
        P5.addSegment(new GraphSegment(P7, (int) P5.getPoint().distance(P7.getPoint())));

        P6.addSegment(new GraphSegment(CHarbor, (int) P6.getPoint().distance(CHarbor.getPoint())));
        P6.addSegment(new GraphSegment(P5, (int) P6.getPoint().distance(P5.getPoint())));
        P6.addSegment(new GraphSegment(P6, (int) P6.getPoint().distance(P6.getPoint())));

        P7.addSegment(new GraphSegment(DHarbor, (int) P7.getPoint().distance(DHarbor.getPoint())));
        P7.addSegment(new GraphSegment(P5, (int) P7.getPoint().distance(P5.getPoint())));
        P7.addSegment(new GraphSegment(P6, (int) P7.getPoint().distance(P6.getPoint())));

        int temp = 30;
        Harbor harborA = new Harbor("harborA","",new Point((65)*GameConfiguration.GAME_SCALE,(80-temp)*GameConfiguration.GAME_SCALE),AHarbor);
        Harbor harborB = new Harbor("harborA","",new Point((150)*GameConfiguration.GAME_SCALE,(300-temp)*GameConfiguration.GAME_SCALE),BHarbor);
        Harbor harborC = new Harbor("harborA","",new Point((340)*GameConfiguration.GAME_SCALE,(130-temp)*GameConfiguration.GAME_SCALE),CHarbor);
        Harbor harborD = new Harbor("harborA","",new Point((580)*GameConfiguration.GAME_SCALE,(225-temp)*GameConfiguration.GAME_SCALE),DHarbor);

        Faction faction1 = new Faction("red");


        player.addHarbor(harborA);
        faction1.addHarbor(harborD);

        lstBotFaction.add(faction1);
        lstHarbor.add(harborA);
        lstHarbor.add(harborB);
        lstHarbor.add(harborC);
        lstHarbor.add(harborD);

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
