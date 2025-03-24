package engine.process.builder;

import engine.battleengine.data.Battle;
import engine.battleengine.data.Bullet;
import engine.battleengine.utilities.BattlePlaceFleet;
import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Harbor;
import engine.entity.boats.*;
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

    public static Bullet createBullet(int x, int y, double angle, String color){
        return new Bullet(x, y, GameConfiguration.DEFAULT_BULLET_SPEED, angle, color);
    }

    public static Battle createBattle(Fleet fleetA, Fleet fleetB) {
        Battle battle = new Battle(fleetA, fleetB);
        BattlePlaceFleet.placeEnemyFleet(battle);
        return battle;
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
        mapInit1();
    }

    public static MapGame mapInit0() {
        MapGame.getInstance().setTimeStop(false);
        ArrayList<PopUp> lstPopUp =new ArrayList<>();
        ArrayList<Harbor> lstHarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        MapGame.getInstance().setMapGraphPoint(mapGraphPoint);
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

        MapGame.getInstance().addGraphPoint(AHarbor);
        MapGame.getInstance().addGraphPoint(BHarbor);
        MapGame.getInstance().addGraphPoint(CHarbor);
        MapGame.getInstance().addGraphPoint(DHarbor);

        MapGame.getInstance().addGraphPoint(P1);
        MapGame.getInstance().addGraphPoint(P2);
        MapGame.getInstance().addGraphPoint(P3);
        MapGame.getInstance().addGraphPoint(P4);
        MapGame.getInstance().addGraphPoint(P5);
        MapGame.getInstance().addGraphPoint(P6);
        MapGame.getInstance().addGraphPoint(P7);

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
        Harbor harborB = new Harbor("harborB","",new Point((150)*GameConfiguration.GAME_SCALE,(300-temp)*GameConfiguration.GAME_SCALE),BHarbor);
        Harbor harborC = new Harbor("harborC","",new Point((340)*GameConfiguration.GAME_SCALE,(130-temp)*GameConfiguration.GAME_SCALE),CHarbor);
        Harbor harborD = new Harbor("harborD","",new Point((580)*GameConfiguration.GAME_SCALE,(225-temp)*GameConfiguration.GAME_SCALE),DHarbor);

        Faction faction1 = new Faction("red");

        lstBotFaction.add(faction1);
        lstHarbor.add(harborA);
        lstHarbor.add(harborB);
        lstHarbor.add(harborC);
        lstHarbor.add(harborD);

        lstFaction.addAll(lstBotFaction);
        lstFaction.add(player);
        MapGame.getInstance().setLstPopUp(lstPopUp);
        MapGame.getInstance().setLstHarbor(lstHarbor);
        MapGame.getInstance().setLstFaction(lstFaction);
        MapGame.getInstance().setLstBotFaction(lstBotFaction);
        MapGame.getInstance().setPlayer(player);
        return MapGame.getInstance();
    }
    public static MapGame mapInit1() {
        MapGame.getInstance().setTimeStop(false);
        ArrayList<PopUp> lstPopUp =new ArrayList<>();
        ArrayList<Harbor> lstHarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        MapGame.getInstance().setMapGraphPoint(mapGraphPoint);
        Player player = new Player("blue");


        GraphPoint AHarbor = new GraphPoint(new Point(100*GameConfiguration.GAME_SCALE,61*GameConfiguration.GAME_SCALE),"AHarbor");
        GraphPoint BHarbor = new GraphPoint(new Point(276*GameConfiguration.GAME_SCALE,47*GameConfiguration.GAME_SCALE),"BHarbor");
        GraphPoint CHarbor = new GraphPoint(new Point(279*GameConfiguration.GAME_SCALE,143*GameConfiguration.GAME_SCALE),"CHarbor");
        GraphPoint DHarbor = new GraphPoint(new Point(379*GameConfiguration.GAME_SCALE,72*GameConfiguration.GAME_SCALE),"DHarbor");
        GraphPoint EHarbor = new GraphPoint(new Point(76*GameConfiguration.GAME_SCALE,229*GameConfiguration.GAME_SCALE),"EHarbor");
        GraphPoint FHarbor = new GraphPoint(new Point(152*GameConfiguration.GAME_SCALE,267*GameConfiguration.GAME_SCALE),"FHarbor");
        GraphPoint GHarbor = new GraphPoint(new Point(347*GameConfiguration.GAME_SCALE,256*GameConfiguration.GAME_SCALE),"GHarbor");
        GraphPoint HHarbor = new GraphPoint(new Point(466*GameConfiguration.GAME_SCALE,281*GameConfiguration.GAME_SCALE),"HHarbor");
        GraphPoint IHarbor = new GraphPoint(new Point(519*GameConfiguration.GAME_SCALE,86*GameConfiguration.GAME_SCALE),"IHarbor");
        GraphPoint JHarbor = new GraphPoint(new Point(627*GameConfiguration.GAME_SCALE,167*GameConfiguration.GAME_SCALE),"JHarbor");
        GraphPoint KHarbor = new GraphPoint(new Point(573*GameConfiguration.GAME_SCALE,218*GameConfiguration.GAME_SCALE),"KHarbor");

        Harbor harborA = new Harbor("harbor-A","",new Point(68*GameConfiguration.GAME_SCALE,48*GameConfiguration.GAME_SCALE),AHarbor);
        Harbor harborB = new Harbor("harbor-B","",new Point(255*GameConfiguration.GAME_SCALE,17*GameConfiguration.GAME_SCALE),BHarbor);
        Harbor harborC = new Harbor("harbor-C","",new Point(315*GameConfiguration.GAME_SCALE,148*GameConfiguration.GAME_SCALE),CHarbor);
        Harbor harborD = new Harbor("harbor-D","",new Point(350*GameConfiguration.GAME_SCALE,30*GameConfiguration.GAME_SCALE),DHarbor);
        Harbor harborE = new Harbor("harbor-E","",new Point(110*GameConfiguration.GAME_SCALE,215*GameConfiguration.GAME_SCALE),EHarbor);
        Harbor harborF = new Harbor("harbor-F","",new Point(122*GameConfiguration.GAME_SCALE,260*GameConfiguration.GAME_SCALE),FHarbor);
        Harbor harborG = new Harbor("harbor-G","",new Point(320*GameConfiguration.GAME_SCALE,275*GameConfiguration.GAME_SCALE),GHarbor);
        Harbor harborH = new Harbor("harbor-H","",new Point(478*GameConfiguration.GAME_SCALE,296*GameConfiguration.GAME_SCALE),HHarbor);
        Harbor harborI = new Harbor("harbor-I","",new Point(502*GameConfiguration.GAME_SCALE,44*GameConfiguration.GAME_SCALE),IHarbor);
        Harbor harborJ = new Harbor("harbor-J","",new Point(597*GameConfiguration.GAME_SCALE,160*GameConfiguration.GAME_SCALE),JHarbor);
        Harbor harborK = new Harbor("harbor-K","",new Point(609*GameConfiguration.GAME_SCALE,229*GameConfiguration.GAME_SCALE),KHarbor);

        MapGame.getInstance().addGraphPoint(AHarbor);
        MapGame.getInstance().addGraphPoint(BHarbor);
        MapGame.getInstance().addGraphPoint(CHarbor);
        MapGame.getInstance().addGraphPoint(DHarbor);
        MapGame.getInstance().addGraphPoint(EHarbor);
        MapGame.getInstance().addGraphPoint(FHarbor);
        MapGame.getInstance().addGraphPoint(GHarbor);
        MapGame.getInstance().addGraphPoint(HHarbor);
        MapGame.getInstance().addGraphPoint(IHarbor);
        MapGame.getInstance().addGraphPoint(JHarbor);
        MapGame.getInstance().addGraphPoint(KHarbor);

        lstHarbor.add(harborA);
        lstHarbor.add(harborB);
        lstHarbor.add(harborC);
        lstHarbor.add(harborD);
        lstHarbor.add(harborE);
        lstHarbor.add(harborF);
        lstHarbor.add(harborG);
        lstHarbor.add(harborH);
        lstHarbor.add(harborI);
        lstHarbor.add(harborJ);
        lstHarbor.add(harborK);

        lstFaction.addAll(lstBotFaction);
        lstFaction.add(player);
        MapGame.getInstance().setLstPopUp(lstPopUp);
        MapGame.getInstance().setLstHarbor(lstHarbor);
        MapGame.getInstance().setLstFaction(lstFaction);
        MapGame.getInstance().setLstBotFaction(lstBotFaction);
        MapGame.getInstance().setPlayer(player);
        return MapGame.getInstance();
    }

}
