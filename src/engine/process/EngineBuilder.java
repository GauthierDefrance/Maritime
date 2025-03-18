package engine.process;

import config.GameConfiguration;
import engine.Map;
import engine.entity.Harbor;
import engine.entity.boats.Fodder;
import engine.entity.boats.Merchant;
import engine.entity.boats.Military;
import engine.entity.boats.Standard;
import engine.faction.Faction;
import engine.faction.Player;
import engine.graph.GraphPoint;
import engine.graph.GraphSegment;
import gui.PopUp;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class containing methods to build (Components)
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @see Component
 * @version 0.3
 */
public class EngineBuilder {
    private static Logger logger = LoggerUtility.getLogger(EngineBuilder.class);

    /**
     * Write log in a logger
     * @param name  text
     */
    private static void loggerWrite(String name) {
        logger.info("creation "+name);
    }

    /**
     * Write log in a logger
     * @param name  text
     * @param param  text
     */
    private static void loggerWrite(String name,String param) {
        logger.info(name+" got "+param);
    }

    public static Standard Standard(String name,GraphPoint graphPoint){
        loggerWrite("Standard name "+name);
        Standard standard = new Standard(name,"",graphPoint);
        return standard;
    }

    public static Standard Standard(String name,GraphPoint graphPoint, Faction faction){
        loggerWrite("Standard name "+name);
        Standard standard = new Standard(name,faction.getColor(),graphPoint);
        return standard;
    }

    public static Fodder Fodder(String name, GraphPoint graphPoint){
        loggerWrite("Fodder name "+name);
        Fodder fodder = new Fodder(name,"",graphPoint);
        return fodder;
    }

    public static Fodder Fodder(String name, GraphPoint graphPoint, Faction faction){
        loggerWrite("Fodder name "+name);
        Fodder fodder = new Fodder(name,"",graphPoint);
        return fodder;
    }

    public static Merchant Merchant(String name, GraphPoint graphPoint){
        loggerWrite("Merchant name "+name);
        Merchant merchant = new Merchant(name,"",graphPoint);
        return merchant;
    }

    public static Merchant Merchant(String name, GraphPoint graphPoint, Faction faction){
        loggerWrite("Merchant name "+name);
        Merchant merchant = new Merchant(name,"",graphPoint);
        return merchant;
    }

    public static Military Military(String name, GraphPoint graphPoint){
        loggerWrite("Military name "+name);
        Military military = new Military(name,"",graphPoint);
        return military;
    }

    public static Harbor harbor(String name,Point position , GraphPoint graphPoint){
        loggerWrite("Harbor name "+name);
        Harbor harbor = new Harbor(name,"",position,graphPoint);
        return harbor;
    }

    public static void mapInit(int choice) {
        mapInit0();
    }

    public static Map mapInit0() {
        Map.getInstance().setTimeStop(false);
        ArrayList<PopUp> lstPopUp =new ArrayList<>();
        ArrayList<Harbor> lstHarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        Player player = new Player("blue");


//        Map.getInstance().put("A",new GraphPoint(new Point(0*GameConfiguration.GAME_SCALE,0*GameConfiguration.GAME_SCALE),"A"));
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
        player.addHarbor(harborB);
        faction1.addHarbor(harborD);

        lstBotFaction.add(faction1);
        lstHarbor.add(harborA);
        lstHarbor.add(harborB);
        lstHarbor.add(harborC);
        lstHarbor.add(harborD);

        lstFaction.addAll(lstBotFaction);
        lstFaction.add(player);
        Map.getInstance().setLstPopUp(lstPopUp);
        Map.getInstance().setLstHarbor(lstHarbor);
        Map.getInstance().setLstFaction(lstFaction);
        Map.getInstance().setLstBotFaction(lstBotFaction);
        Map.getInstance().setMapGraphPoint(mapGraphPoint);
        Map.getInstance().setPlayer(player);
        return Map.getInstance();
    }

}
