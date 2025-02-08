package maritime.test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import maritime.config.GameConfiguration;
import maritime.config.GameInitFactory;
import maritime.engine.entity.Harbor;
import maritime.engine.entity.boats.Fodder;
import maritime.engine.entity.boats.Merchant;
import maritime.engine.entity.boats.Military;
import maritime.engine.entity.boats.Standard;
import maritime.engine.faction.Faction;
import maritime.engine.faction.Player;
import maritime.engine.graph.*;
import maritime.engine.process.BoatManager;
import maritime.engine.process.PlayerManager;
import maritime.gui.GameDisplay;

public class TestMove extends JFrame implements Runnable {

    private GraphPoint F = new GraphPoint(new Point(490*GameConfiguration.GAME_SCALE,70*GameConfiguration.GAME_SCALE),"F");

    private Harbor harbor1 = new Harbor("bob","red",new Point(490*GameConfiguration.GAME_SCALE,30*GameConfiguration.GAME_SCALE),F);

    private Military military = new Military("bob","red",new Point(10,10));
    private Standard standard2 = new Standard("carl","blue",new Point(10,10));

    private Standard standard = new Standard("bob","red",new Point(10,10));
    private Military military2 = new Military("bob","blue",new Point(10,10));

    private Fodder fodder = new Fodder("bob","",new Point(10,10));
    private Merchant merchant2 = new Merchant("bob","red",new Point(10,10));

    private GameInitFactory map = new GameInitFactory(0);
    private PlayerManager playerManager = new PlayerManager(map);

    private GameDisplay dashboard;

    public TestMove(String title) {
        super(title);
        init();
    }

    private void init() {
        Player player = new Player("blue");
        Faction faction = new Faction("");
        player.addBoat(military);
        player.addBoat(standard2);

        player.addBoat(standard);
        player.addBoat(military2);

        faction.addBoat(fodder);
        player.addBoat(merchant2);

        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        lstBotFaction.add(faction);
        ArrayList<Harbor> lstHarbor = new ArrayList<>();
        lstHarbor.add(harbor1);
        player.addHarbor(harbor1);
        map.setLstBotFaction(lstBotFaction);
        map.setPlayer(player);
        map.setLstHarbor(lstHarbor);

        dashboard = new GameDisplay(map);


        GraphPoint A = new GraphPoint(new Point(20*GameConfiguration.GAME_SCALE,80*GameConfiguration.GAME_SCALE),"A");
        GraphPoint B = new GraphPoint(new Point(160*GameConfiguration.GAME_SCALE,160*GameConfiguration.GAME_SCALE),"B");
        GraphPoint C = new GraphPoint(new Point(320*GameConfiguration.GAME_SCALE,320*GameConfiguration.GAME_SCALE),"C");
        GraphPoint D = new GraphPoint(new Point(440*GameConfiguration.GAME_SCALE,280*GameConfiguration.GAME_SCALE),"D");
        GraphPoint G = new GraphPoint(new Point(520*GameConfiguration.GAME_SCALE,240*GameConfiguration.GAME_SCALE),"G");
        GraphPoint E = new GraphPoint(new Point(560*GameConfiguration.GAME_SCALE,240*GameConfiguration.GAME_SCALE),"E");

        A.addSegment(new GraphSegment(B,1));

        B.addSegment(new GraphSegment(A,1));
        B.addSegment(new GraphSegment(C,1));
        B.addSegment(new GraphSegment(D,1));
        B.addSegment(new GraphSegment(F,1));

        C.addSegment(new GraphSegment(B,1));
        C.addSegment(new GraphSegment(D,1));

        D.addSegment(new GraphSegment(F,1));
        D.addSegment(new GraphSegment(G,1));
        D.addSegment(new GraphSegment(B,1));
        D.addSegment(new GraphSegment(C,1));

        G.addSegment(new GraphSegment(D,1));
        G.addSegment(new GraphSegment(F,1));
        G.addSegment(new GraphSegment(E,1));

        F.addSegment(new GraphSegment(B,1));
        F.addSegment(new GraphSegment(D,1));
        F.addSegment(new GraphSegment(G,1));

        E.addSegment(new GraphSegment(G,1));


        ArrayList<GraphPoint> path = new ArrayList<GraphPoint>();
        path = SearchInGraph.findPath(A,E);

        ArrayList<GraphPoint> path4 = new ArrayList<GraphPoint>();
        path4 = SearchInGraph.findPath(A,E);

        ArrayList<GraphPoint> path3 = new ArrayList<GraphPoint>();
        path3 = SearchInGraph.findPath(F,C);

        ArrayList<GraphPoint> path2 = new ArrayList<GraphPoint>();


        military.setPath(path);
        military.setPosition(new Point(A.getPoint()));
        military.setContinuePath(true);

        GraphPoint XX = new GraphPoint(military.getPosition(),"E");
        path2.add(XX);

        standard2.setPath(path2);
        standard2.setPosition(new Point(E.getPoint()));
        standard2.setContinuePath(true);

        standard.setPath(path3);
        standard.setPosition(new Point(G.getPoint()));
        standard.setContinuePath(true);

        military2.setPath(path3);
        military2.setPosition(new Point(D.getPoint()));
        military2.setContinuePath(true);

        fodder.setPath(path4);
        fodder.setPosition(new Point(C.getPoint()));
        fodder.setContinuePath(true);

        merchant2.setPath(path4);
        merchant2.setPosition(new Point(F.getPoint()));
        merchant2.setContinuePath(true);

        Container contentPane = getContentPane();
        contentPane.add(dashboard);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dashboard.setBackground(new Color(78, 172, 233));
        setSize(640, 360);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
        TestDebug debug = new TestDebug("Debug",map);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!map.isTimeStop()){
                BoatManager.followThePath(military);
                BoatManager.followThePath(standard2);

                BoatManager.followThePath(military2);
                BoatManager.followThePath(standard);

                BoatManager.followThePath(fodder);
                BoatManager.followThePath(merchant2);
                playerManager.updatePlayerVision();
            }
            dashboard.repaint();
        }
    }


        public static void main(String[] args) {

        TestMove gameMainGUI = new TestMove("Aircraft game");
        Thread gameThread = new Thread(gameMainGUI);
        gameThread.start();
    }



}
