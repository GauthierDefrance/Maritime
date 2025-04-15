package engine.process.creational;

import engine.battleengine.data.Battle;
import engine.battleengine.data.Bullet;
import engine.battleengine.utilities.BattlePlaceFleet;
import config.GameConfiguration;
import engine.MapGame;
import engine.data.Fleet;
import engine.data.entity.Harbor;
import engine.data.entity.boats.*;
import engine.data.faction.Faction;
import engine.data.faction.Pirate;
import engine.data.faction.Player;
import engine.data.graph.GraphPoint;
import engine.data.graph.GraphSegment;
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

    public static Battle createBattle(Faction factionA, Faction factionB, Fleet fleetA, Fleet fleetB) {
        Battle battle;
        if(factionA instanceof Player) {
            battle = new Battle(factionA,factionB,fleetA,fleetB);
        }
        else {
            battle = new Battle(factionB,factionA,fleetB,fleetA);
        }
        for(Boat boat : battle.getTeamA().getArrayListBoat()) {
            battle.getHunterPreyHashMap().put(boat, null);
            battle.getHunterPreyPointHashMap().put(boat, null);
            battle.getReloadingHashMap().put(boat, GameConfiguration.RELOAD_TIME);
        }
        for(Boat boat : battle.getTeamB().getArrayListBoat()) {
            battle.getHunterPreyHashMap().put(boat, null);
            battle.getHunterPreyPointHashMap().put(boat, null);
            battle.getReloadingHashMap().put(boat, GameConfiguration.RELOAD_TIME);
        }
        BattlePlaceFleet.placeEnemyFleet(battle);
        return battle;
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
        HashMap<Boat, Boat> hunterPreyHashMap = new HashMap<>();
        MapGame.getInstance().setMapGraphPoint(mapGraphPoint);
        MapGame.getInstance().setHunterPreyHashMap(hunterPreyHashMap);
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
        HashMap<Boat, Boat> hunterPreyHashMap = new HashMap<>();
        MapGame.getInstance().setMapGraphPoint(mapGraphPoint);
        MapGame.getInstance().setHunterPreyHashMap(hunterPreyHashMap);
        Player player = new Player("blue");
        Pirate pirate = new Pirate("orange");

        //faction init
        Faction faction1 = new Faction("red");
        lstBotFaction.add(faction1);

        //Harbor init
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

        //GraphPoint init
        GraphPoint P1 = new GraphPoint(new Point(40*GameConfiguration.GAME_SCALE,230*GameConfiguration.GAME_SCALE),"P1");
        GraphPoint P2 = new GraphPoint(new Point(64*GameConfiguration.GAME_SCALE,170*GameConfiguration.GAME_SCALE),"P2");
        GraphPoint P3 = new GraphPoint(new Point(70*GameConfiguration.GAME_SCALE,310*GameConfiguration.GAME_SCALE),"P3");
        GraphPoint P4 = new GraphPoint(new Point(120*GameConfiguration.GAME_SCALE,170*GameConfiguration.GAME_SCALE),"P4");
        GraphPoint P5 = new GraphPoint(new Point(140*GameConfiguration.GAME_SCALE,310*GameConfiguration.GAME_SCALE),"P5");
        GraphPoint P6 = new GraphPoint(new Point(142*GameConfiguration.GAME_SCALE,96*GameConfiguration.GAME_SCALE),"P6");
        GraphPoint P7 = new GraphPoint(new Point(150*GameConfiguration.GAME_SCALE,163*GameConfiguration.GAME_SCALE),"P7");
        GraphPoint P8 = new GraphPoint(new Point(185*GameConfiguration.GAME_SCALE,150*GameConfiguration.GAME_SCALE),"P8");
        GraphPoint P9 = new GraphPoint(new Point(190*GameConfiguration.GAME_SCALE,220*GameConfiguration.GAME_SCALE),"P9");
        GraphPoint P10 = new GraphPoint(new Point(215*GameConfiguration.GAME_SCALE,120*GameConfiguration.GAME_SCALE),"P10");
        GraphPoint P11 = new GraphPoint(new Point(275*GameConfiguration.GAME_SCALE,238*GameConfiguration.GAME_SCALE),"P11");
        GraphPoint P12 = new GraphPoint(new Point(278*GameConfiguration.GAME_SCALE,85*GameConfiguration.GAME_SCALE),"P12");
        GraphPoint P13 = new GraphPoint(new Point(335*GameConfiguration.GAME_SCALE,95*GameConfiguration.GAME_SCALE),"P13");
        GraphPoint P14 = new GraphPoint(new Point(365*GameConfiguration.GAME_SCALE,122*GameConfiguration.GAME_SCALE),"P14");
        GraphPoint P15 = new GraphPoint(new Point(382*GameConfiguration.GAME_SCALE,190*GameConfiguration.GAME_SCALE),"P15");
        GraphPoint P16 = new GraphPoint(new Point(390*GameConfiguration.GAME_SCALE,104*GameConfiguration.GAME_SCALE),"P16");
        GraphPoint P17 = new GraphPoint(new Point(397*GameConfiguration.GAME_SCALE,150*GameConfiguration.GAME_SCALE),"P17");
        GraphPoint P18 = new GraphPoint(new Point(425*GameConfiguration.GAME_SCALE,190*GameConfiguration.GAME_SCALE),"P18");
        GraphPoint P19 = new GraphPoint(new Point(440*GameConfiguration.GAME_SCALE,227*GameConfiguration.GAME_SCALE),"P19");
        GraphPoint P20 = new GraphPoint(new Point(456*GameConfiguration.GAME_SCALE,127*GameConfiguration.GAME_SCALE),"P20");
        GraphPoint P21 = new GraphPoint(new Point(475*GameConfiguration.GAME_SCALE,105*GameConfiguration.GAME_SCALE),"P21");
        GraphPoint P22 = new GraphPoint(new Point(495*GameConfiguration.GAME_SCALE,175*GameConfiguration.GAME_SCALE),"P22");
        GraphPoint P23 = new GraphPoint(new Point(505*GameConfiguration.GAME_SCALE,222*GameConfiguration.GAME_SCALE),"P23");
        GraphPoint P24 = new GraphPoint(new Point(530*GameConfiguration.GAME_SCALE,130*GameConfiguration.GAME_SCALE),"P24");
        GraphPoint P25 = new GraphPoint(new Point(555*GameConfiguration.GAME_SCALE,170*GameConfiguration.GAME_SCALE),"P25");
        GraphPoint P26 = new GraphPoint(new Point(595*GameConfiguration.GAME_SCALE,140*GameConfiguration.GAME_SCALE),"P26");

        MapGame.getInstance().addGraphPoint(P1);
        MapGame.getInstance().addGraphPoint(P2);
        MapGame.getInstance().addGraphPoint(P3);
        MapGame.getInstance().addGraphPoint(P4);
        MapGame.getInstance().addGraphPoint(P5);
        MapGame.getInstance().addGraphPoint(P6);
        MapGame.getInstance().addGraphPoint(P7);
        MapGame.getInstance().addGraphPoint(P8);
        MapGame.getInstance().addGraphPoint(P9);
        MapGame.getInstance().addGraphPoint(P10);
        MapGame.getInstance().addGraphPoint(P11);
        MapGame.getInstance().addGraphPoint(P12);
        MapGame.getInstance().addGraphPoint(P13);
        MapGame.getInstance().addGraphPoint(P14);
        MapGame.getInstance().addGraphPoint(P15);
        MapGame.getInstance().addGraphPoint(P16);
        MapGame.getInstance().addGraphPoint(P17);
        MapGame.getInstance().addGraphPoint(P18);
        MapGame.getInstance().addGraphPoint(P19);
        MapGame.getInstance().addGraphPoint(P20);
        MapGame.getInstance().addGraphPoint(P21);
        MapGame.getInstance().addGraphPoint(P22);
        MapGame.getInstance().addGraphPoint(P23);
        MapGame.getInstance().addGraphPoint(P24);
        MapGame.getInstance().addGraphPoint(P25);
        MapGame.getInstance().addGraphPoint(P26);

        //GraphSegment init
        AHarbor.addSegment(new GraphSegment(P6, (int) AHarbor.getPoint().distance(P6.getPoint())));
        AHarbor.addSegment(new GraphSegment(P10, (int) AHarbor.getPoint().distance(P10.getPoint())));
        AHarbor.addSegment(new GraphSegment(P12, (int) AHarbor.getPoint().distance(P12.getPoint())));
        AHarbor.addSegment(new GraphSegment(BHarbor, (int) AHarbor.getPoint().distance(BHarbor.getPoint())));

        BHarbor.addSegment(new GraphSegment(AHarbor, (int) BHarbor.getPoint().distance(AHarbor.getPoint())));
        BHarbor.addSegment(new GraphSegment(P12, (int) BHarbor.getPoint().distance(P12.getPoint())));
        BHarbor.addSegment(new GraphSegment(P13, (int) BHarbor.getPoint().distance(P13.getPoint())));

        CHarbor.addSegment(new GraphSegment(P10, (int) CHarbor.getPoint().distance(P10.getPoint())));
        CHarbor.addSegment(new GraphSegment(P12, (int) CHarbor.getPoint().distance(P12.getPoint())));
        CHarbor.addSegment(new GraphSegment(P13, (int) CHarbor.getPoint().distance(P13.getPoint())));
        CHarbor.addSegment(new GraphSegment(P13, (int) CHarbor.getPoint().distance(P14.getPoint())));

        DHarbor.addSegment(new GraphSegment(P13, (int) DHarbor.getPoint().distance(P13.getPoint())));
        DHarbor.addSegment(new GraphSegment(P16, (int) DHarbor.getPoint().distance(P16.getPoint())));
        DHarbor.addSegment(new GraphSegment(P21, (int) DHarbor.getPoint().distance(P21.getPoint())));

        EHarbor.addSegment(new GraphSegment(P1, (int) EHarbor.getPoint().distance(P1.getPoint())));

        FHarbor.addSegment(new GraphSegment(P9, (int) FHarbor.getPoint().distance(P9.getPoint())));
        FHarbor.addSegment(new GraphSegment(P11, (int) FHarbor.getPoint().distance(P11.getPoint())));
        FHarbor.addSegment(new GraphSegment(P5, (int) FHarbor.getPoint().distance(P5.getPoint())));

        GHarbor.addSegment(new GraphSegment(P11, (int) GHarbor.getPoint().distance(P11.getPoint())));
        GHarbor.addSegment(new GraphSegment(P15, (int) GHarbor.getPoint().distance(P15.getPoint())));
        GHarbor.addSegment(new GraphSegment(P19, (int) GHarbor.getPoint().distance(P19.getPoint())));

        HHarbor.addSegment(new GraphSegment(P19, (int) HHarbor.getPoint().distance(P19.getPoint())));
        HHarbor.addSegment(new GraphSegment(P23, (int) HHarbor.getPoint().distance(P23.getPoint())));
        HHarbor.addSegment(new GraphSegment(KHarbor, (int) HHarbor.getPoint().distance(KHarbor.getPoint())));

        IHarbor.addSegment(new GraphSegment(P26, (int) IHarbor.getPoint().distance(P26.getPoint())));
        IHarbor.addSegment(new GraphSegment(P24, (int) IHarbor.getPoint().distance(P24.getPoint())));
        IHarbor.addSegment(new GraphSegment(P21, (int) IHarbor.getPoint().distance(P21.getPoint())));

        JHarbor.addSegment(new GraphSegment(P26, (int) JHarbor.getPoint().distance(P26.getPoint())));

        KHarbor.addSegment(new GraphSegment(P23, (int) KHarbor.getPoint().distance(P23.getPoint())));
        KHarbor.addSegment(new GraphSegment(P22, (int) KHarbor.getPoint().distance(P22.getPoint())));
        KHarbor.addSegment(new GraphSegment(P25, (int) KHarbor.getPoint().distance(P25.getPoint())));
        KHarbor.addSegment(new GraphSegment(HHarbor, (int) KHarbor.getPoint().distance(HHarbor.getPoint())));

        P1.addSegment(new GraphSegment(P2, (int) P1.getPoint().distance(P2.getPoint())));
        P1.addSegment(new GraphSegment(P3, (int) P1.getPoint().distance(P3.getPoint())));
        P1.addSegment(new GraphSegment(EHarbor, (int) P1.getPoint().distance(EHarbor.getPoint())));

        P2.addSegment(new GraphSegment(P1, (int) P2.getPoint().distance(P1.getPoint())));
        P2.addSegment(new GraphSegment(P4, (int) P2.getPoint().distance(P4.getPoint())));
        P2.addSegment(new GraphSegment(P6, (int) P2.getPoint().distance(P6.getPoint())));

        P3.addSegment(new GraphSegment(P1, (int) P3.getPoint().distance(P1.getPoint())));
        P3.addSegment(new GraphSegment(P5, (int) P3.getPoint().distance(P5.getPoint())));

        P4.addSegment(new GraphSegment(P2, (int) P4.getPoint().distance(P2.getPoint())));
        P4.addSegment(new GraphSegment(P6, (int) P4.getPoint().distance(P6.getPoint())));
        P4.addSegment(new GraphSegment(P7, (int) P4.getPoint().distance(P7.getPoint())));
        P4.addSegment(new GraphSegment(P9, (int) P4.getPoint().distance(P9.getPoint())));

        P5.addSegment(new GraphSegment(P3, (int) P5.getPoint().distance(P3.getPoint())));
        P5.addSegment(new GraphSegment(FHarbor, (int) P5.getPoint().distance(FHarbor.getPoint())));

        P6.addSegment(new GraphSegment(AHarbor, (int) P6.getPoint().distance(AHarbor.getPoint())));
        P6.addSegment(new GraphSegment(P2, (int) P6.getPoint().distance(P2.getPoint())));
        P6.addSegment(new GraphSegment(P4, (int) P6.getPoint().distance(P4.getPoint())));
        P6.addSegment(new GraphSegment(P7, (int) P6.getPoint().distance(P7.getPoint())));
        P6.addSegment(new GraphSegment(P8, (int) P6.getPoint().distance(P8.getPoint())));
        P6.addSegment(new GraphSegment(P10, (int) P6.getPoint().distance(P10.getPoint())));

        P7.addSegment(new GraphSegment(P4, (int) P7.getPoint().distance(P4.getPoint())));
        P7.addSegment(new GraphSegment(P6, (int) P7.getPoint().distance(P6.getPoint())));
        P7.addSegment(new GraphSegment(P8, (int) P7.getPoint().distance(P8.getPoint())));
        P7.addSegment(new GraphSegment(P9, (int) P7.getPoint().distance(P9.getPoint())));

        P8.addSegment(new GraphSegment(P7, (int) P8.getPoint().distance(P7.getPoint())));
        P8.addSegment(new GraphSegment(P6, (int) P8.getPoint().distance(P6.getPoint())));
        P8.addSegment(new GraphSegment(P10, (int) P8.getPoint().distance(P10.getPoint())));
        P8.addSegment(new GraphSegment(P9, (int) P8.getPoint().distance(P9.getPoint())));

        P9.addSegment(new GraphSegment(P4, (int) P9.getPoint().distance(P4.getPoint())));
        P9.addSegment(new GraphSegment(P7, (int) P9.getPoint().distance(P7.getPoint())));
        P9.addSegment(new GraphSegment(P8, (int) P9.getPoint().distance(P8.getPoint())));
        P9.addSegment(new GraphSegment(P11, (int) P9.getPoint().distance(P11.getPoint())));
        P9.addSegment(new GraphSegment(FHarbor, (int) P9.getPoint().distance(FHarbor.getPoint())));

        P10.addSegment(new GraphSegment(P6, (int) P10.getPoint().distance(P6.getPoint())));
        P10.addSegment(new GraphSegment(AHarbor, (int) P10.getPoint().distance(AHarbor.getPoint())));
        P10.addSegment(new GraphSegment(P12, (int) P10.getPoint().distance(P12.getPoint())));
        P10.addSegment(new GraphSegment(CHarbor, (int) P10.getPoint().distance(CHarbor.getPoint())));
        P10.addSegment(new GraphSegment(P8, (int) P10.getPoint().distance(P8.getPoint())));

        P11.addSegment(new GraphSegment(P9, (int) P11.getPoint().distance(P9.getPoint())));
        P11.addSegment(new GraphSegment(P15, (int) P11.getPoint().distance(P15.getPoint())));
        P11.addSegment(new GraphSegment(GHarbor, (int) P11.getPoint().distance(GHarbor.getPoint())));
        P11.addSegment(new GraphSegment(FHarbor, (int) P11.getPoint().distance(FHarbor.getPoint())));

        P12.addSegment(new GraphSegment(AHarbor, (int) P12.getPoint().distance(AHarbor.getPoint())));
        P12.addSegment(new GraphSegment(BHarbor, (int) P12.getPoint().distance(BHarbor.getPoint())));
        P12.addSegment(new GraphSegment(P13, (int) P12.getPoint().distance(P13.getPoint())));
        P12.addSegment(new GraphSegment(CHarbor, (int) P12.getPoint().distance(CHarbor.getPoint())));
        P12.addSegment(new GraphSegment(P10, (int) P12.getPoint().distance(P10.getPoint())));

        P13.addSegment(new GraphSegment(P12, (int) P13.getPoint().distance(P12.getPoint())));
        P13.addSegment(new GraphSegment(BHarbor, (int) P13.getPoint().distance(BHarbor.getPoint())));
        P13.addSegment(new GraphSegment(DHarbor, (int) P13.getPoint().distance(DHarbor.getPoint())));
        P13.addSegment(new GraphSegment(P16, (int) P13.getPoint().distance(P16.getPoint())));
        P13.addSegment(new GraphSegment(P14, (int) P13.getPoint().distance(P14.getPoint())));
        P13.addSegment(new GraphSegment(CHarbor, (int) P13.getPoint().distance(CHarbor.getPoint())));

        P14.addSegment(new GraphSegment(P13, (int) P14.getPoint().distance(P13.getPoint())));
        P14.addSegment(new GraphSegment(P16, (int) P14.getPoint().distance(P16.getPoint())));
        P14.addSegment(new GraphSegment(P17, (int) P14.getPoint().distance(P17.getPoint())));
        P14.addSegment(new GraphSegment(CHarbor, (int) P14.getPoint().distance(CHarbor.getPoint())));

        P15.addSegment(new GraphSegment(P17, (int) P15.getPoint().distance(P17.getPoint())));
        P15.addSegment(new GraphSegment(P18, (int) P15.getPoint().distance(P18.getPoint())));
        P15.addSegment(new GraphSegment(P19, (int) P15.getPoint().distance(P19.getPoint())));
        P15.addSegment(new GraphSegment(GHarbor, (int) P15.getPoint().distance(GHarbor.getPoint())));
        P15.addSegment(new GraphSegment(P11, (int) P15.getPoint().distance(P11.getPoint())));

        P16.addSegment(new GraphSegment(P13, (int) P16.getPoint().distance(P13.getPoint())));
        P16.addSegment(new GraphSegment(DHarbor, (int) P16.getPoint().distance(DHarbor.getPoint())));
        P16.addSegment(new GraphSegment(P21, (int) P16.getPoint().distance(P21.getPoint())));
        P16.addSegment(new GraphSegment(P20, (int) P16.getPoint().distance(P20.getPoint())));
        P16.addSegment(new GraphSegment(P17, (int) P16.getPoint().distance(P17.getPoint())));
        P16.addSegment(new GraphSegment(P14, (int) P16.getPoint().distance(P14.getPoint())));

        P17.addSegment(new GraphSegment(P14, (int) P17.getPoint().distance(P14.getPoint())));
        P17.addSegment(new GraphSegment(P16, (int) P17.getPoint().distance(P16.getPoint())));
        P17.addSegment(new GraphSegment(P20, (int) P17.getPoint().distance(P20.getPoint())));
        P17.addSegment(new GraphSegment(P22, (int) P17.getPoint().distance(P22.getPoint())));
        P17.addSegment(new GraphSegment(P18, (int) P17.getPoint().distance(P18.getPoint())));
        P17.addSegment(new GraphSegment(P15, (int) P17.getPoint().distance(P15.getPoint())));

        P18.addSegment(new GraphSegment(P15, (int) P18.getPoint().distance(P15.getPoint())));
        P18.addSegment(new GraphSegment(P17, (int) P18.getPoint().distance(P17.getPoint())));
        P18.addSegment(new GraphSegment(P22, (int) P18.getPoint().distance(P22.getPoint())));
        P18.addSegment(new GraphSegment(P19, (int) P18.getPoint().distance(P19.getPoint())));

        P19.addSegment(new GraphSegment(P15, (int) P19.getPoint().distance(P15.getPoint())));
        P19.addSegment(new GraphSegment(P18, (int) P19.getPoint().distance(P18.getPoint())));
        P19.addSegment(new GraphSegment(P22, (int) P19.getPoint().distance(P22.getPoint())));
        P19.addSegment(new GraphSegment(P23, (int) P19.getPoint().distance(P23.getPoint())));
        P19.addSegment(new GraphSegment(GHarbor, (int) P19.getPoint().distance(GHarbor.getPoint())));
        P19.addSegment(new GraphSegment(HHarbor, (int) P19.getPoint().distance(HHarbor.getPoint())));

        P20.addSegment(new GraphSegment(P16, (int) P20.getPoint().distance(P16.getPoint())));
        P20.addSegment(new GraphSegment(P21, (int) P20.getPoint().distance(P21.getPoint())));
        P20.addSegment(new GraphSegment(P24, (int) P20.getPoint().distance(P24.getPoint())));
        P20.addSegment(new GraphSegment(P22, (int) P20.getPoint().distance(P22.getPoint())));
        P20.addSegment(new GraphSegment(P17, (int) P20.getPoint().distance(P17.getPoint())));

        P21.addSegment(new GraphSegment(P14, (int) P21.getPoint().distance(P14.getPoint())));
        P21.addSegment(new GraphSegment(DHarbor, (int) P21.getPoint().distance(DHarbor.getPoint())));
        P21.addSegment(new GraphSegment(IHarbor, (int) P21.getPoint().distance(IHarbor.getPoint())));
        P21.addSegment(new GraphSegment(P24, (int) P21.getPoint().distance(P24.getPoint())));
        P21.addSegment(new GraphSegment(P20, (int) P21.getPoint().distance(P20.getPoint())));

        P22.addSegment(new GraphSegment(P17, (int) P22.getPoint().distance(P17.getPoint())));
        P22.addSegment(new GraphSegment(P20, (int) P22.getPoint().distance(P20.getPoint())));
        P22.addSegment(new GraphSegment(P24, (int) P22.getPoint().distance(P24.getPoint())));
        P22.addSegment(new GraphSegment(P25, (int) P22.getPoint().distance(P25.getPoint())));
        P22.addSegment(new GraphSegment(KHarbor, (int) P22.getPoint().distance(KHarbor.getPoint())));
        P22.addSegment(new GraphSegment(P23, (int) P22.getPoint().distance(P23.getPoint())));
        P22.addSegment(new GraphSegment(P19, (int) P22.getPoint().distance(P19.getPoint())));
        P22.addSegment(new GraphSegment(P18, (int) P22.getPoint().distance(P18.getPoint())));

        P23.addSegment(new GraphSegment(P19, (int) P23.getPoint().distance(P19.getPoint())));
        P23.addSegment(new GraphSegment(P22, (int) P23.getPoint().distance(P22.getPoint())));
        P23.addSegment(new GraphSegment(KHarbor, (int) P23.getPoint().distance(KHarbor.getPoint())));
        P23.addSegment(new GraphSegment(HHarbor, (int) P23.getPoint().distance(HHarbor.getPoint())));

        P24.addSegment(new GraphSegment(P20, (int) P24.getPoint().distance(P20.getPoint())));
        P24.addSegment(new GraphSegment(P21, (int) P24.getPoint().distance(P21.getPoint())));
        P24.addSegment(new GraphSegment(IHarbor, (int) P24.getPoint().distance(IHarbor.getPoint())));
        P24.addSegment(new GraphSegment(P26, (int) P24.getPoint().distance(P26.getPoint())));
        P24.addSegment(new GraphSegment(P25, (int) P24.getPoint().distance(P25.getPoint())));
        P24.addSegment(new GraphSegment(P22, (int) P24.getPoint().distance(P22.getPoint())));

        P25.addSegment(new GraphSegment(P22, (int) P25.getPoint().distance(P22.getPoint())));
        P25.addSegment(new GraphSegment(P24, (int) P25.getPoint().distance(P24.getPoint())));
        P25.addSegment(new GraphSegment(P26, (int) P25.getPoint().distance(P26.getPoint())));
        P25.addSegment(new GraphSegment(KHarbor, (int) P25.getPoint().distance(KHarbor.getPoint())));

        P26.addSegment(new GraphSegment(P24, (int) P26.getPoint().distance(P24.getPoint())));
        P26.addSegment(new GraphSegment(IHarbor, (int) P26.getPoint().distance(IHarbor.getPoint())));
        P26.addSegment(new GraphSegment(JHarbor, (int) P26.getPoint().distance(JHarbor.getPoint())));
        P26.addSegment(new GraphSegment(P25, (int) P26.getPoint().distance(P25.getPoint())));

        lstFaction.addAll(lstBotFaction);
        lstFaction.add(player);
        lstFaction.add(pirate);
        MapGame.getInstance().setLstPopUp(lstPopUp);
        MapGame.getInstance().setLstHarbor(lstHarbor);
        MapGame.getInstance().setLstFaction(lstFaction);
        MapGame.getInstance().setLstBotFaction(lstBotFaction);
        MapGame.getInstance().setPlayer(player);
        MapGame.getInstance().setPirate(pirate);

        for (Faction factionTmp1 : MapGame.getInstance().getLstFaction()){
            for (Faction factionTmp2 : MapGame.getInstance().getLstFaction()){
                factionTmp1.setRelationship(factionTmp2,0);
            }
        }

        return MapGame.getInstance();
    }

}
