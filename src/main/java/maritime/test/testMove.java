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
    private GameDisplay dashboard;

    public testMove(String title) {
        super(title);
        init();
    }

    private void init() {

        Faction player = new Faction();
        player.addLstBoat(standard);

        ArrayList<Faction> LstFaction = new ArrayList<Faction>();
        LstFaction.add(player);

        dashboard = new GameDisplay(LstFaction);


        GraphPoint A = new GraphPoint(new Point(10,10),"A");
        GraphPoint B = new GraphPoint(new Point(210,10),"B");
        GraphPoint C = new GraphPoint(new Point(210,210),"C");
        GraphPoint D = new GraphPoint(new Point(10,210),"D");

        A.addSegment(new GraphSegment(B,1));
        A.addSegment(new GraphSegment(D,1));

        B.addSegment(new GraphSegment(A,1));
        B.addSegment(new GraphSegment(C,1));

        C.addSegment(new GraphSegment(A,1));
        C.addSegment(new GraphSegment(D,1));

        D.addSegment(new GraphSegment(A,1));
        D.addSegment(new GraphSegment(C,1));

        standard.setPosition(A.getPoint());

        standard.setPath(SearchInGraph.findPath(A,C));

        Container contentPane = getContentPane();
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
            dashboard.repaint();
        }
    }


        public static void main(String[] args) {

        testMove gameMainGUI = new testMove("Aircraft game");
        Thread gameThread = new Thread(gameMainGUI);
        gameThread.start();
    }



}
