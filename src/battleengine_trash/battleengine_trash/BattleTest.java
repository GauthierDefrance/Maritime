package battleengine_trash.battleengine_trash;
import battleengine_trash.battleengine_trash.process.TeamManager;
import battleengine_trash.battleengine_trash.team.Team;
import config.GameConfiguration;
import engine.entity.boats.*;
import engine.graph.GraphPoint;
import gui.PaintEntity;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BattleTest extends JPanel {
    private TeamManager teamManager;
    private Team A;
    private Team B;
    private PaintEntity painter;
    private Image image;

    public void init(){
        teamManager = new TeamManager();
        painter = new PaintEntity();
        Fleet Af = new Fleet();
        Fleet Bf = new Fleet();

        Military military2 = new Military("military2","blue",new GraphPoint(new Point(10,10),null));
        Merchant merchant1 = new Merchant("merchant1","blue",new GraphPoint(new Point(10,100),null));
        Merchant merchant2 = new Merchant("merchant2","blue",new GraphPoint(new Point(10,200),null));

        Standard standard2 = new Standard("standard2","red",new GraphPoint(new Point(300,10),null));
        Fodder fodder1 = new Fodder("fodder1","red", new GraphPoint(new Point(300,100),null));
        Fodder fodder2 = new Fodder("fodder2","red",new GraphPoint(new Point(300,200),null));

        Af.add(military2);
        Af.add(merchant1);
        Af.add(merchant2);

        ArrayList<GraphPoint> points = new ArrayList<>();
        points.add(new GraphPoint(new Point(10,10),null));
        points.add(new GraphPoint(new Point(300,500),null));
        points.add(new GraphPoint(new Point(500,200),null));

        for(Boat boat : Af.getArrayListFleet()){
            boat.setPath(points);
            boat.setContinuePath(true);
        }

        Bf.add(standard2);
        Af.add(fodder1);
        Af.add(fodder2);

        A = new Team(Af);
        B = new Team(Bf);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(Boat boat : A.getFleet().getArrayListFleet()){
            painter.paint(boat, g2d);
        }
        for(Boat boat : B.getFleet().getArrayListFleet()){
            painter.paint(boat,g2d);
        }

    }

    private void gametick(){
        teamManager.move(A);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Battle Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BattleTest test = new BattleTest();
        test.init();
        frame.getContentPane().add(test);
        frame.setSize(800, 600);  // Taille de la fenêtre
        frame.setVisible(true);
        test.paint(frame.getGraphics());
        while(true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            test.repaint();
            test.gametick();
        }
    }


}
