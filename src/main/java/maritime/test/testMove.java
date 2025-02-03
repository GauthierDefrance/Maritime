package maritime.test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import maritime.config.GameConfiguration;
import maritime.engine.entity.*;
import maritime.engine.faction.Faction;
import maritime.engine.graph.*;
import maritime.gui.GameDisplay;

public class testMove extends JFrame implements Runnable {
    private Military military = new Military("bob",1,20,new Point(10,10),"standard",6);
    private Standard standard2 = new Standard("carl",1,20,new Point(10,10),"standard",3);

    private Standard standard = new Standard("bob",1,20,new Point(10,10),"standard",3);
    private Military military2 = new Military("bob",1,20,new Point(10,10),"standard",6);

    private Merchant merchant = new Merchant("bob",1,20,new Point(10,10),"standard",2);
    private Merchant merchant2 = new Merchant("bob",1,20,new Point(10,10),"standard",2);

    private GameDisplay dashboard;

    public testMove(String title) {
        super(title);
        init();
    }

    private void init() {
        Faction player = new Faction();
        player.addBoat(military);
        player.addBoat(standard2);

        player.addBoat(standard);
        player.addBoat(military2);

        player.addBoat(merchant);
        player.addBoat(merchant2);

        ArrayList<Faction> LstFaction = new ArrayList<Faction>();
        LstFaction.add(player);

        dashboard = new GameDisplay(LstFaction);




        GraphPoint A = new GraphPoint(new Point(10*2,40*2),"A");
        GraphPoint B = new GraphPoint(new Point(80*2,80*2),"B");
        GraphPoint C = new GraphPoint(new Point(160*2,160*2),"C");
        GraphPoint D = new GraphPoint(new Point(220*2,140*2),"D");
        GraphPoint F = new GraphPoint(new Point(230*2,10*2),"F");
        GraphPoint G = new GraphPoint(new Point(260*2,120*2),"G");
        GraphPoint E = new GraphPoint(new Point(280*2,120*2),"E");

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

        merchant.setPath(path4);
        merchant.setPosition(new Point(C.getPoint()));
        merchant.setContinuePath(true);

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
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            military.followThePath();
            standard2.followThePath();

            military2.followThePath();
            standard.followThePath();

            merchant.followThePath();
            merchant2.followThePath();

            dashboard.repaint();
        }
    }


        public static void main(String[] args) {

        testMove gameMainGUI = new testMove("Aircraft game");
        Thread gameThread = new Thread(gameMainGUI);
        gameThread.start();
    }



}
