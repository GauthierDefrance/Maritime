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

        GraphPoint A = new GraphPoint(new Point(100,100),"A");
        GraphPoint B = new GraphPoint(new Point(640,100),"B");
        GraphPoint C = new GraphPoint(new Point(300,100),"C");
        GraphPoint D = new GraphPoint(new Point(300,200),"D");

        A.addSegment(new GraphSegment(B,1));
        A.addSegment(new GraphSegment(D,1));

        B.addSegment(new GraphSegment(A,1));
        B.addSegment(new GraphSegment(C,1));

        C.addSegment(new GraphSegment(A,1));
        C.addSegment(new GraphSegment(D,1));

        D.addSegment(new GraphSegment(A,1));
        D.addSegment(new GraphSegment(C,1));

        ArrayList<GraphPoint> path = new ArrayList<GraphPoint>();
        path.add(A);
        path.add(B);
        path.add(C);
        path.add(D);

        ArrayList<GraphPoint> path2 = new ArrayList<GraphPoint>();
        path2.add(D);
        path2.add(C);
        path2.add(B);
        path2.add(A);

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
