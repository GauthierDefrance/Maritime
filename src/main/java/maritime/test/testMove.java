package maritime.test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import marine_trash.game.Player;
import maritime.config.GameConfiguration;
import maritime.engine.entity.Standard;
import maritime.engine.faction.Faction;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.GraphSegment;
import maritime.engine.graph.SearchInGraph;
import maritime.gui.GameDisplay;

public class testMove extends JFrame implements Runnable {
    private Standard standard = new Standard("bob",1,20,new Point(10,10),"standard",5);
    private Standard standard2 = new Standard("carl",1,20,new Point(10,10),"standard",5);

    private GameDisplay dashboard;

    public testMove(String title) {
        super(title);
        init();
    }

    private void init() {



        Faction player = new Faction();
        player.addLstBoat(standard);
        player.addLstBoat(standard2);

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
        path.addFirst(A);

        ArrayList<GraphPoint> path2 = new ArrayList<GraphPoint>();
        path2 = SearchInGraph.findPath(C,F);
        path2.addFirst(C);

        standard.setPath(path);
        standard.setPosition(A.getPoint());
        standard.setContinuePath(true);

        standard2.setPath(path2);
        standard2.setPosition(D.getPoint());
        standard2.setContinuePath(true);


        Container contentPane = getContentPane();
        contentPane.add(dashboard);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(640, 360);
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
                standard.followThePath();
                standard2.followThePath();

            dashboard.repaint();
        }
    }


        public static void main(String[] args) {

        testMove gameMainGUI = new testMove("Aircraft game");
        Thread gameThread = new Thread(gameMainGUI);
        gameThread.start();
    }



}
